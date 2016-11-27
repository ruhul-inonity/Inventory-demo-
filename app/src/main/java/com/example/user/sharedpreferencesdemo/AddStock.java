package com.example.user.sharedpreferencesdemo;

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

import java.util.ArrayList;

public class AddStock extends AppCompatActivity {

    private Spinner productSpinner;
    private EditText availableProducts;
    private Button buttonAddStock;
    DBHelper database;
    public ArrayList<Product> productList;
    private ArrayList productNames;
    int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        database = new DBHelper(this);

        getProductNames();

        productSpinner = (Spinner) findViewById(R.id.spinnerProduct);
        availableProducts = (EditText) findViewById(R.id.editAvailableProducts);
        buttonAddStock = (Button) findViewById(R.id.btnAddStock);

        //spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddStock.this,
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


        buttonAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.valueOf(availableProducts.getText().toString());

                long flag = database.addStock(productId,amount);
                if (flag>0){
                    Toast.makeText(getApplicationContext(),"Stock added",Toast.LENGTH_SHORT).show();
                }
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
