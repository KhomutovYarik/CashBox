package com.example.cashbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FinishedOrderAdapter extends ArrayAdapter<FinishedOrder> {
    Context context;
    int resource;

    public FinishedOrderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FinishedOrder> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String id = getItem(position).getId();
        String number = getItem(position).getNumber();
        String cashboxName = getItem(position).getCashboxName();
        String storeName = getItem(position).getStoreName();
        String problem = getItem(position).getProblem();
        String problemDesc = getItem(position).getProblemDesc();
        String status = getItem(position).getStatus();
        int rating = getItem(position).getRating();

        FinishedOrder order = new FinishedOrder(id, number, cashboxName, storeName, problem, problemDesc, status, rating);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView numText, cashboxText, storeText, problemDescText;
        RatingBar ratebar;
        ImageView statusImage;

        numText = convertView.findViewById(R.id.orderNumber);
        cashboxText =  convertView.findViewById(R.id.cashboxName);
        storeText =  convertView.findViewById(R.id.storeName);
        problemDescText =  convertView.findViewById(R.id.problemDescription);
        ratebar = convertView.findViewById(R.id.ratingBar);
        statusImage = convertView.findViewById(R.id.statusImage);

        cashboxText.setText(cashboxName + ", ");
        storeText.setText(storeName);
        problemDescText.setText(problem);
        ratebar.setRating(rating);
        if (status.equals("3")) {
            numText.setText("#" + number + ". Завершённая заявка");
            statusImage.setImageResource(R.drawable.ic_check_24px);
        }
        else {
            numText.setText("#" + number + ". Отменённая заявка");
            statusImage.setImageResource(R.drawable.ic_clear_24px);
            ratebar.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
