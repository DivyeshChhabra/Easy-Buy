package com.example.easybuy;

import static com.example.easybuy.OrderActivity.bridge;
import static com.example.easybuy.OrderActivity.orderList;
import static com.example.easybuy.OrderActivity.ordersViews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easybuy.model.Order;
import com.example.easybuy.utility.MailUtility;
import com.example.easybuy.utility.OrderUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView orderId;
    TextView productTitle;
    TextView customerName;
    TextView customerAddress;
    TextView orderStatus;
    TextView orderTime;
    TextView deliveryDate;
    TextView quantity;
    TextView orderPrice;

    ImageView productImage;

    Button deliverOrder;

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
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#24A0ED")));

        sharedPreferences = this.getSharedPreferences("com.example.easybuy", Context.MODE_PRIVATE);

        orderId = findViewById(R.id.orderID);
        productTitle = findViewById(R.id.productTitle);
        customerName = findViewById(R.id.customerName);
        customerAddress = findViewById(R.id.customerAddress);
        orderStatus = findViewById(R.id.orderStatus);
        orderTime = findViewById(R.id.orderTime);
        deliveryDate = findViewById(R.id.deliveryDate);
        quantity = findViewById(R.id.quantity);
        orderPrice = findViewById(R.id.orderPrice);

        productImage = findViewById(R.id.productImage);

        deliverOrder = findViewById(R.id.deliver);

        Intent intent = getIntent();
        int orderIndex = intent.getIntExtra("Order",0);

        try{
            orderId.append(Integer.toString(orderList.get().get(orderIndex).getId()));
            productTitle.setText(orderList.get().get(orderIndex).getProductName());

            int imageID = getResources().getIdentifier(orderList.get().get(orderIndex).getProductImage().split("\\.")[0].toLowerCase(),"drawable",this.getPackageName());
            productImage.setImageDrawable(getResources().getDrawable(imageID));

            customerName.setText(orderList.get().get(orderIndex).getCustomerName());
            customerAddress.setText(orderList.get().get(orderIndex).getCustomerAddress());

            setOrderStatus(orderIndex);
            orderTime.setText(orderList.get().get(orderIndex).getOrderPlacedDate());
            setDeliveryDate(orderIndex);
            quantity.setText(Integer.toString(orderList.get().get(orderIndex).getQuantity()));
            orderPrice.setText("₹" + orderList.get().get(orderIndex).getTotalPrice());

            if(orderList.get().get(orderIndex).getOrderStatus().equals("Delivered")){
                disableButton();
            }else{
                deliverOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(sharedPreferences.getString("email",null) == null){
                            Toast.makeText(MainActivity.this, "Login before Delivery", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Deliver Your Order")
                                .setMessage("Has the customer completed the payment?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
                                        Calendar orderDeliveryDate = Calendar.getInstance();
                                        String orderDelivered = dateFormat.format(orderDeliveryDate.getTime());

                                        OrderUtility orderUtility = new OrderUtility();

                                        try{
                                            orderList.get().get(orderIndex).setOrderStatus("Delivered");
                                            orderList.get().get(orderIndex).setOrderDeliveredDate(orderDelivered);

                                            ordersViews.get(orderIndex).setOrderStatus("Delivered");
                                            bridge.notifyDataSetChanged();

                                            setOrderStatus(orderIndex);
                                            setDeliveryDate(orderIndex);
                                            disableButton();

                                            orderUtility.deliverOrder(orderList.get().get(orderIndex));

                                            Toast.makeText(MainActivity.this, "Order Delivered!!!", Toast.LENGTH_SHORT).show();

                                            sendConfirmationMail(orderList.get().get(orderIndex));
                                        } catch (Exception exception) {exception.printStackTrace();}
                                    }
                                })
                                .setNegativeButton("No",null)
                                .show();
                    }
                });
            }
        } catch (Exception exception) { exception.printStackTrace(); }
    }

    private void setOrderStatus(int orderId){
        try{
            orderStatus.setText(orderList.get().get(orderId).getOrderStatus());
            orderStatus.setTextColor(orderList.get().get(orderId).getOrderStatus().equals("Pending") ? Color.parseColor("#CC0000") : Color.parseColor("#007E33"));
        } catch (Exception exception) {exception.printStackTrace();}
    }

    private void setDeliveryDate(int orderId){
        try{
            String orderStatus = orderList.get().get(orderId).getOrderStatus();
            String setDeliveryDate = orderList.get().get(orderId).getOrderDeliveredDate();

            deliveryDate.setText(orderStatus.equals("Pending") ? "Expected: " + setDeliveryDate : setDeliveryDate);
        } catch (Exception exception) {exception.printStackTrace();}
    }

    private void disableButton(){
        deliverOrder.setEnabled(false);
        deliverOrder.setVisibility(View.INVISIBLE);
    }

    private void sendConfirmationMail(Order order){
        String message = "Hi " + order.getCustomerName() + "!!\n" +
                "This is to confirm that we have successfully delivered your Order on " + order.getOrderDeliveredDate() + ".\n\n" +
                "ORDER ID: " + order.getId() + "\n" +
                "Product: " + order.getProductName() + " (Quantity: " + order.getQuantity() + ").\n\n" +
                "Delivered By: " + sharedPreferences.getString("name",null) + " (Contact - " + sharedPreferences.getString("phone",null) + ")\n\n" +
                "We hope you liked the products and our service.\n" +
                "See you back on Easy Buy soon!!\n\n" +
                "Happy Shopping,\n" +
                "EasyBuy Team";
        String subject = "Easy Buy - Order Delivered";

        MailUtility mailUtility = new MailUtility(order.getCustomerEmail(),message,subject);
        mailUtility.sendMail();
    }

    public void getLocation(View view) {
        Log.i("info","Clicked");
        Intent intent = new Intent(getApplicationContext(),LocationActivity.class);
        intent.putExtra("Customer Address",customerAddress.getText());
        startActivity(intent);
    }
}