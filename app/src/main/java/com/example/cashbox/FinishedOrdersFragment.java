package com.example.cashbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FinishedOrdersFragment extends Fragment {

    View view;
    public static ArrayList<FinishedOrder> finishedOrdersList = new ArrayList<FinishedOrder>();
    public static FinishedOrderAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_finished_orders, container, false);

        finishedOrdersList.add(new FinishedOrder("#1. Завершённая заявка", "Касса №2, ", "Партнер ККМ №1",
                "Сломалось", 4, true));
        finishedOrdersList.add(new FinishedOrder("#424413654. Отменённая заявка", "Касса №3, ", "Партнер ККМ №2",
                "Не работает", 0, false));

        ListView ordersList = view.findViewById(R.id.finishedOrdersList);
        adapter = new FinishedOrderAdapter(getActivity(), R.layout.finishorderslistelement, finishedOrdersList);
        ordersList.setAdapter(adapter);


        return view;
    }
}
