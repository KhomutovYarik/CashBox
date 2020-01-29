package com.example.cashbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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

    private void setOnClick(final ImageView btn, final String id, final String number, final String cashboxName, final String storeName, final String problem, final String problemDesc){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("orders").child(id).setValue(new Order(id, number, cashboxName, storeName, problem, problemDesc, "2"));
            }
        });
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
        String offersNumber = getItem(position).getOffersNumber();
        String minPrice = getItem(position).getMinPrice();

        ActiveOrder order = new ActiveOrder(id, number, cashboxName, storeName, problem, problemDesc, "1", offersNumber, minPrice);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView numText, cashboxText, storeText, problemDescText, offersNumberText, minPriceText;
        final ImageView removeButton, offersImage, cardImage;

        numText = convertView.findViewById(R.id.orderNumber);
        cashboxText = convertView.findViewById(R.id.cashboxName);
        storeText = convertView.findViewById(R.id.storeName);
        problemDescText = convertView.findViewById(R.id.problemDescription);
        offersNumberText = convertView.findViewById(R.id.currentOffers);
        minPriceText = convertView.findViewById(R.id.minPrice);
        removeButton = convertView.findViewById(R.id.removeButton);
        offersImage = convertView.findViewById(R.id.offersImage);
        cardImage = convertView.findViewById(R.id.cardImage);

        numText.setText("#" + number + ". Активная заявка");
        cashboxText.setText(cashboxName + ", ");
        storeText.setText(storeName);
        problemDescText.setText(problemDesc);
        if (offersNumber == null)
        {
            offersImage.setImageResource(R.drawable.ic_waiting);
            offersNumberText.setText("Ожидание предложений");
            minPriceText.setVisibility(View.INVISIBLE);
            minPriceText.setText("");
            cardImage.setVisibility(View.INVISIBLE);
        }
        else
        {
            offersNumberText.setText(offersNumber);
            minPriceText.setText(minPrice);
        }
        setOnClick(removeButton, id, number, cashboxName, storeName, problem, problemDesc);

        return convertView;
    }
}
