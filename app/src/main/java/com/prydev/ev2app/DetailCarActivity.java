package com.prydev.ev2app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.prydev.ev2app.models.Car;
import com.prydev.ev2app.models.Purchase;
import com.prydev.ev2app.models.User;
import com.prydev.ev2app.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class DetailCarActivity extends AppCompatActivity {

    TextView txtBrandName;
    TextView txtPrice;
    TextView txtHp;
    TextView txtYear;
    TextView txtModel;
    RadioGroup rGroup;
    SwitchMaterial dSwitch;
    ImageView imgBack;
    CardView cardBtnsBuy;
    LinearLayout radioLayout;
    TextView txtColorChoosed;

    ImageView carImage;
    Button btnBuy;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_car);
        User user = (User) getIntent().getSerializableExtra("user");
        String className = (String) getIntent().getSerializableExtra("className");

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Eva2DB").allowMainThreadQueries().build();
        finds();

        Car car = (Car) getIntent().getSerializableExtra("car");
        txtBrandName.setText(car.getBrand());
        txtPrice.setText(String.valueOf(car.getPrice()));
        txtHp.setText(String.valueOf(car.getHp()));
        txtYear.setText(car.getYear());
        txtModel.setText(car.getModel());




        Glide.with(this).load(car.getImageUrl()).into(carImage);

        imgBack.setOnClickListener(view -> {
            callBack(user, className);
        });

        //Switch
        isBalanceEnough(user, car);

        //Car Bought
        if(car.getState()==0){
            btnBuy.setActivated(false);
            btnBuy.setVisibility(View.INVISIBLE);
            btnBuy.setClickable(false);

            cardBtnsBuy.setVisibility(View.INVISIBLE);
            cardBtnsBuy.setEnabled(false);

            radioLayout.setEnabled(false);
            radioLayout.setVisibility(View.GONE);

            txtColorChoosed.setVisibility(View.VISIBLE);
            txtColorChoosed.setText(car.getColorString());

            dSwitch.setClickable(false);
            dSwitch.setActivated(false);
            dSwitch.setEnabled(false);
            dSwitch.setChecked(car.isDeluxe());
        }
        // Buy a car
        btnBuy.setOnClickListener(view -> {
            Purchase purchase = new Purchase();

            if(dSwitch.isChecked()){
                purchase.setNeto((int) (car.getPrice()+car.getPrice()*0.2));
                car.setIsDeluxe(true);
            }else{
                car.setIsDeluxe(false);
                purchase.setNeto(car.getPrice());
            }


            // Set Ids

                //Purchase
                purchase.setIva((int) (purchase.getNeto()*0.19));
                purchase.setTotal(purchase.getNeto()+purchase.getIva());
                purchase.setCarId(car.getId());
                purchase.setClientID(user.getId());

                //Car
                RadioButton rad = findViewById(rGroup.getCheckedRadioButtonId());
                car.setColor(Integer.parseInt(rad.getText().toString()));
                car.setState(1);
                car.setIdUser(user.getId());

                //User
                user.setBalance(user.getBalance()-purchase.getTotal());
                if(user.getListCarsBought() != null){
                    user.setCar(car);
                }else{
                    List<Car> list = new ArrayList<>();
                    list.add(car);
                    user.setListCarsBought(list);
                }



            //Update & Inserts
            db.userdao().updateByObject(user);
            db.carDao().updateByObject(car);
            db.purchaseDao().insert(purchase);

            //Toast & Redirects
            Toast.makeText(this, "Car purchased"+ purchase.getTotal() + " Discounted from your account", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AccountDetailActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();

        });

    }

    private void finds(){
        txtBrandName = findViewById(R.id.txtBrandName);
        txtPrice = findViewById(R.id.txtPrice);
        txtHp = findViewById(R.id.txtHp);
        txtYear = findViewById(R.id.txtYear);
        txtModel = findViewById(R.id.txtModel);
        rGroup = findViewById(R.id.radioGroup);
        dSwitch = findViewById(R.id.versionSwitch);
        imgBack = findViewById(R.id.imgBack);
        cardBtnsBuy = findViewById(R.id.cardBtnsBuy);
        radioLayout = findViewById(R.id.radioLayout);
        txtColorChoosed = findViewById(R.id.txtColorChoosed);

        carImage = findViewById(R.id.carImage);
        btnBuy = findViewById(R.id.btnBuy);
    }
    private void callBack(User user, String activity){
        Intent intent;
        switch (activity){
            case "Main":
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
                break;
            case "User":
                intent = new Intent(this, AccountDetailActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void isBalanceEnough(User user , Car car){
      if(user.getBalance()>car.getPrice()){
          if (!(user.getBalance() > car.getPrice() * 1.19)) {
              btnBuy.setEnabled(false);
          }
      }else {
          btnBuy.setEnabled(false);
      }
    }

    void setfalse(){
        dSwitch.setClickable(false);
        dSwitch.setEnabled(false);
        dSwitch.setChecked(false);
        dSwitch.setActivated(false);
        btnBuy.setEnabled(false);
    }
    void setTrue(){
        dSwitch.setClickable(true);
        dSwitch.setEnabled(true);
        dSwitch.setChecked(true);
        dSwitch.setActivated(true);
        btnBuy.setEnabled(true);
    }


}