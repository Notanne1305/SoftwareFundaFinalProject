package org.example.softfun_funsoft.singleton;

import org.example.softfun_funsoft.model.Food;

import java.util.ArrayList;
import java.util.List;

public class Order {
    //TODO: Implement Singleton Pattern
    private static Order instance;
    private boolean isDineIn;
    private String paymentType;
    private final List<Food> orderItems;

    private String orderID;

    private Order(){
        orderItems = new ArrayList<>();
    }

    public static synchronized Order getInstance(){
        if(instance == null){
            instance = new Order();
        }
        return instance;
    }

    public List<Food> getOrderItems(){
        return orderItems;
    }

    public boolean isDineIn() {
        return isDineIn;
    }

    public void setDineIn(boolean isDineIn) {
        this.isDineIn = isDineIn;
    }

    public void addItems(List<Food> food){
        orderItems.addAll(food);
    }

    public void clearOrder(){
        orderItems.clear();
    }

    public void setPaymentType(String paymentType){
        this.paymentType = paymentType;
    }

    public String getPaymentType(){
        return paymentType;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        double total = 0;

        receipt.append("Receipt:\n");
        receipt.append("----------------------------\n");
        receipt.append("Order Type: ").append(isDineIn ? "Dine In" : "Take Out").append("\n");
        receipt.append("----------------------------\n");

        for (Food food : orderItems) {
            double itemTotal = food.getPrice() * food.getQuantity();
            receipt.append(String.format("%s x%d - PHP %.2f\n", food.getName(), food.getQuantity(), itemTotal));
            total += itemTotal;
        }

        receipt.append("----------------------------\n");
        receipt.append(String.format("Total: PHP %.2f\n", total));

        return receipt.toString();
    }

    public double getGrandTotal(){
        return orderItems.stream().mapToDouble(food -> food.getPrice() * food.getQuantity()).sum();
    }




}
