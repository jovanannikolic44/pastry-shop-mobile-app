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

import com.example.pastry_shop_mobile_app.conversion.ModelPreferencesManager;
import com.example.pastry_shop_mobile_app.models.Item;
import com.example.pastry_shop_mobile_app.models.Promotion;
import com.example.pastry_shop_mobile_app.models.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity {
    public static final Type promotionListType = new TypeToken<List<Promotion>>(){}.getType();
    public static final Type itemListType = new TypeToken<List<Item>>(){}.getType();
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

        List<Promotion> predefinedPromotions = ModelPreferencesManager.get("promotions", ShowItems.promotionListType);

        if(predefinedPromotions == null) {
            predefinedPromotions = new ArrayList<>();
            predefinedPromotions.add(new Promotion("1", "UZ TORTU, I KOLAC", "Uz kupljenu tortu na dva ili vise spratova dobija se gratis jedan kolac po izboru.", R.drawable.promotion1));
            predefinedPromotions.add(new Promotion("2", "POVOLJNIJE ZA MLADENCE", "Tokom ovog meseca traje popust za narucene mladenacke torte.", R.drawable.promotion2));
            predefinedPromotions.add(new Promotion("3", "KO NE VOLI COKOLADNE KOLACE?", "Kupovinom cokoladnih kolaca, dobijate duplo!", R.drawable.promotion3));
            ModelPreferencesManager.put(predefinedPromotions, "promotions");
        }

        List<Item> predefinedCakes = ModelPreferencesManager.get("cakes", ShowItems.itemListType);

        if(predefinedCakes == null) {
            predefinedCakes = new ArrayList<>();
            predefinedCakes.add(new Item("1", "COKOLADNA TORTA", "Torta napravljena od najfinije cokolade.", 2000, new String[]{"3 jaja", "6 kasika secera", "3 kasike ulja", "3 kasike mleka", "3 kasike kakaoa", "3 kasike brasna"}, "torta", R.drawable.chocolate_cake));
            predefinedCakes.add(new Item("2", "VOCNA TORTA", "Osvezavajuca torta od raznolikog sezonskog voca.", 3000, new String[]{"5 belanaca", "220g secera", "600ml slatke pavlake", "400g svezeg sezonskog voca", "1 kasika scera u prahu", "50gr badema"}, "torta", R.drawable.fruit_cake));
            predefinedCakes.add(new Item("3", "TORTA SA JAGODAMA", "Osvezavajuca torta sa jagodama i pudingom.", 3000, new String[]{"200gr keksa", "100ml mlevenih badema i lesnika", "100gr maslaca", "300ml slatke pavlake", "300ml pavlake za kuvanje", "150gr secera u prahu", "10gr zelatina u prahu", "3 kasike soka od limuna", "500gr jagoda"}, "torta", R.drawable.strawberries_cake));
            predefinedCakes.add(new Item("4", "TORTA OD COKOLADICA", "Specijalna cokoladna torta sa vasim omiljenim cokoladicama.", 3000, new String[]{"cokoladice po zelji"}, "torta", R.drawable.cake_with_chocolate));
            predefinedCakes.add(new Item("5", "BOMBA", "Neponovljiva torta koja je izdrzala test vremena.", 4000, new String[]{"250gr maslaca", "300g mlevenog keksa", "200gr oraha", "6 jaja", "150ml soka od breskve", "200g secera", "12 kasika secera", "6 kasika secera za karamel"}, "torta", R.drawable.bomba_cake));
            predefinedCakes.add(new Item("6", "REFORMA", "Kraljica medju tortama - najlepsa cokoladna torta", 4000, new String[]{"12 jaja", "320g secera", "320g oraha", "100gr cokolade", "250gr maslaca"}, "torta", R.drawable.reforma_cake));
            ModelPreferencesManager.put(predefinedCakes, "cakes");
        }

        List<Item> predefinedCookies = ModelPreferencesManager.get("cookies", ShowItems.itemListType);

        if(predefinedCookies == null) {
            predefinedCookies = new ArrayList<>();
            predefinedCookies.add(new Item("7", "BROWNIES", "Kolaci sa punim ukusom cokolade.", 400, new String[]{"100gr margarina", "400gr cokolade", "4 jaja", "140gr secera", "150gr brasna", "150gr oraha", "1 kasicica cimeta"}, "kolac", R.drawable.promotion3));
            predefinedCookies.add(new Item("8", "COOKIES", "Hrskavi kolacici sa mrvicama cokolade.", 250, new String[]{"85gr maslaca", "1 jaje", "85gr secera", "1 kesica vanilinog secera", "150gr brasna", "100g cokolade", "1 kasicica soli", "1 kasicica praska za pecivo"}, "kolac", R.drawable.cookies));
            predefinedCookies.add(new Item("9", "BAJADERA", "Jedan od najstarijih kolaca, ali i dalje nezamenljiv.", 500, new String[]{"650gr secera", "18 kasika vode", "210gr margarina", "250gr mlevenih oraha", "250gr mlevenog keksa", "100gr cokolade", "100gr cokolade za kuvanje", "20gr margarina", "3 kasike mleka", "3 kasike ulja"}, "kolac", R.drawable.bajadera));
            predefinedCookies.add(new Item("10", "RAFAELO KUGLICE", "Sjajni ukus kokosa.", 500, new String[]{"200g vode", "500g secera", "500g mleka u prahu", "250g margarina", "400g kokosa", "100g lesnika"}, "kolac", R.drawable.rafaelo_kuglice));
            predefinedCookies.add(new Item("11", "MILKA KOCKE", "Lagan kolac sa lesnikom i Milka cokoladom.", 400, new String[]{"7 jaja", "200g secera", "2 kasike ulja", "100g mlevenog lesnika", "1 kasika brasna", "5g praska za pecivo", "800ml mleka", "120g gustina", "10g vanilin secera", "250g maslaca", "160g mlecne Milka cokolade", "100g bele Milka cokolade", "200ml slatke pavlake", "1 kasika kisele pavlake"}, "kolac", R.drawable.milka_kocke));
            predefinedCookies.add(new Item("12", "TRILECE", "Kolac koji morate da probate!", 350, new String[]{"6 jaja", "100gr secera", "200gr brasna", "1/2 praska za pecivo", "1 vanilin secer", "400ml slatke pavlake", "300ml kondenzovanog mleka", "800ml mleka", "300gr karamel krema"}, "kolac", R.drawable.trilece));
            ModelPreferencesManager.put(predefinedCookies, "cookies");
        }
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
            if("SVE TORTE".equals(allButtonTexts[pageNumber])) {
                ModelPreferencesManager.put("cakes", "cookiesOrCakes");
                Intent intent = new Intent(this, ShowCakes.class);
                startActivity(intent);
            }
            else if("SVI KOLACI".equals(allButtonTexts[pageNumber])) {
                ModelPreferencesManager.put("cookies", "cookiesOrCakes");
                Intent intent = new Intent(this, ShowCakes.class);
                startActivity(intent);
            }
        }
        else {
            Intent intent = new Intent(this, ShowPromotions.class);
            startActivity(intent);
        }
    }
}