package com.example.user.sharedpreferencesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {
    private DBHelper database;
    private EditText editTextProductName,editTextProductPrice,editTextProductCategoy;
    private Button buttonAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        database = new DBHelper(this);

        editTextProductName = (EditText) findViewById(R.id.editProductName);
        editTextProductCategoy = (EditText) findViewById(R.id.editProductCategory);
        editTextProductPrice = (EditText) findViewById(R.id.editProductPrice);
        buttonAddProduct = (Button) findViewById(R.id.btnAddProduct);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName,productCategory;
                Double price;

                productName = editTextProductName.getText().toString();
                productCategory = editTextProductCategoy.getText().toString();
                price = Double.parseDouble(editTextProductPrice.getText().toString());

                Product product = new Product(productName,productCategory,price);
                addProductToDb(product);

                Intent intent = new Intent(AddProduct.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addProductToDb(Product product) {
        long id = database.addProduct(product);

        if (id > 0){
            Toast.makeText(getApplicationContext(),"Product Added",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(),"Product Insertion Failed",Toast.LENGTH_LONG).show();
        }
    }
}
