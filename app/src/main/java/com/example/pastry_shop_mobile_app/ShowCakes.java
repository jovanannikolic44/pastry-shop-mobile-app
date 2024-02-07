package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.Item;

import java.util.List;

public class ShowCakes extends AppCompatActivity {
    private String cakesOrCookies = "";
    private List<Item> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cakes);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        cakesOrCookies = ModelPreferencesManager.get("cookiesOrCakes", String.class);
        allItems = ModelPreferencesManager.get(cakesOrCookies, ShowItems.itemListType);

        LinearLayout itemsLayout = findViewById(R.id.itemsLayout);

        for(int i = 0; i < allItems.size(); i++) {
            final int itemIndex = i;
            ImageView itemImage = new ImageView(this);
            itemImage.setLayoutParams(new LinearLayout.LayoutParams(400, 400));
            itemImage.setBackgroundResource(R.color.white);
            itemImage.setImageResource(allItems.get(i).getImageUrl());

            TextView itemTitle = new TextView(this);
            itemTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            itemTitle.setText(allItems.get(i).getName());
            itemTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            itemTitle.setTextColor(getResources().getColor(R.color.black));
            itemTitle.setGravity(Gravity.CENTER);
            itemTitle.setPadding(350, 0, 0 , 0);
            itemTitle.setClickable(true);
            itemTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelPreferencesManager.put(allItems.get(itemIndex), "itemToShow");
                    Intent intent = new Intent(ShowCakes.this, ItemDetails.class);
                    startActivity(intent);
                }
            });

            CardView itemsCardView = new CardView(this);
            LinearLayout.LayoutParams itemCardLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
            if(i == 0)
                itemCardLayoutParams.setMargins(0, 100, 0, 50);
            else if(i == allItems.size() - 1)
                itemCardLayoutParams.setMargins(0, 50, 0, 100);
            else
                itemCardLayoutParams.setMargins(0, 50, 0, 50);
            itemsCardView.setLayoutParams(itemCardLayoutParams);
            itemsCardView.setCardBackgroundColor(getResources().getColor(R.color.white));
            itemsCardView.addView(itemImage);
            itemsCardView.addView(itemTitle);

            itemsLayout.addView(itemsCardView);
        }
    }
}