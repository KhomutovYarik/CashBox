package com.example.cashbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StoresAdapter extends ArrayAdapter<Store> {
    Context context;
    int resource;

    public StoresAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Store> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    private void setOnClick(final ImageView btn, final String id){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stores").child(id).removeValue();
            }
        });
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String id = getItem(position).getId();
        String name = getItem(position).getName();
        String region = getItem(position).getRegion();
        String city = getItem(position).getCity();
        String address = getItem(position).getAddress();
        String comment = getItem(position).getComment();

        Store store = new Store(id, name, region, city, address, comment);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView storeName, storeAddress;
        final ImageView editButton, removeButton;

        storeName = convertView.findViewById(R.id.storeName);
        storeAddress = convertView.findViewById(R.id.storeAddress);
        editButton = convertView.findViewById(R.id.editButton);
        removeButton = convertView.findViewById(R.id.removeButton);

        storeName.setText(name);
        storeAddress.setText("г. " + city + ", " + address);
        setOnClick(removeButton, id);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}
