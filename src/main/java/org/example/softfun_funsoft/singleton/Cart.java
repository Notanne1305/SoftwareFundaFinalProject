package org.example.softfun_funsoft.singleton;

import org.example.softfun_funsoft.model.Food;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private final List<Food> cartItems;

    private Cart(){
        cartItems = new ArrayList<>();

    }

    public static synchronized Cart getInstance(){
        if(instance == null){
            instance = new Cart();
        }
        return instance;
    }

    public List<Food> getCartItems(){
        return cartItems;
    }
    public void addItem(Food food) {
        for (Food item : cartItems) {
            if (item.getName().equals(food.getName())) {
                System.out.println("this is item quantity " + item.getQuantity());
                System.out.println("this is currentFood quantity " + food.getQuantity());
                item.setQuantity(item.getQuantity() + food.getQuantity());
                return;
            }
            System.out.println(item.getQuantity());
        }
        cartItems.add(food);
    }
        public void removeItem(Food food){
        cartItems.remove(food);
    }
}
