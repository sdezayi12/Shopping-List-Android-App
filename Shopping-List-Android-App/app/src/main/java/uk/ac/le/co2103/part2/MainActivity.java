package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Persistent storage to store data in application instead of database
    private SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;

    private ShoppingListAdapter adapter;

    private ShoppingApp application;

    private static final int CREATE_LIST_CODE = 1;

    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 1;

    private void setupRecyclerView() {
        application = (ShoppingApp) getApplication();
        List<ShoppingList> shoppingLists = application.shoppingLists;
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new ShoppingListAdapter(shoppingLists, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Clicking fab button to creating new list class
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateListActivity.class);
                startActivityForResult(intent, CREATE_LIST_CODE);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("MyShoppingLists", Context.MODE_PRIVATE);

        // Checking for permission to access images on phone
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(this)
                        .setTitle("Permission required to access images")
                        // Click OK to accept permission to images
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_EXTERNAL_STORAGE);
                            }
                        })
                        // Click deny to deny permission
                        .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_EXTERNAL_STORAGE);
            }

        } else {
            setupRecyclerView();
        }
    }

    // If permission granted, sets up RecyclerView to display shopping lists, if not denied toast message appears
    @Override
    public void onRequestPermissionsResult(int reqCode, String[]perm, int[]grantPerm) {
        super.onRequestPermissionsResult(reqCode, perm, grantPerm);
        switch (reqCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE: {
                if (grantPerm.length > 0 && grantPerm[0] == PackageManager.PERMISSION_GRANTED) {
                    setupRecyclerView();
                } else {
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void addShoppingList(ShoppingList shoppingList) {
        List<ShoppingList> shoppingLists = application.shoppingLists;
        shoppingLists.add(shoppingList);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent info) {
        super.onActivityResult(reqCode, resCode, info);
        List<ShoppingList> shoppingLists = application.shoppingLists;
        if (reqCode == CREATE_LIST_CODE && resCode == RESULT_OK) {
            // Gets name and image of shopping list
            String name = info.getStringExtra("name");
            String image = info.getStringExtra("image");
            int listId = shoppingLists.size();
            //Add to shopping list object
            ShoppingList shoppingList = new ShoppingList(listId, name, image);
            addShoppingList(shoppingList);
        }
    }
}