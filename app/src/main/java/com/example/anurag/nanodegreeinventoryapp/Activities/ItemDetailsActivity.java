package com.example.anurag.nanodegreeinventoryapp.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anurag.nanodegreeinventoryapp.Alert.MyAlert;
import com.example.anurag.nanodegreeinventoryapp.Classes.MyApplication;
import com.example.anurag.nanodegreeinventoryapp.R;

public class ItemDetailsActivity extends AppCompatActivity implements View.OnClickListener {
     int quantity;
    Toolbar toolbar;
    Button mSale, mModify, mOrder, mDelete;
    ImageView imageView;
    TextView tQuantity, tName;
    String name = "XYZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        imageView = (ImageView) findViewById(R.id.imageView);
        byte[] bytes = intent.getByteArrayExtra("image");
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        imageView.setBackground(bitmapDrawable);
        tName = (TextView) findViewById(R.id.item_name);
        tName.setText(name);
        mSale = (Button) findViewById(R.id.sale_button);
        mModify = (Button) findViewById(R.id.modify_button);
        mOrder = (Button) findViewById(R.id.order_button);
        mDelete = (Button) findViewById(R.id.delete_button);
        tQuantity = (TextView) findViewById(R.id.item_quantity);
        quantity = MyApplication.getWritableDatabase().getQuantity(name);
        tQuantity.setText(quantity + "");
        mSale.setOnClickListener(this);
        mModify.setOnClickListener(this);
        mOrder.setOnClickListener(this);
        mDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.sale_button:
                if (quantity > 0) {
                    quantity=MyApplication.getWritableDatabase().getQuantity(name);
                    quantity=quantity-1;
                    MyApplication.getWritableDatabase().updateItemQuantity(name,quantity);
                    tQuantity.setText(quantity + "");
                } else {
                    Toast.makeText(this, "No more items left", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.modify_button:
                EditText editText = (EditText) findViewById(R.id.modify_text);
                String textValue = editText.getText().toString();
                if (!textValue.isEmpty()) {
                    Log.d("value", Integer.parseInt(textValue) + "");
                    MyApplication.getWritableDatabase().updateItemQuantity(name, Integer.parseInt(textValue));
                    tQuantity.setText(textValue);
                    editText.setHint(getResources().getString(R.string.string_modify_text));
                } else {
                    editText.setError("Please enter some value");
                }
                break;
            case R.id.delete_button:
                MyAlert alert = MyAlert.newInstance(name);
                alert.setCancelable(false);
                alert.show(getFragmentManager(), "Dialog");

                break;
            case R.id.order_button:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("message/rfc822");
                String[] to = {"xyz@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, to)
                        .putExtra(Intent.EXTRA_SUBJECT, "order for item " + name);
                Intent chooser = Intent.createChooser(intent, "Order Items");
                startActivity(chooser);
                break;
        }
    }
}
