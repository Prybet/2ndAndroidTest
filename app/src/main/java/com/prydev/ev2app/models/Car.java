package com.prydev.ev2app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;


@Entity
public class Car implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String model;
    private String brand;
    private String year;
    private int deluxe;
    private int price;
    private int hp;
    private int color;
    private int state;
    private int idUser;

    private String imageUrl;

    public Car() {
    }

    public Car(int id, String model, String brand, String year, int price, int hp,int state, String imageUrl) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.price = price;
        this.hp = hp;
        this.state = state;
        this.imageUrl = imageUrl;
        this.idUser = 0;
        this.deluxe = 0;
    }

    public static ArrayList<Car> createSomeCars(){
        ArrayList<Car> carList = new ArrayList<>();
        carList.add(new Car(0, "Mercedes-AMG ONE", "Mercedes-AMG", "2021",2700000, 877,1,"https://static.wikia.nocookie.net/forzamotorsport/images/6/60/FH5_Mercedes-AMG_One_Large.png/revision/latest?cb=20211107040029"));
        carList.add(new Car( 0,"GR Supra", "Toyota", "2020", 55000, 433,1, "https://static.wikia.nocookie.net/forzamotorsport/images/4/43/FH5_Toyota_GR_Supra_Large.png/revision/latest?cb=20211109145016"));
        carList.add(new Car( 0,"Vulcan AMR Pro", "Aston Martin", "2017", 2000000, 820,1, "https://static.wikia.nocookie.net/forzamotorsport/images/b/b8/HOR_XB1_AM_Vulcan_17.png/revision/latest?cb=20200213110307"));
        carList.add(new Car( 0,"2002 Turbo", "BMW", "1973", 26000, 168,1, "https://static.wikia.nocookie.net/forzamotorsport/images/1/1e/HOR_XB1_BMW_2002.png/revision/latest/scale-to-width-down/350?cb=20191111201606"));
        carList.add(new Car( 0,"SESTO ELEMENTO", "LAMBORGHINI", "2011", 2500000, 571,1, "https://static.wikia.nocookie.net/forzamotorsport/images/b/b0/HOR_XB1_Lambo_Sesto.png/revision/latest/scale-to-width-down/350?cb=20190601101803"));
        carList.add(new Car( 0,"Senna", "McLaren", "2018", 100000, 789,1, "https://static.wikia.nocookie.net/forzamotorsport/images/d/d4/HOR_XB1_McLaren_Senna.png/revision/latest?cb=20190916154008"));
        carList.add(new Car( 0,"Skyline GT-R V-Spec II", "Nissan", "2002", 63000, 327,1, "https://static.wikia.nocookie.net/forzamotorsport/images/7/71/HOR_XB1_Nissan_GT-R_02.png/revision/latest?cb=20190916182945"));
        return carList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getDeluxe() {
        return deluxe;
    }

    public boolean isDeluxe() {
        if(this.deluxe == 1){
            return true;
        }else{
            return true;
        }
    }

    public void setDeluxe(int deluxe) {
        this.deluxe = deluxe;
    }

    public void setIsDeluxe(boolean is) {
        if(is){
            this.deluxe = 1;
        }else{
            this.deluxe = 0;
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getColor() {
        return color;
    }

    public String getColorString() {
        switch (this.color){
            case 1:
                return "Rojo";
            case 2:
                return "Negro";
            case 3:
                return "Azul";
            case 4:
                return "Violeta";
            case 5:
                return "Blanco";
            default:
                return "";

        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
