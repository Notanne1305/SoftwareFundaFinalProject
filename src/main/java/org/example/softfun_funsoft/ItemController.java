package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.softfun_funsoft.model.Food;

public class ItemController {

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    private Food food;
    public void setData(Food food){
        System.out.println(food.getName() + " " + food.getPrice() + " " + food.getImgSrc());
        this.food = food;
        nameLabel.setText(food.getName());
        priceLabel.setText("₱" + food.getPrice());
        Image image = new Image(getClass().getResourceAsStream(food.getImgSrc()));
        img.setImage(image);
    }



}
