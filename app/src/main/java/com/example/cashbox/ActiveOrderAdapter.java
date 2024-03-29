package com.example.cashbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    private void setOnClick(final ImageView btn, final String ID, final String number, final String cashboxName, final String storeName, final String problem, final String problemDesc){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Отмена заявки")
                        .setMessage("Вы действительно хотите отменить заявку?")
                        .setCancelable(false)
                        .setPositiveButton("Да",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("orders").child(ID).setValue(new Order(ID, number, cashboxName, storeName, problem, problemDesc, "2"));
                                        String name = "", email = "";
                                        if (User.name != null)
                                            name = "\n\nИмя пользователя: " + User.name;
                                        if (User.email != null)
                                            email = "\n\nE-mail пользователя: " + User.email;
                                        JavaMailAPI sendMessage = new JavaMailAPI(context, "sos@cttp.ru", "Отмена заявки", "Заявка была отменена.\n\nНомер заявки: " + number + "\n\nТелефон пользователя: " + User.phone + name + email, false);
                                        sendMessage.execute();
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
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
        if (problem.length() < 30)
            problemDescText.setText(problem);
        else
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
