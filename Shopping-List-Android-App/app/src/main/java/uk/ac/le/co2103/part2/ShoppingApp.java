package uk.ac.le.co2103.part2;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

// Manages shopping lists throughout app
public class ShoppingApp extends Application {
    public List<ShoppingList> shoppingLists;

    @Override
    public void onCreate() {
        super.onCreate();
        shoppingLists = new ArrayList<>();
    }
}
