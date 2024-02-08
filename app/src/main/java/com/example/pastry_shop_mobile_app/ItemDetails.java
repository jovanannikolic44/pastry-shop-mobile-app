package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.pastry_shop_mobile_app.models.Comments;
import com.example.pastry_shop_mobile_app.models.Item;
import com.example.pastry_shop_mobile_app.models.Promotion;
import com.example.pastry_shop_mobile_app.models.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemDetails extends AppCompatActivity {
    public static final Type basketListType = new TypeToken<List<Basket>>(){}.getType();
    public static final Type commentsListType = new TypeToken<List<Comments>>(){}.getType();
    private int orderId = 1;
    private int commentId = 1;
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

        orderId = ModelPreferencesManager.contains("orderId") ? ModelPreferencesManager.get("orderId", Integer.class) : 1;
        commentId = ModelPreferencesManager.contains("commentId") ? ModelPreferencesManager.get("commentId", Integer.class) : 1;

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

        // prikaz komentara
        List<Comments> allComments = ModelPreferencesManager.get(itemToShow.getId(), ItemDetails.commentsListType);
        if(allComments == null) {
            return;
        }
        LinearLayout commentsLayout = findViewById(R.id.commentsLayout);
        TextView noComments = findViewById(R.id.noComments);
        commentsLayout.removeView(noComments);
        for(int i = 0; i < allComments.size(); i++) {
            TextView showComments = new TextView(this);
            showComments.setText(allComments.get(i).getUsername() + ": " + allComments.get(i).getComment());
            showComments.setTextSize(18);
            showComments.setTextColor(getResources().getColor(R.color.black));
            commentsLayout.addView(showComments);
        }
    }

    public void orderItem(View view) {
        TextView itemQuantityView = findViewById(R.id.itemQuantity);
        int itemQuantity = Integer.parseInt(itemQuantityView.getText().toString());
        if(itemQuantity < 1 || itemQuantity > 20) {
            Toast.makeText(this, "Narudzbina sa zadatom kolicinom nije moguca.", Toast.LENGTH_SHORT).show();
            return;
        }
        User loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);
        if(loggedUser == null) {
            return;
        }

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

    public void saveComment(View view) {
        User loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);
        if(loggedUser == null)
            return;

        String commentText = findViewById(R.id.comment).toString();
        if("".equals(commentText)) {
            Toast.makeText(this, "Niste uneli komentar.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Comments> allComments = ModelPreferencesManager.get(itemToShow.getId(), ItemDetails.commentsListType);
        if(allComments == null) {
            allComments = new ArrayList<>();
        }
        Comments newComment = new Comments(commentId, loggedUser.getUsername(), commentText);

        allComments.add(newComment);
        ModelPreferencesManager.put(allComments, itemToShow.getId());

        commentId++;
        ModelPreferencesManager.put(commentId, "commentId");
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