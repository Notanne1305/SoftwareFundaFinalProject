package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.singleton.CardReceiptData;
import org.example.softfun_funsoft.singleton.Cart;

import javafx.print.PrinterJob;
import org.example.softfun_funsoft.singleton.Order;

import java.io.IOException;
import java.net.URL;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Order order = Order.getInstance();
        CardReceiptData receiptData = CardReceiptData.getInstance();


        orderID.setText("Order ID: " + receiptData.getReceiptId());
        paymentDate.setText("Payment Date: " + receiptData.getPaymentDateTime());
        cardHolderName.setText("Card Holder Name: " + receiptData.getCardHolderName());
        cardType.setText("Card Type: " + receiptData.getCardType());
        paymentType.setText("Payment Type: Card");
        grandTotal.setText("PHP " + (order.getGrandTotal() + 36));
        subTotal.setText("PHP " + order.getGrandTotal());
        orderType.setText("Order Type: " + (order.isDineIn() ? "Dine In": "Take out"));




        for (Food food : order.getOrderItems()) {
            Label itemLabel = new Label(String.format("%s x%d - PHP %.2f", food.getName(), food.getQuantity(), food.getPrice() * food.getQuantity()));
            itemLabel.setStyle("-fx-font-size: 15px; -fx-font-family: Monospaced");
            itemsContainer.getChildren().add(itemLabel);
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

