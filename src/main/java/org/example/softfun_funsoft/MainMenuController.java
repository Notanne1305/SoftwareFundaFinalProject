package org.example.softfun_funsoft;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.model.FoodCategory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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

    @FXML
    private TextField searchBar;

    @FXML
    private Label mainHeader;

    @FXML
    private TextField quantity;

    @FXML
    private Button addQuantity;
    @FXML
    private Button subtractQuantity;



    private MyItemListener myItemListener;
    private MyCategoryListener myCategoryListener;
    private List<Food> foods = new ArrayList<>();
    private List<FoodCategory> categories = new ArrayList<>();
    private List<Food> itemByCategory = new ArrayList<>();


    private int currentQuantity = 1;
    Timer timer = new Timer();
    Runnable embedFoodTask = () -> {
        String searchName = searchBar.getText().toLowerCase();
        embedMatchingFood(searchName);
    };


    public void setAddQuantity(){
        currentQuantity++;
        quantity.setText(String.valueOf(currentQuantity));
    }

    public void setSubtractQuantity(){
        if(currentQuantity > 1){
            currentQuantity--;
            quantity.setText(String.valueOf(currentQuantity));
        }
    }

    public void setAddToCart(){
        System.out.println(currentQuantity);
    }

    private void embedMatchingFood(String searchName){
        ArrayList<Food> matchingFoods = new ArrayList<>();

        int column = 0;
        int row = 1;

        grid.getChildren().clear();

        try {

            for (Food food : foods) {
                if (food.getName().toLowerCase().contains(searchName.toLowerCase())) {
                    matchingFoods.add(food);
                }
            }

            if(searchName.isEmpty()){
                mainHeader.setText("All Time Favourites");
            }else if(!matchingFoods.isEmpty()){

                mainHeader.setText(matchingFoods.get(0).getCategory());
            }else{
                mainHeader.setText("No Matches Found");
            }


            for (Food matchingFood : matchingFoods) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
                AnchorPane pane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(matchingFood, myItemListener);

                if (column == 4) {
                    column = 0;
                    row++;
                }
                grid.add(pane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(pane, new Insets(10));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void getSearch() {
        searchBar.setOnKeyReleased(event -> {
            timer.cancel();
            timer.purge();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(embedFoodTask);
                }
            }, 500);
        });
    }
    private List<Food> getData(){
        List<Food> foods = new ArrayList<>();
        Food food;

        food = new Food();
        food.setName("Big Burger");
        food.setCategory("Burger");
        food.setPrice(100.0);
        food.setImgSrc("/pic_resources/Big Burger.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Cheese Burger");
        food.setCategory("Burger");
        food.setPrice(100.0);
        food.setImgSrc("/pic_resources/Cheese Burger.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Chicken");
        food.setCategory("Chicken");
        food.setPrice(100.0);
        food.setImgSrc("/pic_resources/Chicken.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);


        food = new Food();
        food.setName("Spicy Chicken");
        food.setCategory("Chicken");
        food.setPrice(100.0);
        food.setImgSrc("/pic_resources/Chicken Spicy.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        return foods;

    }

    private List<FoodCategory> getCategories(){
        List<FoodCategory> categories = new ArrayList<>();
        FoodCategory category;


        category = new FoodCategory();
        category.setName("All Time Favourites");
        category.setImgSrc("/pic_resources/Chicken.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);

        category = new FoodCategory();
        category.setName("Chicken");
        category.setImgSrc("/pic_resources/Chicken.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);

        category = new FoodCategory();
        category.setName("Burger");
        category.setImgSrc("/pic_resources/Big Burger.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);


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
                itemController.setData(categories.get(i), myCategoryListener);

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



            }
        }catch(IOException e){
            e.printStackTrace();

        }

    }

    public List<Food> getItemsByCategory(String category){
        List<Food> itemByCategory = new ArrayList<>();
        if(category.equals("All Time Favourites")){
            return foods;
        }
        for(Food food: foods){

            if(food.getCategory().toLowerCase().equals(category.toLowerCase())){
//                System.out.println("This is for debugging: " + food.getCategory() + " " + category);

                itemByCategory.add(food);
            }
        }

        return itemByCategory;

    }

    public void embedCategoricalItems(){
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < itemByCategory.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(itemByCategory.get(i), myItemListener);

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

    myCategoryListener = new MyCategoryListener() {
        @Override
        public void onclickListener(FoodCategory foodCategory) {
            mainHeader.setText(foodCategory.getName());
            grid.setAlignment(Pos.TOP_CENTER);
            grid.getChildren().clear();
            itemByCategory.clear();
            itemByCategory.addAll(getItemsByCategory(foodCategory.getName()));
            embedCategoricalItems();
        }
    };




        embedItems();
        embedCategories();






    }
}
