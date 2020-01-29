package com.example.cashbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cashbox.Cashbox;
import com.example.cashbox.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CashboxAdapter extends ArrayAdapter<Cashbox> {
    Context context;
    int resource;

    public CashboxAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Cashbox> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    private void removeClick(final ImageView btn, final String id, final String parentId){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stores").child(parentId).child("cashboxes").child(id).removeValue();
            }
        });
    }

    private void editClick(final ImageView btn, final String id, final String name, final String model, final String serial)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editCB = new Intent(context, add_cashbox.class);
                editCB.putExtra("title", "Редактирование");
                editCB.putExtra("id", id);
                editCB.putExtra("name", name);
                editCB.putExtra("model", model);
                editCB.putExtra("serial", serial);
                ((Activity) context).startActivityForResult(editCB, 2);
            }
        });
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String id = getItem(position).getId();
        String parentId = getItem(position).getParentId();
        String name = getItem(position).getName();
        String model = getItem(position).getModel();
        String serialNumber = getItem(position).getSerialNumber();

        Cashbox store = new Cashbox(id, parentId, name, model, serialNumber);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView cbName, cbModel;
        final ImageView editButton, removeButton;

        cbName = convertView.findViewById(R.id.cbName);
        cbModel = convertView.findViewById(R.id.cbModel);
        editButton = convertView.findViewById(R.id.editButton);
        removeButton = convertView.findViewById(R.id.removeButton);

        cbName.setText(name);
        cbModel.setText(model);
        removeClick(removeButton, id, parentId);
        editClick(editButton, id, name, model, serialNumber);

        return convertView;
    }
}
