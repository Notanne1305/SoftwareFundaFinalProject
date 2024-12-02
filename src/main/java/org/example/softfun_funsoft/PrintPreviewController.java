package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.print.PrinterJob;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrintPreviewController implements Initializable {
    @FXML
    private AnchorPane previewAnchorPane;

    private AnchorPane receiptAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // This method will be called after the FXML file is loaded
    }

    public void setReceiptAnchorPane(AnchorPane receiptAnchorPane) {
        this.receiptAnchorPane = receiptAnchorPane;
        AnchorPane clonedPane = cloneAnchorPane(receiptAnchorPane);
        previewAnchorPane.getChildren().add(clonedPane);
    }

    private AnchorPane cloneAnchorPane(AnchorPane original) {
        AnchorPane clone = new AnchorPane();
        List<Node> tempList = new ArrayList<>();
        for (Node node : original.getChildren()) {
            Node clonedNode = cloneNode(node);
            if (clonedNode != null) {
                tempList.add(clonedNode);
            }
        }
        clone.getChildren().addAll(tempList);
        return clone;
    }

    private Node cloneNode(Node node) {
        if (node instanceof Label) {
            Label originalLabel = (Label) node;
            Label clonedLabel = new Label(originalLabel.getText());
            clonedLabel.setStyle(originalLabel.getStyle());
            clonedLabel.setLayoutX(originalLabel.getLayoutX());
            clonedLabel.setLayoutY(originalLabel.getLayoutY());
            return clonedLabel;
        }
        // Add more cases for other node types if necessary
        // For unsupported node types, return a placeholder or null
        return null;
    }

    @FXML
    private void printReceipt() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(previewAnchorPane.getScene().getWindow())) {
            boolean success = job.printPage(receiptAnchorPane);
            if (success) {
                job.endJob();
                Stage stage = (Stage) previewAnchorPane.getScene().getWindow();
                stage.close();
            }
        }
    }
}