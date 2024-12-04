package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
    private StackPane rootStackPane;

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
            Node currentRoot = rootStackPane.getChildren().get(rootStackPane.getChildren().size() - 1);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            Label loadingLabel = new Label("Loading Menu...");
            loadingLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: gray;");
            loadingLabel.setOpacity(0);
            rootStackPane.getChildren().add(loadingLabel);

            FadeTransition loadingFadeIn = new FadeTransition(Duration.millis(200), loadingLabel);
            loadingFadeIn.setFromValue(0.0);
            loadingFadeIn.setToValue(1.0);

            fadeOut.setOnFinished(e -> {
                loadingFadeIn.play();

                new Thread(() -> {
                    try {
                        Parent newRoot = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

                        // Back to JavaFX Application Thread to update the UI
                        javafx.application.Platform.runLater(() -> {
                            rootStackPane.getChildren().removeAll(currentRoot, loadingLabel);
                            newRoot.setOpacity(0);
                            rootStackPane.getChildren().add(newRoot);

                            // Fade in the new scene
                            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newRoot);
                            fadeIn.setFromValue(0.0);
                            fadeIn.setToValue(1.0);
                            fadeIn.play();
                        });
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Handle exception properly in your application
                    }
                }).start();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
