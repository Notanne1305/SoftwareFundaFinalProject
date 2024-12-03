package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ByeController implements Initializable {

    private Timeline timeline;
    private int timeRemaining = 5;

    @FXML
    private AnchorPane mainAnchorpane;



    private void startTimer() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    // Update the label every second
                    if (timeRemaining > 0) {
                        timeRemaining--;
                    } else {
                        // Call the method when the timer reaches zero
                        changeScene();
                        timeline.stop(); // Stop the timer when finished
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        timeline.play(); // Start the timer
    }

    private void changeScene() {
        System.out.println("changing scenes");
        try {
            Stage currentStage = (Stage) mainAnchorpane.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StartUp.fxml"));
            Parent newRoot = fxmlLoader.load();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTimer();
    }
}
