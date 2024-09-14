package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    private ProductAdapter prodAdapter;
    private RecyclerView productRecyclerView;

    private ShoppingList findShoppingListById(int listId) {
        ShoppingApp application = (ShoppingApp) getApplication();
        for (ShoppingList list : application.shoppingLists) {
            if (list.getListId() == listId) {
                return list;
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        Intent intent = getIntent();
        int listId = intent.getIntExtra("listId", -1);

        // Calling method above to find shopping list
        ShoppingList shoppingList = findShoppingListById(listId);
        List<Product> products = shoppingList != null ? shoppingList.getProducts() : new ArrayList<>();

        // Finding products of specific shopping list
        productRecyclerView = findViewById(R.id.productRecyclerView);
        prodAdapter = new ProductAdapter(products, this);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setAdapter(prodAdapter);

        // Fab button to add products
        FloatingActionButton fabAddProduct = findViewById(R.id.fabAddProduct);
        fabAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addProdIntent = new Intent(ShoppingListActivity.this, AddProductActivity.class);
                addProdIntent.putExtra("listId", listId);
                startActivity(addProdIntent);
            }
        });
    }

}
