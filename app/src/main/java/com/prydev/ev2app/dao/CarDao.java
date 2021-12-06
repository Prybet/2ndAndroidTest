package com.prydev.ev2app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.prydev.ev2app.models.Car;

import java.util.List;

@Dao
public interface CarDao {
    @Query("SELECT * FROM car WHERE state = 1")
    List<Car> getAll();

    @Query("SELECT * FROM car WHERE idUser = :idUser AND state = 0")
    List<Car> getByUserid(int idUser);

    @Query("SELECT * FROM car WHERE ID = :id")
    Car getByID(int id);

    @Delete
    void deleteByObject(Car... car);

    @Insert
    void insert(Car... car);

    @Update
    void updateByObject(Car... car);
}
