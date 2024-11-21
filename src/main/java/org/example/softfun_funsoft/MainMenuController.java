package org.example.softfun_funsoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.model.FoodCategory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {


    @FXML
    private GridPane categoryGrid;

    @FXML
    private Label confirmPanelItemname;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane leftScroll;

    @FXML
    private AnchorPane orderPanel;

    @FXML
    private Label orderPanelItemPrice;

    @FXML
    private ScrollPane scroll;





    private MyItemListener myItemListener;
    private List<Food> foods = new ArrayList<>();
    private List<FoodCategory> categories = new ArrayList<>();

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
            category.setImgSrc("/pic_resources/Nuggets.jpg");
            category.setColor("#f2f2f2");

            categories.add(category);
        }
        return categories;

    }

    public void embedItems() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < foods.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(foods.get(i), myItemListener);

                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

            scroll.setFitToWidth(true);
            scroll.widthProperty().addListener((obs, oldVal, newVal) -> {
                grid.setPrefWidth(newVal.doubleValue());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelButton(){
        orderPanel.setVisible(false);
    }

    private void setChosenFood(Food food){

    }

//    private void notification(){
//        String title = "Congratulations sir";
//        String message = "You've successfully created your first Tray Notification";
//        Notification notification = Notifications.SUCCESS;
//
//        TrayNotification tray = new TrayNotification();
//        tray.setTitle(title);
//        tray.setMessage(message);
//        tray.setNotification(notification);
//        tray.showAndWait();
//    }

    public void embedCategories(){
        int row = 1;
        int column = 0;

        try {
            for (int i = 0; i < categories.size(); i++) {
                System.out.println(categories.get(i).getName());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemCategory.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();


                ItemCategoryController itemController = fxmlLoader.getController();
                itemController.setData(categories.get(i));

                if (column == 1) {
                    column = 0;
                    row++;
                }


                categoryGrid.add(anchorPane, column++, row);
                categoryGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                categoryGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                categoryGrid.setMaxWidth(Region.USE_PREF_SIZE);


                categoryGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                categoryGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                categoryGrid.setMaxHeight(Region.USE_PREF_SIZE);
                if (i == 0) {
                    // No top margin for the first item
                    GridPane.setMargin(anchorPane, new Insets(0, 10, 10, 10));
                } else {
                    // Standard margin for all other items
                    GridPane.setMargin(anchorPane, new Insets(10));
                }


//                GridPane.setMargin(anchorPane, new Insets(10));

            }
        }catch(IOException e){
            e.printStackTrace();

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foods.addAll(getData());
        categories.addAll(getCategories());


        myItemListener = new MyItemListener() {
            @Override
            public void onclickListener(Food food) {
                System.out.println("I have been called");
//                setChosenFood(food);
                orderPanel.setVisible(true);
            }
        };



        embedItems();
        embedCategories();






    }
}
