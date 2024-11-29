package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.softfun_funsoft.listener.MyCartItemListener;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.singleton.Cart;

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

    @FXML
    private Label itemNumber;

    private Food food;
    private MyCartItemListener myCartItemListener;

    private Cart cart;

    private int number;

    public void setData(Food food, MyCartItemListener myCartItemListener){
        this.food = food;
        this.myCartItemListener = myCartItemListener;
        this.cart = Cart.getInstance();
        number = cart.getCartItems().indexOf(food) + 1;
        itemName.setText(food.getName());
        itemNumber.setText(String.valueOf(number));
        itemQuantity.setText(String.valueOf(food.getQuantity()));
        itemPrice.setText("PHP " + food.getPrice());
        totalPrice.setText("PHP " + (food.getPrice() * food.getQuantity()));
        img.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(food.getImgSrc())));


        removeBTN.setOnAction(e ->{
            myCartItemListener.onRemoveItem(food);
        });

    }
}
