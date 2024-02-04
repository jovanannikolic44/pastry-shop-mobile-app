package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.User;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
    }

    public void register(View view) {
        String regName = ((EditText) findViewById(R.id.regName)).getText().toString();
        String regLastName = ((EditText) findViewById(R.id.regLastName)).getText().toString();
        String regPhoneNumber = ((EditText) findViewById(R.id.regPhoneNumber)).getText().toString();
        String regAddress = ((EditText) findViewById(R.id.regAddress)).getText().toString();
        String regUsername = ((EditText) findViewById(R.id.regUsername)).getText().toString();
        String regPassword = ((EditText) findViewById(R.id.regPassword)).getText().toString();
        String regType = "kupac";

        if("".equals(regUsername) || "".equals(regPassword)) {
            Toast.makeText(this, "Korisnicko ime i lozinka su obavezna polja.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<User> allUsers = ModelPreferencesManager.get("users", MainActivity.userListType);
        for(int i = 0; i < allUsers.size(); i++) {
            if(regUsername.equals(allUsers.get(i).getUsername())) {
                Toast.makeText(this, "Korisnicko ime vec postoji.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        User saveUser = new User(regName, regLastName, regPhoneNumber, regAddress, regUsername, regPassword, regType);
        allUsers.add(saveUser);
        ModelPreferencesManager.put(allUsers, "users");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void backToLogIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}