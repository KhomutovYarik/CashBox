package com.example.cashbox;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HttpReq
{
    public static void getHtml(final String url, final TextView textView, Context context, final Activity activity)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                final StringBuilder builder = new StringBuilder();
                try {
                    Document doc = (Document) Jsoup.connect(url).get();
                    Element adv = doc.getElementById("loader");
                    Elements models = doc.select("option");

                    builder.append(adv.toString());
//                    for (Element model : models)
//                    {
//                        builder.append("\n").append("model: ").append(models.text());
//                    }
                } catch (IOException e) {
                    builder.append("Error");
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
}