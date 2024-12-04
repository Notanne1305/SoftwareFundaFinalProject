package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ByeController implements Initializable {
    @FXML
    private StackPane rootStackPane;

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
            Parent newRoot = FXMLLoader.load(getClass().getResource("StartUp.fxml"));

            Node currentRoot = rootStackPane.getChildren().get(rootStackPane.getChildren().size() - 1);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {

                rootStackPane.getChildren().remove(currentRoot);
                rootStackPane.getChildren().add(newRoot);

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
