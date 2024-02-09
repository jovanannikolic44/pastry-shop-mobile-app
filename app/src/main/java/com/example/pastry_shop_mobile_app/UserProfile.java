package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.User;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        User loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);
        if(loggedUser == null)
            return;

        EditText profileName = findViewById(R.id.profileName);
        profileName.setText(loggedUser.getName());
        EditText profileLastName = findViewById(R.id.profileLastName);
        profileLastName.setText(loggedUser.getLastName());
        EditText profileAddress = findViewById(R.id.profileAddress);
        profileAddress.setText(loggedUser.getAddress());
        EditText profilePhoneNumber = findViewById(R.id.profilePhoneNumber);
        profilePhoneNumber.setText(loggedUser.getPhoneNumber());
        EditText profileUsername = findViewById(R.id.profileUsername);
        profileUsername.setText(loggedUser.getUsername());
        EditText profilePassword = findViewById(R.id.profilePassword);
        profilePassword.setText(loggedUser.getPassword());
    }

    public void updateProfile(View view) {
        User loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);
        if(loggedUser == null)
            return;

        EditText profileName = findViewById(R.id.profileName);
        EditText profileLastName = findViewById(R.id.profileLastName);
        EditText profileAddress = findViewById(R.id.profileAddress);
        EditText profilePhoneNumber = findViewById(R.id.profilePhoneNumber);
        EditText profilePassword = findViewById(R.id.profilePassword);

        profileName.setEnabled(true);
        profileLastName.setEnabled(true);
        profileAddress.setEnabled(true);
        profilePhoneNumber.setEnabled(true);
        profilePassword.setEnabled(true);

        Button updateButton = findViewById(R.id.update);
        updateButton.setVisibility(View.GONE);

        Button cancelButton = findViewById(R.id.cancel);
        cancelButton.setVisibility(View.VISIBLE);

        Button saveButton = findViewById(R.id.save);
        saveButton.setVisibility(View.VISIBLE);
    }

    public void cancelUpdate(View view) {
        EditText profileName = findViewById(R.id.profileName);
        EditText profileLastName = findViewById(R.id.profileLastName);
        EditText profileAddress = findViewById(R.id.profileAddress);
        EditText profilePhoneNumber = findViewById(R.id.profilePhoneNumber);
        EditText profilePassword = findViewById(R.id.profilePassword);

        profileName.setEnabled(false);
        profileLastName.setEnabled(false);
        profileAddress.setEnabled(false);
        profilePhoneNumber.setEnabled(false);
        profilePassword.setEnabled(false);

        Button updateButton = findViewById(R.id.update);
        updateButton.setVisibility(View.VISIBLE);

        Button cancelButton = findViewById(R.id.cancel);
        cancelButton.setVisibility(View.GONE);

        Button saveButton = findViewById(R.id.save);
        saveButton.setVisibility(View.GONE);
    }

    public void saveProfile(View view) {
        User loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);
        if(loggedUser == null)
            return;

        EditText profileName = findViewById(R.id.profileName);
        EditText profileLastName = findViewById(R.id.profileLastName);
        EditText profileAddress = findViewById(R.id.profileAddress);
        EditText profilePhoneNumber = findViewById(R.id.profilePhoneNumber);
        EditText profilePassword = findViewById(R.id.profilePassword);

        loggedUser.setName(profileName.getText().toString());
        loggedUser.setLastName(profileLastName.getText().toString());
        loggedUser.setAddress(profileAddress.getText().toString());
        loggedUser.setPhoneNumber(profilePhoneNumber.getText().toString());
        loggedUser.setPassword(profilePassword.getText().toString());

        ModelPreferencesManager.put(loggedUser, "loggedInUser");

        profileName.setEnabled(false);
        profileLastName.setEnabled(false);
        profileAddress.setEnabled(false);
        profilePhoneNumber.setEnabled(false);
        profilePassword.setEnabled(false);

        Button updateButton = findViewById(R.id.update);
        updateButton.setVisibility(View.VISIBLE);

        Button cancelButton = findViewById(R.id.cancel);
        cancelButton.setVisibility(View.GONE);

        Button saveButton = findViewById(R.id.save);
        saveButton.setVisibility(View.GONE);
    }

    public void logOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void homePage(View view) {
        Intent intent = new Intent(this, ShowItems.class);
        startActivity(intent);
    }
}
