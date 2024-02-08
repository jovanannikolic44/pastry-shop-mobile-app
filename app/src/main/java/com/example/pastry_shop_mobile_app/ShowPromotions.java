package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.Promotion;

import java.util.List;

public class ShowPromotions extends AppCompatActivity {
    private static int pageNumber = 0;
    private ImageView nextImageToShow;
    private TextView nextTitleToShow;
    private TextView nextContentToShow;
    private List<Promotion> allPromotions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_promotions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        allPromotions = ModelPreferencesManager.get("promotions", ShowItems.promotionListType);
        if(allPromotions != null) {
            nextImageToShow = findViewById(R.id.imageToShow);
            nextTitleToShow = findViewById(R.id.title);
            nextContentToShow = findViewById(R.id.content);

            nextImageToShow.setImageResource(allPromotions.get(pageNumber).getImageUrl());
            nextTitleToShow.setText(allPromotions.get(pageNumber).getName());
            nextContentToShow.setText(allPromotions.get(pageNumber).getDescription());
        }
    }

    public void showNextPage(View view) {
        pageNumber = (pageNumber + 1) % allPromotions.size();
        nextImageToShow.setImageResource(allPromotions.get(pageNumber).getImageUrl());
        nextTitleToShow.setText(allPromotions.get(pageNumber).getName());
        nextContentToShow.setText(allPromotions.get(pageNumber).getDescription());
    }

    public void showPreviousPage(View view) {
        pageNumber = (pageNumber - 1 + allPromotions.size()) % allPromotions.size();
        nextImageToShow.setImageResource(allPromotions.get(pageNumber).getImageUrl());
        nextTitleToShow.setText(allPromotions.get(pageNumber).getName());
        nextContentToShow.setText(allPromotions.get(pageNumber).getDescription());
    }

    public void showBasket(View view) {
        Intent intent = new Intent(this, ShowBasket.class);
        startActivity(intent);
    }

    public void logOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void userProfile(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }
}