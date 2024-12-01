package org.example.softfun_funsoft;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;
import com.jfoenix.controls.JFXComboBox;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.payment.Card;
import org.example.softfun_funsoft.singleton.Order;

import java.io.IOException;
import java.net.URL;
import java.util.*;
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
    private AnchorPane progressPane;

    @FXML
    private JFXComboBox<String> regionComboBox;

    @FXML
    private TextField zipField;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Button cancelBTN;

    //TODO: implement another scene for the receipt

    public void submit() {
        if (emailField.getText().isEmpty() || cardNumberField.getText().isEmpty() || cardHolderNameField.getText().isEmpty() || cardMMYYField.getText().isEmpty() || cardCVCField.getText().isEmpty() || zipField.getText().isEmpty() || regionComboBox.getValue() == null) {
            showError("Please fill out all fields");
        } else {
            Platform.runLater(() -> progressPane.setVisible(true));
            Card cardPayment = new Card();
            Order order = Order.getInstance();
            new Thread(() -> {
                try {
                    Charge charge = cardPayment.createCharge("tok_visa", (int) order.getGrandTotal(), emailField.getText(), cardHolderNameField.getText(), cardNumberField.getText(), cardCVCField.getText(), cardMMYYField.getText(), regionComboBox.getValue(), zipField.getText());
                    String chargeJson = charge.toJson();
//                    System.out.println(chargeJson);
                    JSONObject jsonObject = new JSONObject(chargeJson);
                    System.out.println(jsonObject.get("receipt_url"));
                } catch (IllegalArgumentException e) {
                    Platform.runLater(() -> showError(e.getMessage()));
                } catch (StripeException e) {
                    Platform.runLater(() -> showError(e.getMessage()));
                } finally {
                    Platform.runLater(() -> progressPane.setVisible(false));
                }
            }).start();
        }
    }

    public void setCancelBTN() throws IOException {
        Stage currentStage = (Stage) cancelBTN.getScene().getWindow();
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