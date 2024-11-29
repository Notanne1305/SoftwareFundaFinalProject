package org.example.softfun_funsoft.singleton;

import org.example.softfun_funsoft.model.FoodCategory;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    private static Categories instance;
    private List<FoodCategory> categories;

    private Categories() {
        categories = new ArrayList<>();
        categories.addAll(addCategories());
    }
    public static synchronized Categories getInstance() {
        if (instance == null) {
            instance = new Categories();
        }
        return instance;
    }

    public List<FoodCategory> getCategories() {
        return categories;
    }


    public List<FoodCategory> addCategories(){

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
}
