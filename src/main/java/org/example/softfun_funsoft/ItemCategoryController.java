package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.softfun_funsoft.model.FoodCategory;

public class ItemCategoryController {
    @FXML
    private Label categoryLabel;

    @FXML
    private ImageView img;


    public void setData(FoodCategory food){
        System.out.println(food.getName() + " " + food.getImgSrc());
        categoryLabel.setText(food.getName());
        img.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(food.getImgSrc())));

    }
}
