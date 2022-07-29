package Classes;

import Agents.Agent;
import Equipments.*;
import GraphicClasses.Controller;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Interakciós ablakkezelő osztály, mellyel interaktálást végezhetünk a Virolgusok között
 *
 * Ide tartozik a Steal, illetve a támadások, de ezen keresztül adhatunk be magunknak Vakcinát vagy
 * ha úgy adódik akkkor Vírust
 */
public class VirologistInteractionController implements Initializable {

    //A gameViewController egy példánya mely az adatok megfelelő betöltéséért felelős
public GameViewController gVC;

    //kiválasztott elem lopáshoz
    private String selectedItem;
    @FXML
    public ListView<String> lv_attackList; //A lista, mely a támadásra használható item-eket sorolja fel
    @FXML
    public Button btn_cancel; //Gomb ,mellyel kilépünk az interakció képernyőből
    @FXML
    public Button btn_steal; //Gomb, mellyel véghezvisszük a lopás műveletét
    @FXML
    public Button btn_attack;//Gomb, mellyel az adottt támadásra szánt itemmel elindítjuk a támadást
    @FXML
    public ListView<String> lv_effectList; //Felsorolja, hogy az adott virológusra milyen hatások érvényesek
    @FXML
    public  ListView<String> lv_stealableList;// Felsorolja hogy milyen eszkközük lophatóak el az adott virológustól
    @FXML
    private Label l_selected; //A kiválasztott elem



    /**
     * Megnyomása esetén visszavált a GameView képernyőre
     *
     * @return void
     */

    public void OnCancelButtonPressed(ActionEvent ae){
        load();
        gVC.Reload();
        GraphicsMain.SetPane(1);
    }


    /**
     * Megnyomás esetén ellopja az adott virológustól az egy kiválasztott itemet
     *
     * @return void
     */
    public void OnStealButtonPressed(ActionEvent actionEvent) {

        if(selectedItem==null) return;
        ArrayList<Equipment> otherVEquipment=Controller.getVirologist().getEquipments();
        for (Equipment e: otherVEquipment) {
            String s=e.getClass().toString();
            s=s.substring(s.indexOf(".")+1);  //megfelelő kiírásért a String formázása
            //equipmentek hozzáadása
            if (selectedItem.equals(s)){
                Controller.getGameController().GetCurrentPlayer().StealEquipment(Controller.getVirologist(),e);
                load();

                Controller.getGameController().NextPlayer();
                GraphicsMain.SetPane(1);
                gVC.Reload();
                return;

            }
        }
//Agensek hozzáadása
        ArrayList<Agent> otherVAgents=Controller.getVirologist().getAgentStorage();
        for (Agent e: otherVAgents) {
            String s=e.getClass().toString();
            s=s.substring(s.indexOf(".")+1);
            lv_stealableList.getItems().add(s);
            if (selectedItem.equals(s)){
                Controller.getGameController().GetCurrentPlayer().StealAgent(Controller.getVirologist(),e);
                load();
                Controller.getGameController().NextPlayer();
                GraphicsMain.SetPane(1);
                gVC.Reload();
                return;
            }
        }
//Amino hozzáadása
        if (selectedItem.equals("Amino")) {
            Controller.getGameController().GetCurrentPlayer().StealAmino(Controller.getVirologist());
            load();
            Controller.getGameController().NextPlayer();
            GraphicsMain.SetPane(1);
            gVC.Reload();
            return;
        }
        //nukleo hozzáadása
        if (selectedItem.equals("Nukleo")) {
            Controller.getGameController().GetCurrentPlayer().StealNukleo(Controller.getVirologist());
            load();
            Controller.getGameController().NextPlayer();
            GraphicsMain.SetPane(1);
            gVC.Reload();
            return;
        }
        gVC.Reload();
    }
    /**
     * Megnyomásakor megtámadja az adott kiválasztott itemmel a kivá-
     * lsztott virológust
     *
     * @return void
     */
    public void OnAttackButtonPressed(ActionEvent actionEvent) {
        if(selectedItem==null) return;
        ArrayList<Equipment> currentVEquipment = Controller.getGameController().GetCurrentPlayer().getEquipments();
        for (Equipment e : currentVEquipment) {
            String s = e.getClass().toString();
            s = s.substring(s.indexOf(".") + 1);
            if (s.equals(selectedItem)) {
                Controller.getGameController().GetCurrentPlayer().AttackEquipment(Controller.getVirologist(), e);
                load();

                Controller.getGameController().NextPlayer();
                GraphicsMain.SetPane(1);
                gVC.Reload();
                return;
            }
        }

        ArrayList<Agent> currentVAgents = Controller.getGameController().GetCurrentPlayer().getAgentStorage();
        for (Agent e : currentVAgents) {
            String s = e.getClass().toString();
            s = s.substring(s.indexOf(".") + 1);
            if (s.equals(selectedItem)) {
                Controller.getGameController().GetCurrentPlayer().Attack(Controller.getVirologist(), e);
                load();
                Controller.getGameController().NextPlayer();
                GraphicsMain.SetPane(1);
                gVC.Reload();
                return;
            }
        }
    }

