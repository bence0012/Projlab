package Classes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
//A főmenü ablakért felelős osztály
public class MainMenuController {

    //Új játék indítása gomb
    public Button btn_newGame;

    //Kilépő gomb
    public Button btn_exit;


    //A gomb megnyomásával átvált a megfelelő képernyőre az indításért
    public void OnNewGameButtonPressed(ActionEvent event){
        ///Game start metódus is itt hívódjon meg
        GraphicsMain.SetPane(1);
    }

    /**
     *Meghívásával kilép a programból
     *
     * @return void
     */

    public void OnExitButtonPressed(ActionEvent event){
        Platform.exit();
    }
}
