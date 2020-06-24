package com.example.maervit;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maervit.json.Fridge;
import com.example.maervit.json.FridgeItem;
import android.os.Bundle;
import android.widget.TextView;

public class Chladnicka extends AppCompatActivity {

    TextView textView1, textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Fridge fridge = new Fridge(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chladnicka);

        FridgeItem[] items = fridge.GetItemsInFridge();
        for (int i = 0; i < items.length; i++) {

            String a =items[i].productName;
            textView1 = findViewById(R.id.textView4);
            textView2 = findViewById(R.id.textView5);
            textView1.setText(a);






        }

    }
}


