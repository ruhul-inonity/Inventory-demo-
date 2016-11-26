package com.example.user.sharedpreferencesdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 19-Nov-16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "myapp.db"; //innitialize database name and version
    public static final int DB_VERSION = 1;
    //declaring columns
    public static final String USER_TABLE = "users";
    private static final String PRODUCT_TABLE = "products";
    private static final String STOCK_TABLE = "stock";
    private static final String SELL_HISTORY_TABLE = "history";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "password";

    private static final String COLUMN_PRODUCT_NAME = "product";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_PRICE = "price";

    private static final String COLUMN_PRODUCT_ID = "p_id";
    private static final String COLUMN_AMOUNT = "amount";

    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_DATE = "date";

    /*create table users(
        id integer primary key autoincrement,
       email text,
       password text);
    */
    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," //very important to include space in the quotations!!
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT);";

    private String CREATE_TABLE_PRODUCT = "CREATE TABLE " + PRODUCT_TABLE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_PRODUCT_NAME + " TEXT," +
            COLUMN_CATEGORY + " TEXT," +
            COLUMN_PRICE + " DOUBLE" +
            ");";

    private String CREATE_TABLE_STOCK = "CREATE TABLE " + STOCK_TABLE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_PRODUCT_ID + " INTEGER," +
            COLUMN_AMOUNT + " INTEGER" +
            ");";

    private String CREATE_TABLE_SELL = "CREATE TABLE " + SELL_HISTORY_TABLE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_QUANTITY + " INTEGER," +
            COLUMN_DATE + " TEXT" +
            ");";


/*    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," //very important to include space in the quotations!!
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT);";*/

    public DBHelper(Context context) {  //explain this constructor and how it works with other activities.
        super(context, DB_NAME, null, DB_VERSION); //SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        // since we have database name and version, we can change default
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_STOCK);
        db.execSQL(CREATE_TABLE_SELL);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //check this methods docuentation!
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE);
        onCreate(db);
    }

    public void addUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();//write to databasen---EXPLAIN THIS!!

        ContentValues values = new ContentValues(); //input values into database
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);

        long id = db.insert(USER_TABLE, null, values); //inserting
        db.close();

    }

    public long addProduct(Product product){
        long id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(COLUMN_PRODUCT_NAME, product.getProductName());
            values.put(COLUMN_CATEGORY, product.getProductCategory());
            values.put(COLUMN_PRICE, product.getProductPrice());
            id = db.insert(PRODUCT_TABLE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return id;
    }


    //get all products
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productArrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();


            Cursor cursor = db.rawQuery("select * from products", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                int productId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                Double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

               /* product.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                product.setProductName(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME)));
                product.setProductCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                product.setProductPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)));*/

                Product product = new Product(productId,name,category,price);
                productArrayList.add(product);
                cursor.moveToNext();
            }
            cursor.close();
        } else
            System.out.print("Cursor is empty");


        db.close();
        return productArrayList;
    }


    //check if user logged in correctly
    public boolean getUser (String email, String pass){
        String selectQuery = "select * from " + USER_TABLE + " where " +COLUMN_EMAIL + " = " + "'"+email+"'"  + " and " + COLUMN_PASS  + " = " + "'"+pass+"'";
        SQLiteDatabase db = this.getReadableDatabase(); //--EXPLAIN THIS!!!!
        Cursor cursor = db.rawQuery(selectQuery, null); //--EXPLAIN THIS ALSO!!

        cursor.moveToFirst(); //goes to the top row

        if(cursor.getCount() >0){ //returns the number of rows in the cursor.
            return true;
        }
        cursor.close();
        db.close();
        return false;

    }

}
