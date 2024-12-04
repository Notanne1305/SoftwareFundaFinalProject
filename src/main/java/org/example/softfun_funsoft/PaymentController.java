package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.softfun_funsoft.lang.LangCheck;
import org.example.softfun_funsoft.singleton.Order;
import org.example.softfun_funsoft.utils.SoundManager;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PaymentController implements Initializable {
    @FXML
    private StackPane rootStackPane;

    @FXML
    private Button cashBTN;

    private MediaPlayer soundPlayer;


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
                Parent newRoot = FXMLLoader.load(getClass().getResource("CardPayment.fxml"));

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



        }else{
            try {
                Parent newRoot = FXMLLoader.load(getClass().getResource("Receipt.fxml"));

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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SoundManager.playPaymentType();

    }
}