    //Kiválasztás a támadáshoz
    ChangeListener<String> attackSelected = (observableValue, s, t1) -> {

        selectedItem = lv_attackList.getSelectionModel().getSelectedItem();
        l_selected.setText(selectedItem);
    };

    //kiválasztás lekezelése a lopáshoz
    ChangeListener<String> stealSelected = (observableValue, s, t1) -> {

        selectedItem = lv_stealableList.getSelectionModel().getSelectedItem();
        l_selected.setText(selectedItem);
    };
    /**
     * az ablak init függvénye
     *
     * @return void
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//Az adatok feltölése külön függvénnyel
        load();



        lv_attackList.getSelectionModel().selectedItemProperty().addListener(attackSelected);
        lv_stealableList.getSelectionModel().selectedItemProperty().addListener(stealSelected);

    }

    /**
     * A betöltő függvény mellyel a listákat töltjük fel
     * a megfelelő adatokkal
     *
     * @return void
     */
    public  void load(){
        //Betöltéskor  a listák lenullozása
        lv_stealableList.getItems().clear();
        lv_effectList.getItems().clear();
        lv_attackList.getItems().clear();

//A megfelelő adatok egyesével való feltöltése
        ArrayList<Equipment> currentVEquipment=Controller.getGameController().GetCurrentPlayer().getEquipments();
        for (Equipment e: currentVEquipment) {
            String s=e.getClass().toString();
            s=s.substring(s.indexOf(".")+1);
            if(s.equals("Axe"))
                lv_attackList.getItems().add("Axe");
        }

        //ágensek feltöltése
        ArrayList<Agent> currentVAgents=Controller.getGameController().GetCurrentPlayer().getAgentStorage();
        for (Agent e: currentVAgents) {
            String s=e.getClass().toString();
            s=s.substring(s.indexOf(".")+1);
            lv_attackList.getItems().add(s);
        }

        //A hatás lsita feltöltése
        ArrayList<Agent> otherVEffects= Controller.getVirologist().getAffectedBy();
        for (Agent e: otherVEffects) {
            String s=e.getClass().toString();
            s=s.substring(s.indexOf(".")+1);
            lv_effectList.getItems().add(s);
        }

        //Az equipmentek hozzáadása a lopásra kész itemek közé
        ArrayList<Equipment> otherVEquipment=Controller.getVirologist().getEquipments();
        for (Equipment e: otherVEquipment) {
            String s=e.getClass().toString();
            s=s.substring(s.indexOf(".")+1);
            lv_stealableList.getItems().add(s);
        }
        //Az ágensek hozzáadása a lopásra kész itemek közé
        ArrayList<Agent> otherVAgents=Controller.getVirologist().getAgentStorage();
        for (Agent e: otherVAgents) {
            String s=e.getClass().toString();
            s=s.substring(s.indexOf(".")+1);
            lv_stealableList.getItems().add(s);
        }
//Az amino és nukleo kiírása amennyiben az adott virlógusnál van
        if (Controller.getVirologist().getAmino()>0)
            lv_stealableList.getItems().add("Amino");
        if (Controller.getVirologist().getNukleo()>0)
            lv_stealableList.getItems().add("Nukleo");
    }





}
