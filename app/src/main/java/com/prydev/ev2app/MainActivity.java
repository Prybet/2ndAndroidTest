package com.prydev.ev2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.prydev.ev2app.adapters.CarAdapter;
import com.prydev.ev2app.models.Car;
import com.prydev.ev2app.models.User;
import com.prydev.ev2app.room.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvCars;

    FloatingActionButton flot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvCars = findViewById(R.id.rvCars);
        flot = findViewById(R.id.flotAccount);

        User user = (User) getIntent().getSerializableExtra("user");

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Eva2DB").allowMainThreadQueries().build();

        //Intento de crear objetos para llenar por primera vez el ReciclerView
        if(db.carDao().getAll().size() == 0){
            Snackbar.make(rvCars, "Ops apparently we are out of stock, if you want you can recharge", Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getResources().getColor(R.color.teal_700))
                    .setAction("ReLoad", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            createSomeCars(db);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();
        }

        List<Car> carList = db.carDao().getAll();

        CarAdapter carAdapter = new CarAdapter(carList);

        rvCars.setAdapter(carAdapter);
        rvCars.setHasFixedSize(true);
        rvCars.setLayoutManager(new LinearLayoutManager(this));

        carAdapter.setMyItemClickListener(pos -> {
            send(carList.get(pos), user);
        });


        flot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMyAccount(user);
            }
        });
    }
    public void send(Car car, User user){
        if(user!= null){
            Intent intent = new Intent(this, DetailCarActivity.class);
            intent.putExtra("car", car);
            intent.putExtra("user", user);
            intent.putExtra("className", "Main");
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("car", car);
            startActivity(intent);
            Toast.makeText(this, "Ops you haven't logged in, redirecting to the login", Toast.LENGTH_SHORT).show();
        }
    }

    public void goMyAccount(User user){
        if(user==null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, AccountDetailActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

    private void createSomeCars(AppDatabase db){
        for (int i = 0; i < Car.createSomeCars().size(); i++) {
         db.carDao().insert(Car.createSomeCars().get(i));
        }
    }




}