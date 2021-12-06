package com.prydev.ev2app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Purchase implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int carId;
    private int clientID;
    private int neto;
    private int iva;
    private int total;

    public Purchase() {
    }

    public Purchase(int id, int carId, int clientID, int neto, int iva, int total) {
        this.id = id;
        this.carId = carId;
        this.clientID = clientID;
        this.neto = neto;
        this.iva = iva;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getNeto() {
        return neto;
    }

    public void setNeto(int neto) {
        this.neto = neto;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
