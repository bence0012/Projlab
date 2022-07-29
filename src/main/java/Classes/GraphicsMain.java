package Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


//A grafikai osztálykezelők main függvénye

public class GraphicsMain extends Application {

    // Ehhez rendelei hozzá az összes ablakot
    static AnchorPane root;

    //Az ablakaink
    static List<Pane> panes = new ArrayList<>();

    //Az aktuálisan megjelenített ablak indexe
    private  static  int idx_cur = 0;

    /**
     * Main függvény
     *
     * @return void
     */

    public static void main(String[] args) {
        launch(args);
    }

    public static void SetPane(int idx) { //Ez a fv. az ablakok közötti átjárást valósítja meg
        //Kivesszük az aktuálisan megjelenített ablakot és a helyére betesszük a paraméterben kapott új ablakot
        root.getChildren().remove(panes.get(idx_cur));
        root.getChildren().add(panes.get(idx));
      //  root.getChildren().get(1).
        idx_cur = idx;
    }


    /**
     *Elindítja a játékot a megfelelő stage megadás mellett
     *
     * @return void
     */

    @Override
    public void start(Stage stage) {
        try{
            //Az fxml fajlok betöltése
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Anchor.fxml")));
            FXMLLoader vi = new FXMLLoader(getClass().getResource("VirologistInteraction.fxml"));
            FXMLLoader gv = new FXMLLoader(getClass().getResource("GameView.fxml"));
            panes.add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml"))));
            panes.add(gv.load());
            panes.add(vi.load());

            //A működési elemek beállítása
            GameViewController gvc = gv.getController();
            VirologistInteractionController vic = vi.getController();
            gvc.viController = vic;
            vic.gVC=gvc;
            panes.add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameOver.fxml"))));
            root.getChildren().add(panes.get(0));
            Scene scene = new Scene(root, 600, 400);
            //A stage-hez beállítja a megfelelő ablakot
            stage.setTitle("Game");
            stage.setScene(scene);
            stage.show();


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
