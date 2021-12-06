package com.prydev.ev2app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.prydev.ev2app.models.Purchase;

@Dao
public interface PurchaseDao {

    @Query("SELECT * FROM Purchase WHERE ID = :id")
    Purchase getByID(int id);

    @Insert
    void insert(Purchase... purchases);

    @Update
    void updateByObject(Purchase... purchases);
}
