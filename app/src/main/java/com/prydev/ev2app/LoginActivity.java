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

public class LoginActivity extends AppCompatActivity {

    EditText userName;
    EditText pass;

    Button btnLogIn;
    Button btnSingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        finds();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Eva2DB").allowMainThreadQueries().build();
        Car car = (Car) getIntent().getSerializableExtra("car");



        btnLogIn.setOnClickListener(view -> {
            User user = db.userdao().testLogin(userName.getText().toString(), pass.getText().toString());
            if(user!=null){
                loginSend(user);
            }else{
                Toast.makeText(this, "Inicio de Sesion Incorrecto", Toast.LENGTH_SHORT).show();
            }
        });

        btnSingIn.setOnClickListener(view -> {
            Intent intent = new Intent(this, SingInActivity.class);
            intent.putExtra("car", getIntent().getSerializableExtra("car"));
            startActivity(intent);
        });

    }

    private void finds(){
        userName = findViewById(R.id.txtUsername);
        pass = findViewById(R.id.txtPass);
        btnLogIn = findViewById(R.id.btnLogin);
        btnSingIn = findViewById(R.id.btnSingIn);
    }

    public void loginSend(User user){
        Toast.makeText(this, "Inicio de Sesion Correcto", Toast.LENGTH_SHORT).show();
        if( getIntent().getSerializableExtra("car")!=null){
            Intent intent = new Intent(this, DetailCarActivity.class);
            intent.putExtra("car", getIntent().getSerializableExtra("car"));
            intent.putExtra("user", user);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}