package com.prydev.ev2app.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;
    private String password;
    private int state;
    private int balance;

    @Ignore
    private List<Car> ListCarsBought;

    public User() {
    }

    public User(int id, String username, String password, int state, int balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.state = state;
        this.balance = balance;
        this.setListCarsBought(new ArrayList<Car>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Car> getListCarsBought() {
        return ListCarsBought;
    }

    public void setListCarsBought(List<Car> listCarsBought) {
        ListCarsBought = listCarsBought;
    }

    public void setCar(Car carBought) {
        this.ListCarsBought.add(carBought);
    }
}
