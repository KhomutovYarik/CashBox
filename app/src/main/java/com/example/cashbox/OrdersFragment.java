package com.example.cashbox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cashbox.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class OrdersFragment extends Fragment {
    TextView ordersText, profileText;
    ImageView ordersIcon, profileIcon, addOrderButton;
    LinearLayout ordersSection, profileSection;
    View view;
    public View onCreateView (LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = layoutInflater.inflate(R.layout.activity_orders_fragment, container, false);
        return view;
    }
}
