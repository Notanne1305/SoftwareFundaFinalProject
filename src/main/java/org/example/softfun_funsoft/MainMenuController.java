package org.example.softfun_funsoft;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.softfun_funsoft.listener.MyCartItemListener;
import org.example.softfun_funsoft.listener.MyCategoryListener;
import org.example.softfun_funsoft.listener.MyItemListener;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.model.FoodCategory;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainMenuController implements Initializable {

    @FXML
    private AnchorPane mainAnchorpane;

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
    private AnchorPane addAnchorPane;

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

    @FXML
    private ImageView confirmPanelImg;

    @FXML
    private Label itemsLabel;

    @FXML
    private JFXButton addToCart;

    @FXML
    private AnchorPane cartPane;

    @FXML
    private AnchorPane proceedToCheckoutPanel;

    @FXML
    private GridPane cartGrid;

    @FXML
    private ScrollPane cartScrollPane;



    private MyItemListener myItemListener;
    private MyCategoryListener myCategoryListener;
    private MyCartItemListener myCartItemListener;
    private List<Food> foods = new ArrayList<>();
    private List<FoodCategory> categories = new ArrayList<>();
    private List<Food> itemByCategory = new ArrayList<>();

    private List<Food> cart = new ArrayList<>();

    private Food chosenFood;

    private int currentQuantity = 1;
    Timer timer = new Timer();
    Runnable embedFoodTask = () -> {
        String searchName = searchBar.getText().toLowerCase();
        embedMatchingFood(searchName);
    };

    //TODO: Implement Add to cart pane
//    private void showNotification(Food food) {
//        Node imageView = new ImageView(new Image(getClass().getResource(food.getImgSrc()).toExternalForm()));
//        Notifications notificationBuilder = Notifications.create()
//                .title("Order Received")
//                .text("You have added " + currentQuantity + " " + food.getName() + " to your cart")
//                .graphic(imageView) // You can replace null with a Node for custom graphic
//                .hideAfter(Duration.seconds(3))
//                .position(Pos.BOTTOM_RIGHT) // Change to your desired position
//                .onAction(e -> System.out.println("Notification clicked!"));
//
//        notificationBuilder.showInformation();
//    }

    public void showNotification(Food food) {
        HBox notification = new HBox();
        notification.setStyle("-fx-background-color: #333; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        notification.setSpacing(10);

        ImageView foodImage = new ImageView(new Image(getClass().getResource(food.getImgSrc()).toExternalForm()));
        foodImage.setFitWidth(70);
        foodImage.setFitHeight(70);




        Label foodDetails = new Label("Added " + food.getName() + " to cart\nPrice: ₱" + food.getPrice());
        foodDetails.setStyle("-fx-text-fill: white;");

        notification.getChildren().addAll(foodImage, foodDetails);

        notification.setLayoutX(mainAnchorpane.getWidth() - 250);
        notification.setLayoutY(mainAnchorpane.getHeight() - 190);

        mainAnchorpane.getChildren().add(notification);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), notification);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> mainAnchorpane.getChildren().remove(notification));

        pause.setOnFinished(event -> fadeOut.play());
        pause.play();
    }
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
        orderPanel.setVisible(false);
        addAnchorPane.setVisible(false);
        chosenFood.setQuantity(currentQuantity);
        showNotification(chosenFood);
        cart.add(chosenFood);
        itemsLabel.setText(String.valueOf(cart.size()) + " item/s in the cart");
        System.out.println(chosenFood.getQuantity());
        //TODO: Implement Add to cart pane and functionality.

    }

    public void showCart(){
        //TODO: Implement a cart pane, that shows the items in the cart. e.g a tableview
        cartGrid.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < cart.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CartItem.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                CartItemController itemController = fxmlLoader.getController();
                itemController.setData(cart.get(i), myCartItemListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                cartGrid.add(anchorPane, column++, row);
                cartGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                cartGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);

//                GridPane.setMargin(anchorPane, new Insets(25));
            }

            cartScrollPane.setFitToWidth(true);
            cartScrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                cartGrid.setPrefWidth(newVal.doubleValue());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


        addAnchorPane.setVisible(false);
        cartPane.setVisible(true);
        proceedToCheckoutPanel.setVisible(true);


        //Example Output idea

