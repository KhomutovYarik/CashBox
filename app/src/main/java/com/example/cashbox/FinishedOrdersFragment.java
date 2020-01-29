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
    public static ArrayList<FinishedOrder> finishedOrdersList;
    public static FinishedOrderAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_finished_orders, container, false);

        ListView ordersList = view.findViewById(R.id.finishedOrdersList);
        ordersList.setAdapter(adapter);


        return view;
    }
}
