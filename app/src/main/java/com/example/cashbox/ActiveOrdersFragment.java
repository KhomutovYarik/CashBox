package com.example.cashbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class ActiveOrdersFragment extends Fragment {

    View view;
    ImageView removeButton;
    ListView ordersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_orders, container, false);

        ArrayList<ActiveOrder> activeOrdersList = new ArrayList<ActiveOrder>();

        activeOrdersList.add(new ActiveOrder("#312124. Активная заявка", "Касса №1, ", "Партнер ККМ №1",
                "Сломалось", "3 предложения", "от 1500 р"));
        activeOrdersList.add(new ActiveOrder("#6213654. Активная заявка", "Касса №2, ", "Партнер ККМ №2",
                "Не работает", "1 предложение", "от 3000 р"));
        activeOrdersList.add(new ActiveOrder("#312124. Активная заявка", "Касса №1, ", "Партнер ККМ №1",
                "Сломалось", "3 предложения", "от 1500 р"));
        activeOrdersList.add(new ActiveOrder("#6213654. Активная заявка", "Касса №2, ", "Партнер ККМ №2",
                "Не работает", "1 предложение", "от 3000 р"));

        ordersList = view.findViewById(R.id.activeOrdersList);
        ActiveOrderAdapter adapter = new ActiveOrderAdapter(getActivity(), R.layout.orderslistelement, activeOrdersList);
        ordersList.setAdapter(adapter);


        return view;
    }

    private void preparing()
    {
        removeButton = view.findViewById(R.id.removeButton);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
