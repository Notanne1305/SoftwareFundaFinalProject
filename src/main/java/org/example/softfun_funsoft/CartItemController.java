package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.softfun_funsoft.listener.MyCartItemListener;
import org.example.softfun_funsoft.model.Food;

public class CartItemController {
    @FXML
    private ImageView img;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private Label itemQuantity;

    @FXML
    private Label totalPrice;

    @FXML
    private Button removeBTN;

    private Food food;
    private MyCartItemListener myCartItemListener;

    public void setData(Food food, MyCartItemListener myCartItemListener){
        this.food = food;
        this.myCartItemListener = myCartItemListener;
        itemName.setText(food.getName());
        itemQuantity.setText(String.valueOf(food.getQuantity()));
        itemPrice.setText("PHP " + food.getPrice());
        totalPrice.setText("PHP " + (food.getPrice() * food.getQuantity()));
        img.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(food.getImgSrc())));


        removeBTN.setOnAction(e ->{
            myCartItemListener.onRemoveItem(food);
        });
    }
}
