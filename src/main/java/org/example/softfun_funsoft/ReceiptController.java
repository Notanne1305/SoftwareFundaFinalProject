package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.singleton.CardReceiptData;
import org.example.softfun_funsoft.singleton.Order;

import javafx.print.PrinterJob;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {
    @FXML
    private AnchorPane receiptAnchorPane;

    @FXML
    private VBox itemsContainer;

    @FXML
    private Label cardHolderName;

    @FXML
    private Label cardType;

    @FXML
    private Label grandTotal;

    @FXML
    private Label orderID;

    @FXML
    private Label paymentDate;

    @FXML
    private Label paymentType;

    @FXML
    private Label subTotal;

    @FXML
    private Label orderType;

    @FXML
    private Label timer;
    private int timeRemaining = 10;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Order order = Order.getInstance();
        CardReceiptData receiptData = CardReceiptData.getInstance();

        if(order.getPaymentType().equals("Cash")) {
            paymentType.setText("Payment Type: Cash");
            cardType.setVisible(false);
            cardHolderName.setVisible(false);
            orderID.setText("Order ID: " + order.getOrderID());

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            paymentDate.setText("Payment Date: " + formattedDateTime);

            grandTotal.setText("PHP " + (order.getGrandTotal() + 36));
            subTotal.setText("PHP " + order.getGrandTotal());
            orderType.setText("Order Type: " + (order.isDineIn() ? "Dine In" : "Take out"));
        }else{
            orderID.setText("Order ID: " + receiptData.getReceiptId());
            paymentDate.setText("Payment Date: " + receiptData.getPaymentDateTime());
            cardHolderName.setText("Card Holder Name: " + receiptData.getCardHolderName());
            cardType.setText("Card Type: " + receiptData.getCardType());
            paymentType.setText("Payment Type: Card");
            grandTotal.setText("PHP " + (order.getGrandTotal() + 36));
            subTotal.setText("PHP " + order.getGrandTotal());
            orderType.setText("Order Type: " + (order.isDineIn() ? "Dine In" : "Take out"));

        }

        for (Food food : order.getOrderItems()) {
            Label itemLabel = new Label(String.format("%s x%d - PHP %.2f", food.getName(), food.getQuantity(), food.getPrice() * food.getQuantity()));
            itemLabel.setStyle("-fx-font-size: 15px; -fx-font-family: Monospaced");
            itemsContainer.getChildren().add(itemLabel);
        }

    }

    public void startTimer() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    // Update the label every second
                    if (timeRemaining > 0) {
                        timeRemaining--;
                        timer.setText(timeRemaining + "s");
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
            Stage currentStage = (Stage) receiptAnchorPane.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bye.fxml"));
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

    public void printReceipt() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(receiptAnchorPane.getScene().getWindow())) {
            boolean success = job.printPage(receiptAnchorPane);
            if (success) {
                job.endJob();
            }
        }
    }
}