//        AnchorPane cartPane = new AnchorPane();
//        cartPane.setStyle("-fx-background-color: white; -fx-padding: 20px;");
//        cartPane.setPrefSize(400, 600);
//
//        VBox cartItemsBox = new VBox(10);
//        cartItemsBox.setPadding(new Insets(10));
//        cartItemsBox.setAlignment(Pos.TOP_CENTER);
//
//        for (Food food : cart) {
//            HBox cartItem = new HBox(10);
//            cartItem.setAlignment(Pos.CENTER_LEFT);
//
//            // Item image
//            ImageView foodImage = new ImageView(new Image(getClass().getResourceAsStream(food.getImgSrc())));
//            foodImage.setFitWidth(50);
//            foodImage.setFitHeight(50);
//
//            // Item name
//            Label nameLabel = new Label(food.getName());
//            nameLabel.setPrefWidth(150);
//
//            // Item quantity
//            Label quantityLabel = new Label("Qty: " + food.getQuantity());
//            quantityLabel.setPrefWidth(50);
//
//            // Item price
//            Label priceLabel = new Label("₱" + food.getPrice() * food.getQuantity());
//            priceLabel.setPrefWidth(100);
//
//            // Remove button
//            Button removeButton = new Button("Remove");
//            removeButton.setOnAction(event -> {
//                cart.remove(food);
//                showCart(); // Refresh the cart pane
//            });
//
//            cartItem.getChildren().addAll(foodImage, nameLabel, quantityLabel, priceLabel, removeButton);
//            cartItemsBox.getChildren().add(cartItem);
//        }
//
//        // Add the VBox to the cart pane
//        cartPane.getChildren().add(cartItemsBox);
//
//        // Add the cart pane to the mainAnchorpane
//        mainAnchorpane.getChildren().add(cartPane);
//
//        // Position the cart pane in the center of the mainAnchorpane
//        AnchorPane.setTopAnchor(cartPane, 50.0);
//        AnchorPane.setBottomAnchor(cartPane, 50.0);
//        AnchorPane.setLeftAnchor(cartPane, 50.0);
//        AnchorPane.setRightAnchor(cartPane, 50.0);
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
        food.setName("1pc Chicken");
        food.setCategory("Chicken");
        food.setPrice(82.0);
        food.setImgSrc("/pic_resources/Chicken/1pcchicken.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("1pc Spicy Chicken");
        food.setCategory("Chicken");
        food.setPrice(99.0);
        food.setImgSrc("/pic_resources/Chicken/1pcspicychicken.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("2pc Chicken");
        food.setCategory("Chicken");
        food.setPrice(163.0);
        food.setImgSrc("/pic_resources/Chicken/2pcchicken.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Chicken Fillet");
        food.setCategory("Chicken");
        food.setPrice(148.0);
        food.setImgSrc("/pic_resources/Chicken/ChickenFilet.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Chicken with Spaghetti");
        food.setCategory("Chicken");
        food.setPrice(139.0);
        food.setImgSrc("/pic_resources/Chicken/ChickenSpagh.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Bacon Chicken Mayo Burger");
        food.setCategory("Burger");
        food.setPrice(149.0);
        food.setImgSrc("/pic_resources/Burgers/baconchickenmayo.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Big Burger");
        food.setCategory("Burger");
        food.setPrice(150.0);
        food.setImgSrc("/pic_resources/Burgers/Bigburger.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Big Mc Burger");
        food.setCategory("Burger");
        food.setPrice(179.0);
        food.setImgSrc("/pic_resources/Burgers/BigMc.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Big Tasty w/ Bacon Burger");
        food.setCategory("Burger");
        food.setPrice(179.0);
        food.setImgSrc("/pic_resources/Burgers/Bigtastywithbacon.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Chicken Sandwich Burger");
        food.setCategory("Burger");
        food.setPrice(220.0);
        food.setImgSrc("/pic_resources/Burgers/chickensandwich.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Crispy Chicken Sandwich Burger");
        food.setCategory("Burger");
        food.setPrice(171.0);
        food.setImgSrc("/pic_resources/Burgers/crispychickensandwich.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Deluxe Crispy Chicken Burger");
        food.setCategory("Burger");
        food.setPrice(229.0);
        food.setImgSrc("/pic_resources/Burgers/deluxecrispychicken.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Double Cheese Burger");
        food.setCategory("Burger");
        food.setPrice(160.0);
        food.setImgSrc("/pic_resources/Burgers/doublecheeseburger.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Filet-o-Fish Burger");
        food.setCategory("Burger");
        food.setPrice(99.0);
        food.setImgSrc("/pic_resources/Burgers/filet-o-fish.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Quarter Pounder w/ Cheese Burger");
        food.setCategory("Burger");
        food.setPrice(271.0);
        food.setImgSrc("/pic_resources/Burgers/quarterpounderwithcheese.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Regular Burger");
        food.setCategory("Burger");
        food.setPrice(79.0);
        food.setImgSrc("/pic_resources/Burgers/regburger.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Triple Cheese Burger");
        food.setCategory("Burger");
        food.setPrice(169.0);
        food.setImgSrc("/pic_resources/Burgers/triplecheeseburger.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Vegetable Deluxe Burger");
        food.setCategory("Burger");
        food.setPrice(145.0);
        food.setImgSrc("/pic_resources/Burgers/vegetabledeluxe.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Banana Strawberry Smoothie");
        food.setCategory("Beverages");
        food.setPrice(89.0);
        food.setImgSrc("/pic_resources/beverages/bananastrawberrysmoothie.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Coke");
        food.setCategory("Beverages");
        food.setPrice(22.0);
        food.setImgSrc("/pic_resources/beverages/coke1.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Coke Diet");
        food.setCategory("Beverages");
        food.setPrice(32.0);
        food.setImgSrc("/pic_resources/beverages/Cokediet.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Coke Zero");
        food.setCategory("Beverages");
        food.setPrice(38.0);
        food.setImgSrc("/pic_resources/beverages/cokezero.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Dasani");
        food.setCategory("Beverages");
        food.setPrice(18.0);
        food.setImgSrc("/pic_resources/beverages/dasani.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Dr.Pepper");
        food.setCategory("Beverages");
        food.setPrice(38.0);
        food.setImgSrc("/pic_resources/beverages/DrPepper.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Fanta");
        food.setCategory("Beverages");
        food.setPrice(22.0);
        food.setImgSrc("/pic_resources/beverages/fanta.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Frozen Coke");
        food.setCategory("Beverages");
        food.setPrice(40.0);
        food.setImgSrc("/pic_resources/beverages/frozencoke.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Frozen Fanta");
        food.setCategory("Beverages");
        food.setPrice(40.0);
        food.setImgSrc("/pic_resources/beverages/frozenfanta.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Lemonade");
        food.setCategory("Beverages");
        food.setPrice(39.0);
        food.setImgSrc("/pic_resources/beverages/lemonade.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Mango Pineapple Smoothie");
        food.setCategory("Beverages");
        food.setPrice(49.0);
        food.setImgSrc("/pic_resources/beverages/mangopineapplesmoothie.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("McFloat");
        food.setCategory("Beverages");
        food.setPrice(49.0);
        food.setImgSrc("/pic_resources/beverages/mcfloat.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Sprite");
        food.setCategory("Beverages");
        food.setPrice(32.0);
        food.setImgSrc("/pic_resources/beverages/sprite.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Sweet Tea");
        food.setCategory("Beverages");
        food.setPrice(37.0);
        food.setImgSrc("/pic_resources/beverages/sweettea.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Fries");
        food.setCategory("Sides");
        food.setPrice(79.0);
        food.setImgSrc("/pic_resources/Sides/Fries.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Fries and Nuggets");
        food.setCategory("Sides");
        food.setPrice(120.0);
        food.setImgSrc("/pic_resources/Sides/Friesandnuggets.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Nuggets");
        food.setCategory("Sides");
        food.setPrice(59.0);
        food.setImgSrc("/pic_resources/Sides/Nuggets.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Bacon Egg Muffin");
        food.setCategory("Breakfast Menu");
        food.setPrice(120.0);
        food.setImgSrc("/pic_resources/breakfast menu/baconeggmuffin .jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Baconroll with Brown Sauce");
        food.setCategory("Breakfast Menu");
        food.setPrice(99.0);
        food.setImgSrc("/pic_resources/breakfast menu/baconrollwithbrownsauce.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Baconroll with Tomato Sauce");
        food.setCategory("Breakfast Menu");
        food.setPrice(99.0);
        food.setImgSrc("/pic_resources/breakfast menu/baconrollwithtomatosauce.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Double Bacon Egg");
        food.setCategory("Breakfast Menu");
        food.setPrice(130.0);
        food.setImgSrc("/pic_resources/breakfast menu/doublebaconegg.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Double Sausage Muffin");
        food.setCategory("Breakfast Menu");
        food.setPrice(130.0);
        food.setImgSrc("/pic_resources/breakfast menu/doublesausagemuffin.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Egg Cheese Muffin");
        food.setCategory("Breakfast Menu");
        food.setPrice(89.0);
        food.setImgSrc("/pic_resources/breakfast menu/eggcheesemuffin.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Muffin with Brown Sauce");
        food.setCategory("Breakfast Menu");
        food.setPrice(49.0);
        food.setImgSrc("/pic_resources/breakfast menu/muffinbrownsauce.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Muffin with Tomato Sauce");
        food.setCategory("Breakfast Menu");
        food.setPrice(49.0);
        food.setImgSrc("/pic_resources/breakfast menu/muffintomatosauce.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Pancake with Sausage with Syrup");
        food.setCategory("Breakfast Menu");
        food.setPrice(59.0);
        food.setImgSrc("/pic_resources/breakfast menu/pancakeandsausagewithsyrup.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Roll with Ketchup");
        food.setCategory("Breakfast Menu");
        food.setPrice(49.0);
        food.setImgSrc("/pic_resources/breakfast menu/roll with ketchup.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Roll with Brown Sauce");
        food.setCategory("Breakfast Menu");
        food.setPrice(49.0);
        food.setImgSrc("/pic_resources/breakfast menu/rollwithbrownsauce.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Sausage and Egg Muffin");
        food.setCategory("Breakfast Menu");
        food.setPrice(79.0);
        food.setImgSrc("/pic_resources/breakfast menu/sausageandeggmuffin.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Wrap with Brown Sauce");
        food.setCategory("Breakfast Menu");
        food.setPrice(100.0);
        food.setImgSrc("/pic_resources/breakfast menu/wrapwithbrownsauce.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Wrap with Ketchup");
        food.setCategory("Breakfast Menu");
        food.setPrice(100.0);
        food.setImgSrc("/pic_resources/breakfast menu/wrapwithketchup.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Apple Pie");
        food.setCategory("Dessert");
        food.setPrice(60.0);
        food.setImgSrc("/pic_resources/desserts/applepie.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Chocolate Brownie");
        food.setCategory("Dessert");
        food.setPrice(30.0);
        food.setImgSrc("/pic_resources/desserts/Chocbrownie.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Mixed Berry Muffin");
        food.setCategory("Dessert");
        food.setPrice(60.0);
        food.setImgSrc("/pic_resources/desserts/mixedberrymuffin.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Sugary Donut");
        food.setCategory("Dessert");
        food.setPrice(40.0);
        food.setImgSrc("/pic_resources/desserts/sugardonut.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        food = new Food();
        food.setName("Triple Chocolate Cookie");
        food.setCategory("Dessert");
        food.setPrice(60.0);
        food.setImgSrc("/pic_resources/desserts/Triplechoccookie.jpg");
        food.setColor("#f2f2f2");
        foods.add(food);

        return foods;



    }

    private List<FoodCategory> getCategories(){
        List<FoodCategory> categories = new ArrayList<>();
        FoodCategory category;


        category = new FoodCategory();
        category.setName("All Time Favourites");
        category.setImgSrc("/pic_resources/final menu/alltimefave.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);

        category = new FoodCategory();
        category.setName("Chicken");
        category.setImgSrc("/pic_resources/Chicken/2pcchicken.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);

        category = new FoodCategory();
        category.setName("Burger");
        category.setImgSrc("/pic_resources/Burgers/quarterpounderwithcheese.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);

        category = new FoodCategory();
        category.setName("Beverages");
        category.setImgSrc("/pic_resources/beverages/coke1.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);

        category = new FoodCategory();
        category.setName("Breakfast Menu");
        category.setImgSrc("/pic_resources/breakfast menu/eggcheesemuffin.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);

        category = new FoodCategory();
        category.setName("Dessert");
        category.setImgSrc("/pic_resources/desserts/applepie.jpg");
        category.setColor("#f2f2f2");
        categories.add(category);

        category = new FoodCategory();
        category.setName("Sides");
        category.setImgSrc("/pic_resources/Sides/Fries.jpg");
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

                GridPane.setMargin(anchorPane, new Insets(25));
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
        cartPane.setVisible(false);
        proceedToCheckoutPanel.setVisible(false);
        addAnchorPane.setVisible(false);
        orderPanel.setVisible(false);
    }

    private void setChosenFood(Food food){
        chosenFood = food;
        confirmPanelItemname.setText(chosenFood.getName());
        orderPanelItemPrice.setText("PHP " + chosenFood.getPrice());
        confirmPanelImg.setImage(new Image(getClass().getResourceAsStream(chosenFood.getImgSrc())));
    }


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

                GridPane.setMargin(anchorPane, new Insets(25));
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
                currentQuantity = 1;
                quantity.setText(String.valueOf(currentQuantity));
                setChosenFood(food);
                proceedToCheckoutPanel.setVisible(false);
                orderPanel.setVisible(true);
                addAnchorPane.setVisible(true);
            }
        };

    myCategoryListener = new MyCategoryListener() {
        @Override
        public void onclickListener(FoodCategory foodCategory) {
            mainHeader.setText(foodCategory.getName());
            grid.getChildren().clear();
            itemByCategory.clear();
            itemByCategory.addAll(getItemsByCategory(foodCategory.getName()));
            embedCategoricalItems();
        }
    };

    myCartItemListener = new MyCartItemListener() {
        @Override
        public void onRemoveItem(Food food) {
            cart.remove(food);
            itemsLabel.setText(String.valueOf(cart.size()) + " item/s in the cart");
            showCart();

        }
    };
        embedItems();
        embedCategories();
    }
}
