package com.prydev.ev2app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.prydev.ev2app.adapters.CarAdapter;
import com.prydev.ev2app.models.Car;
import com.prydev.ev2app.models.User;
import com.prydev.ev2app.room.AppDatabase;

import java.util.List;

public class AccountDetailActivity extends AppCompatActivity {

    String[] depositsArray = {"500000", "1000000", "1500000", "2000000", "2500000","3000000"};

    Spinner balanceSpinner;
    EditText userName;
    EditText pass;
    ImageView imgBack;
    TextView txtBalance;

    RecyclerView rvCars;

    Button btnDeposit;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_account);
        finds();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Eva2DB").allowMainThreadQueries().build();
        Car car = (Car) getIntent().getSerializableExtra("car");

        User user = db.userdao().testLogin(((User) (getIntent().getSerializableExtra("user"))).getUsername(), ((User) (getIntent().getSerializableExtra("user"))).getPassword());

        userName.setText(user.getUsername());
        pass.setText(user.getPassword());

        //Back
        imgBack.setOnClickListener(view -> {
            callBack(user);
        });

        // RV
        List<Car> carList = db.carDao().getByUserid(user.getId());
        CarAdapter carAdapter = new CarAdapter(carList);
        rvCars.setAdapter(carAdapter);
        rvCars.setHasFixedSize(true);
        rvCars.setLayoutManager(new LinearLayoutManager(this));

        carAdapter.setMyItemClickListener(pos -> {
            findMyCar(carList.get(pos), user);
        });

        txtBalance.setText(String.valueOf(user.getBalance()));

        //Spinner
        balanceSpinner.setAdapter(new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, depositsArray));

        btnUpdate.setOnClickListener(view -> {
            user.setUsername(userName.getText().toString());
            user.setPassword(pass.getText().toString());
            db.userdao().updateByObject(user);
            Snackbar.make(view, "User Updated", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        btnDeposit.setOnClickListener(view -> {
            if(car != null){
                user.setBalance(user.getBalance() + Integer.parseInt(balanceSpinner.getSelectedItem().toString()));
                db.userdao().updateByObject(user);
                Toast.makeText(this, "Balance Charged to your Credit / Debit Card", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,DetailCarActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("car", car);
                intent.putExtra("className", "User");
                startActivity(intent);
            }else{
                user.setBalance(Integer.parseInt(balanceSpinner.getSelectedItem().toString()));
                db.userdao().updateByObject(user);
                Toast.makeText(this, "Balance Charged to your Credit / Debit Card", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("car", car);
                startActivity(intent);

            }

        });

        //Swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target){
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                Car car = carList.get(pos);
                user.setBalance(user.getBalance()+ car.getPrice());
                db.userdao().updateByObject(user);
                car.setState(0);
                car.setColor(0);
                car.setDeluxe(0);
                car.setIdUser(0);
                db.carDao().updateByObject(car);
                Snackbar.make(rvCars, "Vehicle sold, Balance deposited in your account", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                carAdapter.deleteByPosition(pos);
            }
        }).attachToRecyclerView(rvCars);


    }
    private void callBack(User user){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);

    }
    private void findMyCar(Car car, User user){
        Intent intent = new Intent(this, DetailCarActivity.class);
        intent.putExtra("car", car);
        intent.putExtra("user", user);
        intent.putExtra("isisBought", true);
        intent.putExtra("className", "User");
        startActivity(intent);

    }
    private void finds(){
        balanceSpinner = findViewById(R.id.spinnerBalance);
        userName = findViewById(R.id.txtUsername);
        pass = findViewById(R.id.txtPass);
        imgBack = findViewById(R.id.imgBack);
        txtBalance = findViewById(R.id.txtBalance);
        rvCars = findViewById(R.id.rvMyCars);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
}