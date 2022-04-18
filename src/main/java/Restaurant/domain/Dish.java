package Restaurant.domain;

import java.util.ArrayList;
import java.util.List;

public class Dish {

    private int id;
    private float price;
    private String name;
    private String restaurantName;

    private Restaurant restaurant;

    private List<Order> orders;

    public Dish() {
        orders = new ArrayList<>();
    }

    public Dish(float price, String name, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
        orders = new ArrayList<>();
    }
    public Dish(float price, String name) {
        this.name = name;
        this.price = price;
        orders = new ArrayList<>();
    }
    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public float getPrice() {return price;}

    public void setPrice(float price) {this.price = price;}

    public void setRestaurant (Restaurant restaurant){this.restaurant = restaurant;}

    public Restaurant getRestaurant() {return restaurant;}

    public String getRestaurantName() {return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {this.restaurantName = restaurantName;}
}
