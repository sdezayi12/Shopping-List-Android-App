package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {
    private ShoppingList shoppingList;

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
        setContentView(R.layout.activity_add_product);

        Intent intent = getIntent();
        int listId = intent.getIntExtra("listId", -1);
        // Finding shopping list with method above
        shoppingList = findShoppingListById(listId);
    }

    public void addProduct(View view) {
        // Product information
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextQuantity = findViewById(R.id.editTextQuantity);
        Spinner spinner = findViewById(R.id.spinner);
        String prodName = editTextName.getText().toString();
        int prodQuantity = Integer.parseInt(editTextQuantity.getText().toString());
        String prodUnit = spinner.getSelectedItem().toString();

        // Error for duplicated products as should be unique
        if (shoppingList != null) {
            for (Product product : shoppingList.getProducts()) {
                if (product.getName().equals(prodName)) {
                    Toast.makeText(this, "Product already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Putting product in shopping list
            Product newProd = new Product(prodName, prodQuantity, prodUnit);
            shoppingList.addProd(newProd);

            Intent intent = new Intent(this, ShoppingListActivity.class);
            intent.putExtra("listId", shoppingList.getListId());
            startActivity(intent);
        }
    }

}
