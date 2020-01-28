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

import com.example.cashbox.Cashbox;
import com.example.cashbox.R;

import java.util.ArrayList;

public class CashboxAdapter extends ArrayAdapter<Cashbox> {
    Context context;
    int resource;

    public CashboxAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Cashbox> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    private void setOnClick(final ImageView btn, final int i){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(getItem(i));
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String model = getItem(position).getModel();
        String serialNumber = getItem(position).getSerialNumber();

        Cashbox store = new Cashbox(name, model, serialNumber);

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
        setOnClick(removeButton, position);

        return convertView;
    }
}
