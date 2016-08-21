package com.example.anurag.nanodegreeinventoryapp.Classes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anurag.nanodegreeinventoryapp.Activities.ItemDetailsActivity;
import com.example.anurag.nanodegreeinventoryapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by anurag on 7/9/2016.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyHolder> {

    Context context;
    ArrayList<ProductDetails> list = new ArrayList<>();
    LayoutInflater inflater;

    public CardViewAdapter(Context context, ArrayList<ProductDetails> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        Log.d("garima", "bind ");
        final ProductDetails current = list.get(position);
        Log.d("rama", "current" + current);

        holder.itemQuantity.setText(current.getQuantity() + " Pieces");
        holder.itemPrice.setText(current.getPrice() + "");
        holder.itemName.setText(current.getProductName());
        holder.imageView.setImageBitmap(current.getImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra("name", current.getProductName());
                intent.putExtra("price", current.getPrice());
                intent.putExtra("quantity", current.getQuantity());
                intent.putExtra("image", getBitmapAsByteArray(current.getImage()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("garima", "size" + list.size());
        return list.size();

    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView itemQuantity;
        TextView itemPrice;
        TextView itemName;
        CardView cardView;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            Log.d("garima", "Myholder");
            itemQuantity = (TextView) itemView.findViewById(R.id.quantity_value);
            itemPrice = (TextView) itemView.findViewById(R.id.price_value);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
