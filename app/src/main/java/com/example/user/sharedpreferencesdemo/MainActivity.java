package com.example.user.sharedpreferencesdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
            return super.onOptionsItemSelected(item);
    }
}
