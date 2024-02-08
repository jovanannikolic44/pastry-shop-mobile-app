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
import com.example.pastry_shop_mobile_app.models.User;

import java.util.List;

public class UserNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }

        User loggedUser = ModelPreferencesManager.get("loggedInUser", User.class);
        if(loggedUser == null) {
            return;
        }

        List<Basket> orderedItems = ModelPreferencesManager.get("notifications_" + loggedUser.getUsername(), ItemDetails.basketListType);
        if(orderedItems == null) {
            Toast.makeText(this, "Nemate narudzbina.", Toast.LENGTH_SHORT).show();
            return;
        }

        TableLayout table = findViewById(R.id.notificationTable);
        TableRow headerRow =  new TableRow(this);
        headerRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        String[] headers = {"Sadrzaj", "Cena"};
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
        String content = "";
        for (int i = 0; i < orderedItems.size(); i++) {
            if("".equals(content))
                content = orderedItems.get(i).getItemName();
            else
                content = content + "," + orderedItems.get(i).getItemName();
            totalSum = totalSum + orderedItems.get(i).getTotalPrice();

        }

        TableRow oneRow = new TableRow(this);
        oneRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView itemContent = createTextView(content);
        TextView sumToPay = createTextView(String.valueOf(totalSum));
        oneRow.addView(itemContent);
        oneRow.addView(sumToPay);
        table.addView(oneRow);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView.setPadding(5, 5, 5, 5);
        textView.setGravity(Gravity.CENTER);
        return textView;
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