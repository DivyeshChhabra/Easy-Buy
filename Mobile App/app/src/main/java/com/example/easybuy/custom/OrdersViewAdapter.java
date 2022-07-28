package com.example.easybuy.custom;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easybuy.R;

import java.util.List;

public class OrdersViewAdapter extends ArrayAdapter<OrdersView> {

    public OrdersViewAdapter(@NonNull Context context, @NonNull List<OrdersView> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.order_list_view, parent, false);
        }

        OrdersView currentOrderPosition = getItem(position);

        ImageView productImage = currentItemView.findViewById(R.id.productImage);
        assert currentOrderPosition != null;
        productImage.setImageDrawable(currentOrderPosition.getImageSource());

        TextView productTitle = currentItemView.findViewById(R.id.productTitle);
        productTitle.setText(currentOrderPosition.getProductName());

        TextView orderStatus = currentItemView.findViewById(R.id.orderStatus);
        orderStatus.setText("Order Status: ");
        if(currentOrderPosition.getOrderStatus().equals("Pending")) orderStatus.setTextColor(Color.parseColor("#CC0000"));
        else orderStatus.setTextColor(Color.parseColor("#007E33"));
        orderStatus.append(currentOrderPosition.getOrderStatus());

        return currentItemView;
    }
}
