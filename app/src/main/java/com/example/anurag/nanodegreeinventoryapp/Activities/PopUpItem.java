package com.example.anurag.nanodegreeinventoryapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anurag.nanodegreeinventoryapp.Classes.MyApplication;
import com.example.anurag.nanodegreeinventoryapp.Classes.ProductDetails;
import com.example.anurag.nanodegreeinventoryapp.R;

import java.io.FileNotFoundException;

public class PopUpItem extends AppCompatActivity implements View.OnClickListener {
    EditText textName, textPrice, textQuantity;
    TextView imageText;
    Button buttonSave;
    ImageView imageView;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_item);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        textName = (EditText) findViewById(R.id.input_name);
        textPrice = (EditText) findViewById(R.id.input_price);
        textQuantity = (EditText) findViewById(R.id.input_quantity);
        buttonSave = (Button) findViewById(R.id.button_save);
        imageView = (ImageView) findViewById(R.id.upload_image);
        imageText = (TextView) findViewById(R.id.image_text);
        imageView.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri targetUri = data.getData();

                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                        imageView.setImageBitmap(bitmap);
                        imageText.setText("Uploaded");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    public boolean validate(String name, String price, String quantity) {
        boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            valid = false;
            textName.setError(getString(R.string.error_item_name));
        }
        if (price.isEmpty() || Integer.parseInt(price) < 0) {
            valid = false;
            textPrice.setError(getString(R.string.error_item_price));
        }
        if (quantity.isEmpty() || Integer.parseInt(quantity) < 0) {
            valid = false;
            textQuantity.setError(getString(R.string.error_item_quantity));
        }
        return valid;
    }

    public void saveToDatabase() {
        String name = textName.getText().toString();
        String price = textPrice.getText().toString();
        String quantity = textQuantity.getText().toString();
        if (validate(name, price, quantity)) {
            ProductDetails current = new ProductDetails();
            current.setProductName(name);
            current.setQuantity(Integer.parseInt(quantity));
            current.setPrice(Integer.parseInt(price));
            if (bitmap != null) {
                current.setImage(bitmap);
            } else {
                current.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.no_img));
            }

            MyApplication.getWritableDatabase().insertItem(current);
            finish();

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button_save:
                saveToDatabase();
                break;
            case R.id.upload_image:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
        }
    }
}
