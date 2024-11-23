package org.example.softfun_funsoft;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import javafx.util.Duration;

public class NotificationExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button notifyButton = new Button("Show Notification");
        notifyButton.setOnAction(e -> showNotification());

        VBox root = new VBox(10, notifyButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ControlFX Notification Example");
        primaryStage.show();
    }

    private void showNotification() {
        Notifications notificationBuilder = Notifications.create()
                .title("Sample Notification")
                .text("This is a custom notification!")
                .graphic(null) // You can replace null with a Node for custom graphic
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT) // Change to your desired position
                .onAction(e -> System.out.println("Notification clicked!"));

        notificationBuilder.showInformation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
