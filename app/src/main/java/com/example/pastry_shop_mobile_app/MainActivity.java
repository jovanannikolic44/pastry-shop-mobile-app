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
import com.google.gson.reflect.TypeToken;

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final Type userListType = new TypeToken<List<User>>(){}.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ModelPreferencesManager.initialize(this);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        List<User> predefinedUsers = ModelPreferencesManager.get("users", MainActivity.userListType);

        if(predefinedUsers == null) {
            predefinedUsers = new ArrayList<>();
            predefinedUsers.add(new User("Ana", "Marinkovic", "0678899334", "Adresa 1", "ana", "ana123", "kupac"));
            predefinedUsers.add(new User("Milos", "Markovic", "0678899339", "Adresa 2", "milos", "milos123", "kupac"));
            predefinedUsers.add(new User("Nenad", "Novakovic", "0678899332", "Adresa 3", "nenad", "nenad123", "zaposleni"));
            ModelPreferencesManager.put(predefinedUsers, "users");
        }
    }

    public void logIn(View view) {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        if("".equals(username) || "".equals(password)) {
            Toast.makeText(this, "Popute sva prazna polja.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<User> allUsers = ModelPreferencesManager.get("users", MainActivity.userListType);

        User loggedUser = null;
        for(int i = 0; i < allUsers.size(); i++) {
            if(username.equals(allUsers.get(i).getUsername()) && password.equals(allUsers.get(i).getPassword())) {
                loggedUser = allUsers.get(i);
                break;
            }
        }

        if(loggedUser == null) {
            Toast.makeText(this, "Pogresni kredencijali.", Toast.LENGTH_SHORT).show();
            return;
        }
        ModelPreferencesManager.put(loggedUser, "loggedInUser");
        Intent intent = new Intent(this, ShowItems.class);
        startActivity(intent);
    }

    public void registerLink(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}