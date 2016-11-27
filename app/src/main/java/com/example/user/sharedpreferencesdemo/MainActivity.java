package com.example.user.sharedpreferencesdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sharedpreferencesdemo.adapter.ProductsAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private Session session;
    DBHelper database;

    ArrayList<Product> productsList ;
    private RecyclerView recyclerView;
    private ProductsAdapter pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DBHelper(this);
        productsList = new ArrayList<>();

        session = new Session(this);
        if (!session.loggedin()) {
            logout();
        }
        logout = (Button) findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pAdapter = new ProductsAdapter(productsList);

        //recycle view
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);

        prepareProductList();
    }

    private void prepareProductList() {

        ArrayList<Product> pro = new ArrayList<>();
        try {
            pro = database.getProductsAndAmounts();
            Log.i("data","..............."+productsList.get(1).getProductName()+productsList.get(1).getAvailableProduct());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Product p : pro){
            Product pr = new Product();
            pr.setProductName(p.getProductName());
            pr.setAvailableProduct(p.getAvailableProduct());
            productsList.add(pr);
            Log.e("prod","..............."+p.getProductName()+" "+p.getAvailableProduct());
        }
        pAdapter.notifyDataSetChanged();
    }


    private void logout() {
        session.setLoggedIn(false);
        finish();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

         if (id == R.id.addProduct) {
              Intent intent = new Intent(getApplicationContext(), AddProduct.class);
             startActivity(intent);
             return true;
         }
       else if (id == R.id.addStock) {
            Intent intent = new Intent(getApplicationContext(), AddStock.class);
            startActivity(intent);
            return true;
        }
         else if (id == R.id.sellProduct) {
             Intent intent = new Intent(getApplicationContext(), SellProduct.class);
             startActivity(intent);
             return true;
         }
            return super.onOptionsItemSelected(item);
    }
}
