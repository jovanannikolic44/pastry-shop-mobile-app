package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPromotions extends AppCompatActivity {
    private static int pageNumber = 0;
    private ImageView nextImageToShow;
    private TextView nextTitleToShow;
    private TextView nextContentToShow;
    private int[] allImages = {R.drawable.promotion1, R.drawable.promotion2, R.drawable.promotion3};
    private String[] allTitles = {"UZ TORTU, I KOLAC", "POVOLJNIJE ZA MLADENCE", "KO NE VOLI COKOLADNE KOLACE?"};
    private String[] allContent = {"Uz kupljenu tortu na dva ili vise spratova dobija se gratis jedan kolac po izboru.", "Tokom ovog meseca traje popust za narucene mladenacke torte.", "Kupovinom cokoladnih kolaca, dobijate duplo!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_promotions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        nextImageToShow = findViewById(R.id.imageToShow);
        nextTitleToShow = findViewById(R.id.title);
        nextContentToShow = findViewById(R.id.content);

        nextImageToShow.setImageResource(allImages[pageNumber]);
        nextTitleToShow.setText(allTitles[pageNumber]);
        nextContentToShow.setText(allContent[pageNumber]);
    }

    public void showNextPage(View view) {
        pageNumber = (pageNumber + 1) % allImages.length;
        nextImageToShow.setImageResource(allImages[pageNumber]);
        nextTitleToShow.setText(allTitles[pageNumber]);
        nextContentToShow.setText(allContent[pageNumber]);
    }

    public void showPreviousPage(View view) {
        pageNumber = (pageNumber - 1 + allImages.length) % allImages.length;
        nextImageToShow.setImageResource(allImages[pageNumber]);
        nextTitleToShow.setText(allTitles[pageNumber]);
        nextContentToShow.setText(allContent[pageNumber]);
    }
}