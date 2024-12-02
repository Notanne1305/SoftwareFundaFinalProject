package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.softfun_funsoft.lang.LangCheck;
import org.example.softfun_funsoft.singleton.Order;

import java.io.IOException;
import java.util.Objects;
import java.io.File;

public class DineInOrTakeOutController {

    @FXML
    private Button dineIn;

    @FXML
    private Button takeOut;

    private MediaPlayer soundPlayer;

    public void initialize(){
        playSound();//Select a place to Eat
    }


    public void dineIn() {
        Order order = Order.getInstance();
        order.setDineIn(true);
        playProceedMenu();//Proceed to Menu
        proceedToMainMenu();
    }

    public void takeOut() {
        Order order = Order.getInstance();
        order.setDineIn(false);
        playProceedMenu();//Proceed to Menu
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

    private void playSound() {
        String soundPath = "/sounds/place_Eng.mp3"; // Adjusted to classpath-relative path
        try {
            Media sound = new Media(Objects.requireNonNull(getClass().getResource(soundPath)).toString());
            soundPlayer = new MediaPlayer(sound);

            if (LangCheck.isEnglish()) {
                soundPlayer.play();
            } else {
                System.out.println("Sound not played for current language: " + LangCheck.getLanguage());
            }
        } catch (NullPointerException e) {
            showError("Sound file not found at: " + soundPath);
        }
    }

    private void playProceedMenu() {
        String soundPath = "/sounds/menu_Eng.mp3"; // Adjusted to classpath-relative path
        try {
            Media sound = new Media(Objects.requireNonNull(getClass().getResource(soundPath)).toString());
            soundPlayer = new MediaPlayer(sound);

            if (LangCheck.isEnglish()) {
                soundPlayer.play();
            } else {
                System.out.println("Sound not played for current language: " + LangCheck.getLanguage());
            }
        } catch (NullPointerException e) {
            showError("Sound file not found at: " + soundPath);
        }
    }



    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }


    }
