package com.example.cashbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ActiveOrderAdapter extends ArrayAdapter<ActiveOrder> {
    Context context;
    int resource;

    public ActiveOrderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ActiveOrder> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String number = getItem(position).getNumber();
        String cashboxName = getItem(position).getCashboxName();
        String storeName = getItem(position).getStoreName();
        String problemDesc = getItem(position).getProblemDesc();
        String offersNumber = getItem(position).getOffersNumber();
        String minPrice = getItem(position).getMinPrice();

        ActiveOrder order = new ActiveOrder(number, cashboxName, storeName, problemDesc, offersNumber, minPrice);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView numText, cashboxText, storeText, problemDescText, offersNumberText, minPriceText;
        //ImageView offersImage, priceImage, cancelImage;

        numText = convertView.findViewById(R.id.orderNumber);
        cashboxText =  convertView.findViewById(R.id.cashboxName);
        storeText =  convertView.findViewById(R.id.storeName);
        problemDescText =  convertView.findViewById(R.id.problemDescription);
        offersNumberText =  convertView.findViewById(R.id.currentOffers);
        minPriceText =  convertView.findViewById(R.id.minPrice);
//        offersImage =  convertView.findViewById(R.id.offersImage);
//        priceImage =  convertView.findViewById(R.id.cardImage);
//        cancelImage =  convertView.findViewById(R.id.removeButton);

        numText.setText(number);
        cashboxText.setText(cashboxName);
        storeText.setText(storeName);
        problemDescText.setText(problemDesc);
        offersNumberText.setText(offersNumber);
        minPriceText.setText(minPrice);
//        offersImage.setImageResource(R.drawable.offersimage);
//        priceImage.setImageResource(R.drawable.cardimage);
//        cancelImage.setImageResource(R.drawable.removeimage);

        return convertView;
    }
}