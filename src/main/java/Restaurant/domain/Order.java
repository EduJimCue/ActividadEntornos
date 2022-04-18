package Restaurant.domain;

import Restaurant.util.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private  LocalDate currentDate;
    private boolean paid;
    private String dishName;
    private Date date;
    private float dishPrice;
    private String orderUser;

    private User user;
    private List<Dish> dishes;

    public Order() {
        dishes = new ArrayList<>();
    }

    public Order(boolean paid, LocalDate date, User user) {
        this.paid = paid;
        this.currentDate = date;
        this.user = user;
        dishes = new ArrayList<>();
    }
    public Order(boolean paid,Date date, User user, String dishName,float dishPrice) {
        this.paid = paid;
        this.date = date;
        this.user = user;
        this.dishName=dishName;
        this.dishPrice=dishPrice;
        dishes = new ArrayList<>();
    }
    public Order(boolean paid,Date date, User user, String dishName,float dishPrice,String orderUser) {
        this.paid = paid;
        this.date = date;
        this.user = user;
        this.dishName=dishName;
        this.dishPrice=dishPrice;
        this.orderUser=orderUser;
        dishes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }



    @Override
    public String toString() {
        return "Pagado: " + paid + "\n" +
                "Fecha: " + DateUtils.formatLocalDate(currentDate);
    }

    public String getDishName() {return dishName;}

    public void setDishName(String dishName) {this.dishName = dishName;}

    public LocalDate getCurrentDate() {return currentDate;}

    public void setCurrentDate(LocalDate currentDate) {this.currentDate = currentDate;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public float getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(float dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }
}
