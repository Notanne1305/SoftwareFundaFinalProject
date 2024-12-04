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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.softfun_funsoft.lang.LangCheck;
import org.example.softfun_funsoft.listener.MyCartItemListener;
import org.example.softfun_funsoft.listener.MyCategoryListener;
import org.example.softfun_funsoft.listener.MyItemListener;
import org.example.softfun_funsoft.model.Food;
import org.example.softfun_funsoft.model.FoodCategory;
import org.example.softfun_funsoft.singleton.Cart;
import org.example.softfun_funsoft.singleton.Categories;
import org.example.softfun_funsoft.singleton.MenuItem;
import org.example.softfun_funsoft.singleton.Order;
import org.example.softfun_funsoft.utils.SoundManager;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainMenuController implements Initializable {

    @FXML
    private StackPane rootStackPane;

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

    @FXML
    private Label grandTotal;


    private MyItemListener myItemListener;
    private MyCategoryListener myCategoryListener;
    private MyCartItemListener myCartItemListener;
    private List<Food> foods = new ArrayList<>();
    private List<FoodCategory> categories = new ArrayList<>();
    private List<Food> itemByCategory = new ArrayList<>();

    private Cart cart;

    private Food chosenFood;

    private int currentQuantity = 1;
    Timer timer = new Timer();
    Runnable embedFoodTask = () -> {
        String searchName = searchBar.getText().toLowerCase();
        embedMatchingFood(searchName);
    };


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

// Add listener to the AnchorPane size to update the position dynamically
        mainAnchorpane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double notificationWidth = notification.prefWidth(-1);  // Get the preferred width of the notification
            double xPosition = newValue.doubleValue() - 250 - notificationWidth;  // 250px from the right

            notification.setLayoutX(xPosition);
        });

// Add listener to the AnchorPane height to update the Y position dynamically
        mainAnchorpane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double yPosition = newValue.doubleValue() - 190;  // Adjust as needed for vertical placement
            notification.setLayoutY(yPosition);
        });

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
        SoundManager.playAddandSubt();
    }

    public void setSubtractQuantity(){
        if(currentQuantity > 1){
            currentQuantity--;
            quantity.setText(String.valueOf(currentQuantity));
            SoundManager.playAddandSubt();;
        }
    }

    public void setAddToCart(){
        orderPanel.setVisible(false);
        addAnchorPane.setVisible(false);
        chosenFood.setQuantity(Integer.parseInt(quantity.getText()));
        showNotification(chosenFood);
        cart.addItem(chosenFood);
        itemsLabel.setText(String.valueOf(cart.getCartItems().size()) + " item/s in the cart");
        SoundManager.playClick();
        //TODO: There's a bug when adding two of the same item more than twice. The quantity is not updating

    }

    public void proceedToCheckoutAction(){
        Order order = Order.getInstance();
        order.clearOrder();
        order.addItems(cart.getCartItems());

        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("PaymentTypes.fxml"));

            Node currentRoot = rootStackPane.getChildren().get(rootStackPane.getChildren().size() - 1);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {

                rootStackPane.getChildren().remove(currentRoot);
                rootStackPane.getChildren().add(newRoot);

            });
            fadeOut.play();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    public void showCart(){
        double totalPrice = 0;
        cartGrid.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            for (Food food : cart.getCartItems()) {
                totalPrice += (food.getPrice() * food.getQuantity());

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CartItem.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                CartItemController itemController = fxmlLoader.getController();
                itemController.setData(food, myCartItemListener);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                cartGrid.add(anchorPane, column++, row);
                cartGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                cartGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            }

            cartScrollPane.setFitToWidth(true);
            cartScrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                cartGrid.setPrefWidth(newVal.doubleValue());
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        grandTotal.setStyle("-fx-text-fill: green;");
        grandTotal.setText("PHP " + totalPrice );

        addAnchorPane.setVisible(false);
        cartPane.setVisible(true);

        if(cart.getCartItems().isEmpty()) {
            proceedToCheckoutPanel.setVisible(false);
        }else{
            proceedToCheckoutPanel.setVisible(true);

        }


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

    public void cancelButton() {
        if (!orderPanel.isVisible() && !cartPane.isVisible()) {
            System.out.println("True, they are both hidden");
            cart.removeAll();

            try {
                Parent newRoot = FXMLLoader.load(getClass().getResource("StartUp.fxml"));

                Node currentRoot = rootStackPane.getChildren().get(rootStackPane.getChildren().size() - 1);

                FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentRoot);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(e -> {

                    rootStackPane.getChildren().remove(currentRoot);
                    rootStackPane.getChildren().add(newRoot);

                });

                SoundManager.playRemove();
                fadeOut.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cartPane.setVisible(false);
            proceedToCheckoutPanel.setVisible(false);
            addAnchorPane.setVisible(false);
            orderPanel.setVisible(false);
        }
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
        foods = MenuItem.getInstance().getFoods();
        categories = Categories.getInstance().getCategories();
        cart = Cart.getInstance();

        itemsLabel.setText(String.valueOf(cart.getCartItems().size()) + " item/s in the cart");


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
            cart.removeItem(food);
            itemsLabel.setText(String.valueOf(cart.getCartItems().size()) + " item/s in the cart");
            SoundManager.playRemove();
            showCart();

        }
    };
        embedItems();
        embedCategories();
    }

}
