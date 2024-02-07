package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.Basket;
import com.example.pastry_shop_mobile_app.models.Item;
import com.example.pastry_shop_mobile_app.models.Promotion;
import com.example.pastry_shop_mobile_app.models.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemDetails extends AppCompatActivity {
    public static final Type basketListType = new TypeToken<List<Basket>>(){}.getType();
    private int orderId = 1;
    private int commentId = 1;
    private Item itemToShow;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        orderId = ModelPreferencesManager.contains("orderId") ? ModelPreferencesManager.get("orderId", Integer.class) : 1;
        commentId = ModelPreferencesManager.contains("commentId") ? ModelPreferencesManager.get("commentId", Integer.class) : 1;

        // get ids from storage
//        }
//        System.out.println("ORDER ID " + orderId);
//        commentId = ModelPreferencesManager.get("commentId", Integer.class);

//        ModelPreferencesManager.put(orderId, "orderId");
//        ModelPreferencesManager.put(commentId, "commentId");

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

    public void orderItem(View view) {
        TextView itemQuantityView = findViewById(R.id.itemQuantity);
        int itemQuantity = Integer.parseInt(itemQuantityView.getText().toString());
        if(itemQuantity < 1 || itemQuantity > 20) {
            Toast.makeText(this, "Narudzbina sa zadatom kolicinom nije moguca.", Toast.LENGTH_SHORT).show();
            return;
        }
        loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);

        List<Basket> orderedItems = ModelPreferencesManager.get("orders_" + loggedUser.getUsername(), ItemDetails.basketListType);
        if(orderedItems == null) {
            orderedItems = new ArrayList<>();
        }
        float totalPrice = itemToShow.getPrice() * itemQuantity;
        Basket newPurchase = new Basket(orderId, itemToShow.getName(), itemQuantity, itemToShow.getPrice(), totalPrice);

        orderedItems.add(newPurchase);
        ModelPreferencesManager.put(orderedItems, "orders_" + loggedUser.getUsername());

        orderId++;
        ModelPreferencesManager.put(orderId, "orderId");

        Toast.makeText(this, "Narudzbina je evidentirana. Mozete je naci u korpi.", Toast.LENGTH_SHORT).show();
    }
}