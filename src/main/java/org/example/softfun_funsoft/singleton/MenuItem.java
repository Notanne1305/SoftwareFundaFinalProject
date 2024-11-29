package org.example.softfun_funsoft.singleton;

import org.example.softfun_funsoft.model.Food;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
    private static MenuItem instance;
    private List<Food> foods;

    private MenuItem(){
        foods = new ArrayList<>();
        foods.addAll(getData());
    }

    public static synchronized MenuItem getInstance(){
        if(instance == null){
            instance = new MenuItem();
        }
        return instance;
    }

    public List<Food> getFoods(){
        return foods;
    }

    public void addFood(Food food){
        foods.add(food);
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

}
