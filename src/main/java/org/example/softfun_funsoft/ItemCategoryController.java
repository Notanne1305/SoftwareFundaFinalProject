package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.softfun_funsoft.listener.MyCategoryListener;
import org.example.softfun_funsoft.model.FoodCategory;

public class ItemCategoryController {
    @FXML
    private Label categoryLabel;

    @FXML
    private ImageView img;


    private FoodCategory foodCategory;
    private MyCategoryListener myCategoryListener;

    public void onClicked(){
        myCategoryListener.onclickListener(foodCategory);
    }


    public void setData(FoodCategory foodCategory, MyCategoryListener myCategoryListener){
        this.foodCategory = foodCategory;
        this.myCategoryListener = myCategoryListener;
//        System.out.println(foodCategory.getName() + " " + foodCategory.getImgSrc());
        categoryLabel.setText(foodCategory.getName());
        img.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream(foodCategory.getImgSrc())));

    }
}
