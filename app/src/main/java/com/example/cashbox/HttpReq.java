package com.example.cashbox;

import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HttpReq
{
    public static void getHtml(final String url)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                String title = null;
                try {
                    doc = (Document) Jsoup.connect(url).get();
                    title = doc.title();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}