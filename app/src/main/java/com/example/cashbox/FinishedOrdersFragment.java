package com.example.cashbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FinishedOrdersFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished_orders, container, false);

        ArrayList<FinishedOrder> finishedOrdersList = new ArrayList<FinishedOrder>();

        finishedOrdersList.add(new FinishedOrder("#212124. Завершённая заявка", "Касса №2, ", "Партнер ККМ №1",
                "Сломалось", 4, true));
        finishedOrdersList.add(new FinishedOrder("#4213654. Отменённая заявка", "Касса №3, ", "Партнер ККМ №2",
                "Не работает", 0, false));

        ListView ordersList = view.findViewById(R.id.finishedOrdersList);
        FinishedOrderAdapter adapter = new FinishedOrderAdapter(getActivity(), R.layout.finishorderslistelement, finishedOrdersList);
        ordersList.setAdapter(adapter);


        return view;
    }
}
