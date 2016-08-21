package com.example.anurag.nanodegreeinventoryapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.example.anurag.nanodegreeinventoryapp.Classes.ProductDetails;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by anurag on 7/24/2016.
 */
public class PostDatabase {
    private MyHelper helper;
    private SQLiteDatabase mdatabase;

    public PostDatabase(Context context) {
        helper = new MyHelper(context);
        mdatabase = helper.getWritableDatabase();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public long insertItem(ProductDetails details) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyHelper.COLUMN_NAME, details.getProductName());
        contentValues.put(MyHelper.COLUMN_PRICE, details.getPrice());
        contentValues.put(MyHelper.COLUMN_QUANTITY, details.getPrice());
        contentValues.put(MyHelper.COLUMN_IMAGE, getBitmapAsByteArray(details.getImage()));
        return mdatabase.insert(MyHelper.TABLE_NAME, null, contentValues);
    }

    public int updateItemQuantity(String name, int quantity) {
        String[] selectionArgs = {name};
        ContentValues values = new ContentValues();
        values.put(MyHelper.COLUMN_QUANTITY, quantity);
        return mdatabase.update(MyHelper.TABLE_NAME, values, MyHelper.COLUMN_NAME + " =?", selectionArgs);
    }


    public ArrayList<ProductDetails> getAllItems() {
        ArrayList<ProductDetails> list = new ArrayList<>();
        String[] columns = {
                MyHelper.COLUMN_UID,
                MyHelper.COLUMN_NAME,
                MyHelper.COLUMN_PRICE,
                MyHelper.COLUMN_QUANTITY,
                MyHelper.COLUMN_IMAGE,
        };

        Cursor cursor = mdatabase.query(MyHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ProductDetails current = new ProductDetails();
                current.setProductName(cursor.getString(cursor.getColumnIndex(MyHelper.COLUMN_NAME)));
                current.setPrice(cursor.getInt(cursor.getColumnIndex(MyHelper.COLUMN_PRICE)));
                current.setQuantity(cursor.getInt(cursor.getColumnIndex(MyHelper.COLUMN_QUANTITY)));
                byte[] bytes = cursor.getBlob(cursor.getColumnIndex(MyHelper.COLUMN_IMAGE));
                current.setImage(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                list.add(current);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;

    }


    public void deleteItem(String name) {
        String[] whereArgs = {name};
        mdatabase.delete(MyHelper.TABLE_NAME, MyHelper.COLUMN_NAME + "=?", whereArgs);
    }

    private static class MyHelper extends SQLiteOpenHelper {
        private static String DB_NAME = "inventory";
        private static String TABLE_NAME = "items";
        private static int DB_VERSION = 1;
        private static String COLUMN_UID = "_id";
        private static String COLUMN_NAME = "product_name";
        private static String COLUMN_PRICE = "product_price";
        private static String COLUMN_QUANTITY = "product_quantity";
        private static String COLUMN_IMAGE = "product_image";
        private static String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_IMAGE + " BLOB );";
        private static String DROP_TABLE_ITEMS = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context mContext;


        public MyHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            Log.d("job", "inside MyHelper const");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE_ITEMS);
            } catch (SQLiteException e) {
                Log.d("job", "error");
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE_ITEMS);
                db.execSQL(CREATE_TABLE_ITEMS);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }
    }

}
