package com.prydev.ev2app.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.prydev.ev2app.dao.CarDao;
import com.prydev.ev2app.dao.PurchaseDao;
import com.prydev.ev2app.dao.UserDao;
import com.prydev.ev2app.models.Car;
import com.prydev.ev2app.models.Purchase;
import com.prydev.ev2app.models.User;

@Database(entities = {Car.class, User.class, Purchase.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CarDao carDao();

    public abstract UserDao userdao();

    public abstract PurchaseDao purchaseDao();
}
