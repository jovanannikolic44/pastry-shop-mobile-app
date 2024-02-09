package com.example.pastry_shop_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.Basket;
import com.example.pastry_shop_mobile_app.models.Notification;
import com.example.pastry_shop_mobile_app.models.User;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShowBasket extends AppCompatActivity {
    public static final Type notificationListType = new TypeToken<List<Notification>>(){}.getType();
    private User loggedUser;
    private int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_basket);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        notificationId = ModelPreferencesManager.contains("notificationId") ? ModelPreferencesManager.get("notificationId", Integer.class) : 1;

        loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);
        if(loggedUser == null) {
            return;
        }

        List<Basket> orderedItems = ModelPreferencesManager.get("orders_" + loggedUser.getUsername(), ItemDetails.basketListType);
        if(orderedItems == null) {
            Toast.makeText(this, "Korpa je prazna.", Toast.LENGTH_SHORT).show();
            return;
        }

        TableLayout table = findViewById(R.id.basketTable);
        TableRow headerRow =  new TableRow(this);
        headerRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        String[] headers = {"Artikal", "Kolicina", "Cena", "Ukupno"};
        for(int i = 0; i < headers.length; i++) {
            TextView headerView = createTextView(headers[i]);
            headerView.setTextColor(getResources().getColor(R.color.black));
            headerRow.addView(headerView);
        }
        table.addView(headerRow);
        View tableLine = new View(this);
        tableLine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        tableLine.setBackgroundColor(getResources().getColor(android.R.color.black));
        table.addView(tableLine);

        float totalSum = 0;
        for (int i = 0; i < orderedItems.size(); i++) {
            TableRow oneRow = new TableRow(this);
            oneRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView itemNameData = createTextView(orderedItems.get(i).getItemName());
            TextView quantityData = createTextView(String.valueOf(orderedItems.get(i).getQuantity()));
            TextView priceData = createTextView(String.valueOf(orderedItems.get(i).getPrice()));
            TextView totalPriceData = createTextView(String.valueOf(orderedItems.get(i).getTotalPrice()));
            oneRow.addView(itemNameData);
            oneRow.addView(quantityData);
            oneRow.addView(priceData);
            oneRow.addView(totalPriceData);
            totalSum = totalSum + orderedItems.get(i).getTotalPrice();
            table.addView(oneRow);
        }

        TextView sumToPay = findViewById(R.id.sumToPay);
        sumToPay.setText("Ukupan iznos za uplatu: " + totalSum);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView.setPadding(5, 5, 5, 5);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public void confirmOrder(View view) {
        loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);
        if(loggedUser == null) {
            Toast.makeText(this, "Nije ulogovan korisnik.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Basket> orderedItems = ModelPreferencesManager.get("orders_" + loggedUser.getUsername(), ItemDetails.basketListType);
        if(orderedItems == null) {
            Toast.makeText(this, "Nema porudzbina.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Notification> allNotifications = ModelPreferencesManager.get("notifications_" + loggedUser.getUsername(), ShowBasket.notificationListType);
        if(allNotifications == null) {
            allNotifications = new ArrayList<>();
        }

        float totalSum = 0;
        String content = "";
        for (int i = 0; i < orderedItems.size(); i++) {
            if("".equals(content))
                content = orderedItems.get(i).getItemName();
            else
                content = content + "," + orderedItems.get(i).getItemName();
            totalSum = totalSum + orderedItems.get(i).getTotalPrice();
        }

        Notification notification = new Notification(notificationId, content, totalSum);
        allNotifications.add(notification);
        ModelPreferencesManager.put(allNotifications, "notifications_" + loggedUser.getUsername());

        notificationId++;
        ModelPreferencesManager.put(notificationId, "notificationId");

        ModelPreferencesManager.deleteKey("orders_" + loggedUser.getUsername());
        Toast.makeText(this, "Potvrdjena porudzbina.", Toast.LENGTH_SHORT).show();
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
