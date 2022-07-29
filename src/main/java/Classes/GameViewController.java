package Classes;

import Agents.*;
import Equipments.Axe;
import GameControl.GameMap;
import GraphicClasses.Controller;
import Players.Virologist;
import Tiles.Tile;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//A játékmenetet kezelő ablak és logika

public class GameViewController implements Initializable {


    //A viroglógus interakció képernyő egy példánya  a szinkron működés érdekében
    public VirologistInteractionController viController;
    //A játék ablaka
    public AnchorPane gamePane;
    //Model
    @FXML
    public ListView<String> lv_virologists; //Az összes virológust felsoroló lista, itt el lehet dönteni melyik egyszerűbb,
    // hogy eleve csak azok legyenek felsorolva akikkel interaktálhat, vagy az összes,
    // de csak azokra lép át a VirologistIntercation nézetbe akit meg tud érinteni
    //mezők fxml listája
    @FXML
    public ListView<String> lv_tiles;
    //kiirja hogy éppen melyik virológus van soron
    public Label current;
    //A mozgatásért felelős gomb
    @FXML
    private Button bMove;
    //A Crafoltási lista
    @FXML
    private ChoiceBox<String> ch_craftables; //A craftolható ágenseket megjelenítő lista
    @FXML
    private ListView<String> lv_items = new ListView(); //A játékos birtokában lévő itemeket megjelenítő lista
    @FXML
    private Label l_selected;   //Label amin bemutatjuk a kiválasztás működését

    private String selectedItem;    //Ebben a stringben tárolja a Controller, hogy melyik itemet választotta ki a user, szintén csak bemutatás miatt

    // az adott kiválasztott elemet állítja be
    ChangeListener<String> itemSelected = (observableValue, s, t1) -> { //finom kis lambda, ez gyakorlatilag ki is vehető mert ebben a nézetben ez csak egy olvasható lista
        selectedItem = lv_items.getSelectionModel().getSelectedItem();
        l_selected.setText(selectedItem);
    };

    //Ha az adott virológusra kattintunk akkor átváltja annak megfelelő interaction nézetébe
    ChangeListener<String> virologistSelected = (observableValue, s, t1) -> { //Virológus kiválasztása listából
        selectedItem = lv_virologists.getSelectionModel().getSelectedItem();
        l_selected.setText(selectedItem);
        if(!lv_virologists.getItems().contains(l_selected.getText()))
            return;
        //Kiiratja a megfelelő virológusokat név szerint
        for (Virologist virologist : Controller.getGameController().getGameMap().getVirologists()) {
            if (virologist.GetName().equals(selectedItem)) {
                Controller.setVirologist(virologist);
                break;
            }
        }
        //Adat frissítés és ablak váltás
        viController.load();
        GraphicsMain.SetPane(2);
    };


    //Az adott mezőt választja ki, amire a User kattint
    ChangeListener<String> tileSelected = (observableValue, s, t1) -> {

        selectedItem = lv_tiles.getSelectionModel().getSelectedItem();
        l_selected.setText(selectedItem);
    };


    //Kraftolható agent kiválasztását kezelő listener (csak bemutatásként változtat egy text labelt)
    public void agentSelected(ActionEvent ae){
        String agent = ch_craftables.getValue();
        l_selected.setText(agent);
    }
    public void BMoveEffect(ActionEvent ae){
        if(!lv_tiles.getItems().contains(l_selected.getText()))
            return;
        ArrayList<Tile> tiles=Controller.getGameController().GetCurrentPlayer().GetTile().getNeighbours();
        int counter=0;
        for (Tile t:tiles) {
            String s=t.getClass().toString();
            s=s.substring(s.indexOf('.')+1);
            if (l_selected.getText().substring(0,l_selected.getText().indexOf(" ")).equals(s))
                counter++;
            if(l_selected.getText().equals(s+" "+counter)){
                Controller.GVirologistMove(t);
                break;


            }


        }


        Reload();
        gamePane.getChildren().add(Controller.RedrawComponenets());
        gamePane.getChildren().clear();
        Controller.ReDraw((int) gamePane.getWidth(), (int) gamePane.getHeight());
        gamePane.getChildren().add(Controller.RedrawComponenets());
    }

