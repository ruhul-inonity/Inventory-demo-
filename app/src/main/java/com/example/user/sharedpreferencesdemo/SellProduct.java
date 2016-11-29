package com.example.user.sharedpreferencesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SellProduct extends AppCompatActivity {

    private Spinner productSpinner;
    private EditText noOfProducts;
    private Button buttonSell;
    DBHelper database;
    public ArrayList<Product> productList;
    private ArrayList productNames;
    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        database = new DBHelper(this);
        getProductNames();

        productSpinner = (Spinner) findViewById(R.id.spinnerProductForSell);
        noOfProducts = (EditText) findViewById(R.id.editNoOfProducts);
        buttonSell = (Button) findViewById(R.id.btnSell);

        //spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SellProduct.this,
                android.R.layout.simple_spinner_item,productNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productSpinner.setAdapter(adapter);

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pname = (String) productNames.get(position);//finding selected items name by using adapter position
                Toast.makeText(getApplicationContext(),pname,Toast.LENGTH_SHORT).show();

                getProductIdFromDb(pname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(noOfProducts.getText().toString());

                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateandTime = date.format(new Date());
                Log.i("current date"," ................."+currentDateandTime);

                Product product = new Product();
                product.setId(productId);
                product.setProductQuantity(quantity);
                product.setDate(currentDateandTime);

                long flag = database.sellProduct(product);
                if (flag>0){
                    Toast.makeText(getApplicationContext(),"Product Sold",Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(SellProduct.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void getProductIdFromDb(String p_name) {
        productId = database.getAccountId(p_name);
        Log.i("product id ","................. " + productId);
    }

    private void getProductNames() {
        productList = database.getAllProducts();
        productNames = new ArrayList();

        for (Product product : productList){
            //int index = Integer.valueOf(product.getId());
            productNames.add(product.getProductName());
            Log.i("product id ","................. "+product.getId()+" product name "+product.getProductName());
        }
    }
}
