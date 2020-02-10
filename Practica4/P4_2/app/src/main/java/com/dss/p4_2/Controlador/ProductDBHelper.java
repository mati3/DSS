package com.dss.p4_2.Controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.dss.p4_2.Modelo.Product;


public class ProductDBHelper extends SQLiteOpenHelper {

    private static ProductDBHelper mInstance = null;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products_db";
    private Context mCxt;

    public static ProductDBHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new ProductDBHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mCxt = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table
        db.execSQL(Product.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Product.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertProduct(Product p) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add them
        values.put(Product.COLUMN_RESUMEN, p.getResumen());
        values.put(Product.COLUMN_DESCRIPCION, p.getDescripcion());

        // insert row
        long id = db.insert(Product.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Product getProduct(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Product.TABLE_NAME,
                new String[]{Product.COLUMN_ID, Product.COLUMN_RESUMEN, Product.COLUMN_DESCRIPCION},
                Product.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare product object
        Product p = new Product(
                cursor.getInt(cursor.getColumnIndex(Product.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_RESUMEN)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_DESCRIPCION)));

        // close the db connection
        cursor.close();

        return p;
    }

    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Product.TABLE_NAME + " ORDER BY " +
                Product.COLUMN_DESCRIPCION + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setId(cursor.getInt(cursor.getColumnIndex(Product.COLUMN_ID)));
                p.setResumen(cursor.getString(cursor.getColumnIndex(Product.COLUMN_RESUMEN)));
                p.setDescripcion(cursor.getString(cursor.getColumnIndex(Product.COLUMN_DESCRIPCION)));

                products.add(p);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return products;
    }

    public int getProductCount() {
        String countQuery = "SELECT  * FROM " + Product.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateProduct(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_RESUMEN, p.getResumen());

        // updating row
        return db.update(Product.TABLE_NAME, values, Product.COLUMN_ID + " = ?",
                new String[]{String.valueOf(p.getId())});
    }

    public void deleteProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL(Product.CREATE_TABLE);
        db.close();
    }
}
