package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.softfun_funsoft.ItemController;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.model.FoodCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane leftScroll;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vbox;




    private List<Food> foods = new ArrayList<>();

    private List<Food> getData(){
        List<Food> foods = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            Food food = new Food();
            food.setName("No Name");
            food.setPrice(100.0);
            food.setImgSrc("/pic_resources/Fries.jpg");
            food.setColor("#f2f2f2");

            foods.add(food);
        }
        return foods;

    }

    private List<FoodCategory> getCategories(){
        List<FoodCategory> categories = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            FoodCategory category = new FoodCategory();
            category.setName("No Name");
            category.setImgSrc("/pic_resources/Fries.jpg");
            category.setColor("#f2f2f2");

            categories.add(category);
        }
        return categories;

    }

    public void embedItems(){
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < foods.size(); i++) {
                System.out.println(foods.get(i).getName());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();


                ItemController itemController = fxmlLoader.getController();
                itemController.setData(foods.get(i));

                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

//                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid.setMaxWidth(Region.USE_PREF_SIZE);
//
//
//                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(10));

            }
        }catch(IOException e){
            e.printStackTrace();

        }
    }

    public void embedCategories(){
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < foods.size(); i++) {
                System.out.println(foods.get(i).getName());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();


                ItemController itemController = fxmlLoader.getController();
                itemController.setData(foods.get(i));

//                if (column == 4) {
//                    column = 0;
//                    row++;
//                }

                vbox.getChildren().add(anchorPane);

//                grid.add(anchorPane, column++, row);


//                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid.setMaxWidth(Region.USE_PREF_SIZE);
//
//
//                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane, new Insets(10));

            }
        }catch(IOException e){
            e.printStackTrace();

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foods.addAll(getData());
        embedItems();
        embedCategories();






    }
}
