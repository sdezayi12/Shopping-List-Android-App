package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class CreateListActivity extends AppCompatActivity {
    private static final int SELECT_IMAGE_CODE = 1;

    private Uri image;

    private EditText listNameEditText;

    private ImageView imgImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        listNameEditText = findViewById(R.id.listNameEditText);
        imgImageView = findViewById(R.id.imgImageView);

        // Create list button
        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = listNameEditText.getText().toString();
                String imageString = image != null ? image.toString() : null;

                // Name and image information
                Intent resIntent = new Intent();
                resIntent.putExtra("name", name);
                resIntent.putExtra("image", imageString);
                setResult(RESULT_OK, resIntent);
                finish();
            }
        });

        // Selecting gallery image button
        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_IMAGE_CODE);
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, @Nullable Intent info) {
        super.onActivityResult(reqCode, resCode, info);
        if (reqCode == SELECT_IMAGE_CODE && resCode == RESULT_OK && info != null && info.getData() != null) {
            // Gets selected image URI from result Intent
            image = info.getData();
            // Persists URI permission to access image from gallery
            getContentResolver().takePersistableUriPermission(image, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // Displays selected image
            imgImageView.setImageURI(image);
            imgImageView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }
}