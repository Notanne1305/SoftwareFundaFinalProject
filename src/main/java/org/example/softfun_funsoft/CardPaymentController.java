package org.example.softfun_funsoft;

import com.jfoenix.controls.JFXComboBox;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.payment.Card;
import org.example.softfun_funsoft.singleton.Order;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CardPaymentController implements Initializable {
    @FXML
    private TextField cardCVCField;

    @FXML
    private TextField cardHolderNameField;

    @FXML
    private TextField cardMMYYField;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField emailField;

    @FXML
    private Label grandTotal;

    @FXML
    private TextArea orderItemsArea;

    @FXML
    private JFXComboBox<String> regionComboBox;

    @FXML
    private TextField zipField;

    @FXML
    private ProgressIndicator progressIndicator;


public void submit() {
    if (emailField.getText().isEmpty() || cardNumberField.getText().isEmpty() || cardHolderNameField.getText().isEmpty() || cardMMYYField.getText().isEmpty() || cardCVCField.getText().isEmpty() || zipField.getText().isEmpty() || regionComboBox.getValue() == null) {
        showError("Please fill out all fields");
    } else {
        Platform.runLater(() -> progressIndicator.setVisible(true));
        Card cardPayment = new Card();
        Order order = Order.getInstance();
        new Thread(() -> {
            try {
                Charge charge = cardPayment.createCharge("tok_visa", (int) order.getGrandTotal(), emailField.getText(), cardHolderNameField.getText(), cardNumberField.getText(), cardCVCField.getText(), cardMMYYField.getText(), regionComboBox.getValue(), zipField.getText());
                System.out.println(charge);
            } catch (IllegalArgumentException e) {
                Platform.runLater(() -> showError(e.getMessage()));
            } catch (StripeException e) {
                Platform.runLater(() -> showError(e.getMessage()));
            } finally {
                Platform.runLater(() -> progressIndicator.setVisible(false));
            }
        }).start();
    }
}
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String generateReceiptLayout(Order order) {
        StringBuilder receipt = new StringBuilder();
        for (Food food : order.getOrderItems()) {
            receipt.append(String.format("%s x%d - PHP %.2f\n", food.getName(), food.getQuantity(), food.getPrice() * food.getQuantity()));
        }
        return receipt.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Order order = Order.getInstance();

        regionComboBox.getItems().addAll(
                Arrays.stream(Locale.getISOCountries())
                        .map(countryCode -> new Locale("", countryCode).getDisplayCountry())
                        .collect(Collectors.toList())
        );

        orderItemsArea.setText(generateReceiptLayout(order));
        grandTotal.setText("Grand Total: " + order.getGrandTotal());
    }
}