package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
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
import org.example.softfun_funsoft.utils.SoundManager;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class PaymentController {

    @FXML
    private Button cashBTN;

    private MediaPlayer soundPlayer;

    public void initialize(){
        SoundManager.playPaymentType();
    }

    public void cardPayment() {
        Order order = Order.getInstance();
        order.setPaymentType("Card");
        proceedToPaymentPage();

    }

    public void cashPayment() {
        Order order = Order.getInstance();
        order.setOrderID(new Random()
                .ints('A', 'Z' + 1)
                .limit(10)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining()));
        order.setPaymentType("Cash");
        proceedToPaymentPage();


    }

    private void proceedToPaymentPage(){
        Order order = Order.getInstance();
        if(order.getPaymentType().equals("Card")){
            try {
                Stage currentStage = (Stage) cashBTN.getScene().getWindow();
                Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CardPayment.fxml")));
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



        }else{
            try {
                Stage currentStage = (Stage) cashBTN.getScene().getWindow();
                Parent newRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Receipt.fxml")));
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

}
