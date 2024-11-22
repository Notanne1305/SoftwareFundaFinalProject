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
    private MyItemListener myItemListener;

    public void onClicked(){
        myItemListener.onclickListener(food);
    }
    public void setData(Food food, MyItemListener myItemListener){
        this.food = food;
        this.myItemListener = myItemListener;
        System.out.println(food.getName() + " " + food.getPrice() + " " + food.getImgSrc());
        nameLabel.setText(food.getName());
        priceLabel.setText("₱" + food.getPrice());
        Image image = new Image(getClass().getResourceAsStream(food.getImgSrc()));
        img.setImage(image);
    }



}
