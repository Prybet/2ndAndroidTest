package com.prydev.ev2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prydev.ev2app.models.Car;
import com.prydev.ev2app.models.User;
import com.prydev.ev2app.room.AppDatabase;

public class SingInActivity extends AppCompatActivity {

    EditText userName;
    EditText pass;

    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        finds();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Eva2DB").allowMainThreadQueries().build();
        Car car = (Car) getIntent().getSerializableExtra("car");

        btnCreate.setOnClickListener(view -> {
            if(!db.userdao().testLoginUsername(userName.getText().toString())){
                User user = new User(0,userName.getText().toString(), pass.getText().toString(),0,0);
                db.userdao().insert(user);
                Intent intent = new Intent(this, AccountDetailActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("car", car);
                Toast.makeText(this, "Account Created successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }else{
                Toast.makeText(this, "Username already registered, try another", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void finds(){
        userName = findViewById(R.id.txtUsername);
        pass = findViewById(R.id.txtPass);
        btnCreate = findViewById(R.id.btnSingIn);
    }
}