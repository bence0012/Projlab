package Classes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

//A befejező képernyőért felelős osztály
public class GameOverController {
    //A menübe visszalépő gomb
    public Button btn_backbobenu;
    //A programból kilépő gomb
    public Button btn_exit;

    /**
     * Visszalép a kezdőmenübe
     *
     * @return void
     */

    public void OnBackToMenuButtonPressed(ActionEvent event){
        ///Game start metódus is itt hívódjon meg
        GraphicsMain.SetPane(0);
    }

    /**
     * Kilépteti a programot
     *
     * @return void
     */

    public void OnExitButtonPressed(ActionEvent event){
        Platform.exit();
    }
}
