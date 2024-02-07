package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.Item;

public class ItemDetails extends AppCompatActivity {
    private Item itemToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        itemToShow = ModelPreferencesManager.get("itemToShow", Item.class);

        ImageView itemImage = findViewById(R.id.itemImage);
        itemImage.setImageResource(itemToShow.getImageUrl());

        TextView itemTitle = findViewById(R.id.itemTitle);
        itemTitle.setText(itemToShow.getName());

        TextView itemDescription = findViewById(R.id.itemDescription);
        itemDescription.setText(itemToShow.getDescription());

        TextView itemPrice = findViewById(R.id.itemPrice);
        itemPrice.setText(String.valueOf(itemToShow.getPrice()));

        LinearLayout ingredientsLayout = findViewById(R.id.ingredientsLayout);
        for(int i = 0; i < itemToShow.getComposition().length; i++) {
            TextView ingredients = new TextView(this);
            ingredients.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ingredients.setText("- " + itemToShow.getComposition()[i]);
            ingredients.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            ingredients.setTextColor(getResources().getColor(R.color.black));
            ingredientsLayout.addView(ingredients);
        }
    }
}