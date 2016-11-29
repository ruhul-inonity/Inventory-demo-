package com.example.user.sharedpreferencesdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.sharedpreferencesdemo.Product;
import com.example.user.sharedpreferencesdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11/27/16.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

        private List<Product> productsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name, amount;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.productName);
                amount = (TextView) view.findViewById(R.id.productAmount);
            }
        }


        public ProductsAdapter(ArrayList<Product> productsList) {
            this.productsList = productsList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_row, parent, false);

            return new MyViewHolder(itemView);
        }
    @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Product product = productsList.get(position);
            holder.name.setText(product.getProductName());
            holder.amount.setText(String.valueOf(product.getAvailableProduct()));

        }

        @Override
        public int getItemCount() {
            return productsList.size();
        }

}
