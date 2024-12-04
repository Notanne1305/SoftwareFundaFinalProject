package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.softfun_funsoft.lang.LangCheck;
import org.example.softfun_funsoft.lang.Language;
import org.example.softfun_funsoft.singleton.Order;
import org.example.softfun_funsoft.utils.SoundManager;

import java.io.IOException;
import java.util.Objects;
import java.io.File;

public class DineInOrTakeOutController {

    @FXML
    private Button dineIn;

    @FXML
    private Button takeOut;

//    @FXML
//    private Button TagalogBTN;
//
//    @FXML Button EnglishBTN;
//
//    @FXML
//    private Label LabelDineIn;
//
//    @FXML
//    private Label LabelTakeOut;
//
//    @FXML
//    private Label LabelDIorTO;
//    Added if language is implemented
    private MediaPlayer soundPlayer;

    public void initialize(){
        SoundManager.playSelectPlaceSound();//Select a place to Eat
    }


    public void dineIn() {
        Order order = Order.getInstance();
        order.setDineIn(true);
        SoundManager.playProceedMenuSound();//Proceed to Menu
        proceedToMainMenu();
    }

    public void takeOut() {
        Order order = Order.getInstance();
        order.setDineIn(false);
        SoundManager.playProceedMenuSound();//Proceed to Menu
        proceedToMainMenu();
    }

    private void proceedToMainMenu() {
        try {
            Stage currentStage = (Stage) dineIn.getScene().getWindow();
            Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
            Scene currentScene = currentStage.getScene();

            // Create a fade-out transition for the current scene
            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentScene.getRoot());
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Set an event handler to change the scene after the fade-out
            fadeOut.setOnFinished(e -> {
                currentScene.setRoot(newRoot);

                // Create a fade-in transition for the new scene
                FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();


            });

            fadeOut.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
