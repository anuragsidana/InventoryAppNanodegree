package com.example.anurag.nanodegreeinventoryapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.anurag.nanodegreeinventoryapp.Classes.CardViewAdapter;
import com.example.anurag.nanodegreeinventoryapp.Classes.MyApplication;
import com.example.anurag.nanodegreeinventoryapp.Classes.ProductDetails;
import com.example.anurag.nanodegreeinventoryapp.Classes.VerticalSpace;
import com.example.anurag.nanodegreeinventoryapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button;
    int i = 0;
    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        button = (Button) findViewById(R.id.addItem);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new VerticalSpace(30));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PopUpItem.class));
            }
        });

        showResults();
    }

    public void showResults() {
        ArrayList<ProductDetails> list = MyApplication.getWritableDatabase().getAllItems();
        LinearLayout layout = (LinearLayout) findViewById(R.id.textLayout);
        if (!list.isEmpty()) {
            CardViewAdapter adapter = new CardViewAdapter(this, list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            layout.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        showResults();
    }
}
