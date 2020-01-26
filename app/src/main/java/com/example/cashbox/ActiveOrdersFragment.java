package com.example.cashbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ActiveOrdersFragment extends Fragment {

    View view;
    public static ArrayList<ActiveOrder> activeOrdersList;
    public static ActiveOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_orders, container, false);

        activeOrdersList = new ArrayList<ActiveOrder>();

        activeOrdersList.add(new ActiveOrder("#312124. Активная заявка", "Касса №1, ", "Партнер ККМ №1",
                "Сломалось", "3 предложения", "от 1500 р"));
        activeOrdersList.add(new ActiveOrder("#6213654. Активная заявка", "Касса №2, ", "Партнер ККМ №2",
                "Не работает", "1 предложение", "от 3000 р"));
        activeOrdersList.add(new ActiveOrder("#312124. Активная заявка", "Касса №1, ", "Партнер ККМ №1",
                "Сломалось", "3 предложения", "от 1500 р"));
        activeOrdersList.add(new ActiveOrder("#6213654. Активная заявка", "Касса №2, ", "Партнер ККМ №2",
                "Не работает", "1 предложение", "от 3000 р"));

        ListView ordersList = view.findViewById(R.id.activeOrdersList);

        adapter = new ActiveOrderAdapter(getActivity(), R.layout.orderslistelement, activeOrdersList);
        ordersList.setAdapter(adapter);

        return view;
    }
}