    /**
     * Init függvény amely kirajzol, és betölti megfelelő ablakokat
     *
     * @return void
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Controller.Init();
        Controller.ReDraw(400, 600);
        gamePane.getChildren().add(Controller.RedrawComponenets());
        // Ablak ujrameretezesere vonatkozo event
        gamePane.heightProperty().addListener((obs, oldVal, newVal) -> {
            gamePane.getChildren().clear();
            Controller.ReDraw((int) gamePane.getWidth(), (int) gamePane.getHeight());
            gamePane.getChildren().add(Controller.RedrawComponenets());
        });
        gamePane.widthProperty().addListener((obs, oldVal, newVal) -> {
            gamePane.getChildren().clear();
            Controller.ReDraw((int) gamePane.getWidth(), (int) gamePane.getHeight());
            gamePane.getChildren().add(Controller.RedrawComponenets());
        });

//Tesztadatok Annak érdekében hogy valamennyivel könnyebb legyen tesztelni, mindenki kap egy baltát

        Controller.getGameController().GetCurrentPlayer().AddEquipment(new Axe());
        Controller.getGameController().GetCurrentPlayer().setAmino(4);
        Controller.getGameController().GetCurrentPlayer().setNukleo(3);

        Reload();

        lv_items.getSelectionModel().selectedItemProperty().addListener(itemSelected); //Regisztráljuk, hogy kiválasztáshoz milyen viselkedést akarunk hogy történjen
        ch_craftables.setOnAction(this::agentSelected); //Regisztráljuk, hogy kiválasztáshoz milyen viselkedést akarunk, hogy történjen
        lv_virologists.getSelectionModel().selectedItemProperty().addListener(virologistSelected); //Regisztráljuk, hogy kiválasztáshoz milyen viselkedést akarunk hogy történjen
        lv_tiles.getSelectionModel().selectedItemProperty().addListener(tileSelected);
        bMove.setOnAction(this::BMoveEffect);

    }

    //Craftolast megvalosito esemenykezelo
    /**
     *
     *
     * @return void
     */
    public void OnCraftButtonPressed(ActionEvent actionEvent) {
try {
    if(Controller.getGameController().GetCurrentPlayer().getNukleo()>0&&Controller.getGameController().GetCurrentPlayer().getAmino()>1&&
            Controller.getGameController().GetCurrentPlayer().getLearnedAgents().size()!=0) {
        //A névmeggegyezés szerint kraftol
        String agent = ch_craftables.getValue();
        // l_selected.setText(agent);
        if (agent.equals("DanceVirus")) {
            Controller.getGameController().GetCurrentPlayer().Craft(new DanceVirus());
            Controller.getGameController().NextPlayer();
            GraphicsMain.SetPane(1);
        } else if (agent.equals("DanceVaccine")) {
            Controller.getGameController().GetCurrentPlayer().Craft(new DanceVaccine());
            Controller.getGameController().NextPlayer();
            GraphicsMain.SetPane(1);
        } else if (agent.equals("ForgetVirus")) {
            Controller.getGameController().GetCurrentPlayer().Craft(new ForgetVirus());
            Controller.getGameController().NextPlayer();
            GraphicsMain.SetPane(1);
        } else if (agent.equals("ForgetVaccine")) {
            Controller.getGameController().GetCurrentPlayer().Craft(new ForgetVaccine());
            Controller.getGameController().NextPlayer();
            GraphicsMain.SetPane(1);
        } else if (agent.equals("StunVirus")) {
            Controller.getGameController().GetCurrentPlayer().Craft(new StunVirus());
            Controller.getGameController().NextPlayer();
            GraphicsMain.SetPane(1);
        } else if (agent.equals("StunVaccine")) {
            Controller.getGameController().GetCurrentPlayer().Craft(new StunVaccine());
            Controller.getGameController().NextPlayer();
            GraphicsMain.SetPane(1);
        }

        Reload();
    }


}catch (Throwable e) {
    e.printStackTrace();
}
}
    public void Reload(){
        //mikor újratöltjük akkor a labeleket és listákat lenullozzuk
        lv_virologists.getItems().clear();
        lv_tiles.getItems().clear();
        lv_items.getItems().clear();
        ch_craftables.getItems().clear();

        current.setText("Current Player: \n\n"+Controller.getGameController().GetCurrentPlayer().GetName());
        // jobb oldal
        // virologists
        ArrayList<Virologist> Virologists=Controller.getGameController().GetCurrentPlayer().GetTile().getVirologists();
        int viroCounter=0;
        for (Virologist v:Virologists) {
            lv_virologists.getItems().add(v.GetName());
            viroCounter++;
        }

        //tiles
        ArrayList<Tile> Tiles=Controller.getGameController().GetCurrentPlayer().GetTile().getNeighbours();
        int tileCounter=0;
        int labCounter=0;
        int bunkerCounter=0;
        int storageCounter=0;
        //Tile.ok felsorolása és a label-hez hozzáadása a labelhez, ahol fel van listaszerűen sorolva
        for (Tile t:Tiles) {
            String s=t.getClass().toString();
            s=s.substring(s.indexOf('.')+1);
            if(s.equals("Tile")){
                lv_tiles.getItems().add(s+" "+(tileCounter+1));
                tileCounter++;
            }
            else if(s.equals("Laboratory")){
                lv_tiles.getItems().add(s+" "+(labCounter+1));
                labCounter++;
            }
            else  if(s.equals("Storage")){
                lv_tiles.getItems().add(s+" "+(storageCounter+1));
                storageCounter++;
            }
            else if(s.equals("Bunker")){
                lv_tiles.getItems().add(s+" "+(bunkerCounter+1));
                bunkerCounter++;
            }
        }

        //alsó
        if(Controller.getGameController().GetCurrentPlayer().getEquipments().size()!=0){
            for (int i=0; i<Controller.getGameController().GetCurrentPlayer().getEquipments().size();i++){
                String s=Controller.getGameController().GetCurrentPlayer().getEquipments().get(i).toString();
                s=s.substring(s.indexOf('.')+1,s.indexOf('@'));
                lv_items.getItems().add(s);
            }
        }

        //A felhasznalhato mar megcraftolt agensek hozzaadasa
        if(Controller.getGameController().GetCurrentPlayer().getAgentStorage().size()!=0){
            for (int i=0; i<Controller.getGameController().GetCurrentPlayer().getAgentStorage().size();i++){
                String s=Controller.getGameController().GetCurrentPlayer().getAgentStorage().get(i).toString();
                s=s.substring(s.indexOf('.')+1,s.indexOf('@'));
                lv_items.getItems().add(s);
            }
        }
        //  aminoszam  hozzadasa a Virologus itemjeihez
        if(Controller.getGameController().GetCurrentPlayer().getAmino()!=0)
            lv_items.getItems().add("Amino: " +Controller.getGameController().GetCurrentPlayer().getAmino());
        //  nukelszam  hozzadasa a Virologus itemjeihez
        if(Controller.getGameController().GetCurrentPlayer().getNukleo()!=0)
            lv_items.getItems().add("Nukleo: "+Controller.getGameController().GetCurrentPlayer().getNukleo());

        if(Controller.getGameController().GetCurrentPlayer().getLearnedAgents().size()!=0){
            for (int i=0; i<Controller.getGameController().GetCurrentPlayer().getLearnedAgents().size();i++){
                String s=Controller.getGameController().GetCurrentPlayer().getLearnedAgents().get(i).toString();
                s=s.substring(s.indexOf('.')+1,s.indexOf('@'));
                ch_craftables.getItems().add(s);
            }
        }

    }

}



//asd