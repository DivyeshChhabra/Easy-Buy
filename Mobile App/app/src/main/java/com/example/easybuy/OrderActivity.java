package com.example.easybuy;

import com.example.easybuy.custom.OrdersView;
import com.example.easybuy.custom.OrdersViewAdapter;
import com.example.easybuy.model.Order;
import com.example.easybuy.utility.OrderUtility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    ListView orders;
    static AsyncTask<Void,Void,List<Order>> orderList;
    static List<OrdersView> ordersViews = new ArrayList<>();
    static OrdersViewAdapter bridge;

    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.logout){
            sharedPreferences.edit().clear().apply();

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#24A0ED")));

        sharedPreferences = this.getSharedPreferences("com.example.easybuy", Context.MODE_PRIVATE);

        orders = findViewById(R.id.orderList);
        ordersViews.clear();

        try{

            OrderUtility orderUtility = new OrderUtility();

            orderList = orderUtility.getTotalOrders();

            for(int i=0;i<orderList.get().size();i++){

                int imageID = getResources().getIdentifier(orderList.get().get(i).getProductImage().split("\\.")[0].toLowerCase(),"drawable",this.getPackageName());
                Drawable res = getResources().getDrawable(imageID);

                ordersViews.add(new OrdersView(res,orderList.get().get(i).getProductName(),orderList.get().get(i).getOrderStatus()));
            }

            bridge = new OrdersViewAdapter(this,ordersViews);
            orders.setAdapter(bridge);

            orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("Order",position);

                    startActivity(intent);
                }
            });
        } catch (Exception exception) {exception.printStackTrace();}

    }
}