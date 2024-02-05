package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class ShowItems extends AppCompatActivity {
    private static int pageNumber = 0;
    private ImageView nextImageToShow;
    private Button nextButtonText;
    private int[] allImages = {R.drawable.show_items_cake, R.drawable.show_items_cupcakes, R.drawable.show_items_promotion};
    private String[] allButtonTexts = {"SVE TORTE", "SVI KOLACI", "PROMOCIJE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        nextImageToShow = findViewById(R.id.imageToShow);
        nextButtonText = findViewById(R.id.showItemsButton);

        nextImageToShow.setImageResource(allImages[pageNumber]);
        nextButtonText.setText(allButtonTexts[pageNumber]);
    }

    public void showNextPage(View view) {
        pageNumber = (pageNumber + 1) % allImages.length;
        nextImageToShow.setImageResource(allImages[pageNumber]);
        nextButtonText.setText(allButtonTexts[pageNumber]);
    }

    public void showPreviousPage(View view) {
        pageNumber = (pageNumber - 1 + allImages.length) % allImages.length;
        nextImageToShow.setImageResource(allImages[pageNumber]);
        nextButtonText.setText(allButtonTexts[pageNumber]);
    }

    public void showItems(View view) {
        if(pageNumber == 0 || pageNumber == 1) {
            // show Cakes and Cookies
        }
        else {
            // show promotions
            Intent intent = new Intent(this, ShowPromotions.class);
            startActivity(intent);
        }
    }
}