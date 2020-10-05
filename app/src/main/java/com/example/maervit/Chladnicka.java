package com.example.maervit;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maervit.json.Fridge;
import com.example.maervit.json.FridgeItem;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Chladnicka extends AppCompatActivity {

    public int a = 0;
    public int b = 0;

    TextView textView1, textView2, c02_first, c02_second;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Fridge fridge = new Fridge(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chladnicka);


        textView1 = findViewById(R.id.textView4);
        c02_first = findViewById(R.id.C02_first);


        textView2 = findViewById(R.id.textView5);
        c02_second = findViewById(R.id.C02_second);

        FridgeItem[] items = fridge.GetItemsInFridge();

        for (int i = 0; i < items.length - 1; i++) {

            //String productName = items[i].productName;


            //String b = String.valueOf(items[i].CO2Footprint);


            textView1.setText(items[i].productName);
            c02_first.setText(String.valueOf(items[i].CO2Footprint));


        }

        for (int i = 0; i < items.length; i++) {

            //String a =items[i].productName;

            textView2.setText(items[i].productName);

            c02_second.setText(String.valueOf(items[i].CO2Footprint));

        }
    }
}


