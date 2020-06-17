
package com.example.maervit;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maervit.json.Fridge;
import com.example.maervit.json.FridgeItem;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends AppCompatActivity {

    public ProgressBar pb;
    private ZXingScannerView scannerView;
    public Fridge fridge;
    public int co2footprint = 0;
    TextView textView;
    public int tester = 2;
    public int number = 0;
    public boolean shouldStart = true;
    Handler handler = new Handler();
    public int progressNumber;


    //private ObjectAnimator progressAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fridge = new Fridge(this);

        FridgeItem[] items = fridge.GetItemsInFridge();
        long _temp_footprint = 0;
        for (int i = 0; i < items.length; i++) {
            _temp_footprint += items[i].CO2Footprint;
            System.out.println(items[i].CO2Footprint);
        }
        co2footprint = map(0,5,0, 15000, (_temp_footprint < 0 ? 0 : (_temp_footprint > 15000 ? 15000: _temp_footprint)));


        super.onCreate(savedInstanceState);
        if (tester == 1){
            setContentView(R.layout.activity_main);
             number = 1;
        }
        else if (tester ==2){
            setContentView(R.layout.activity_main_2);
             number = 3;
        }
        else if (tester ==3){
            setContentView(R.layout.activity_main_3);
             number = 5;
        }


        textView = findViewById(R.id.number);
        pb = findViewById(R.id.progress_horizontal);
        pb.setMax(5);
        textView.setText(co2footprint + "/5");
        pb.setProgress(co2footprint);





//        handler.postDelayed(run,5000);
//        change();
        //textView.setText("" + number);




        //init();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.action_bar, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );


    }


    // private void  init (){
    //  progressAnimator = findViewById(R.id.)
    //  progressAnimator = ObjectAnimator.ofInt(progressAnimator, "progress", 0,100);
    // }


    public void scanCode(View v) {
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultHandler());

        setContentView(scannerView);
        scannerView.startCamera();

    }

    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler {

        @Override
        public void handleResult(com.google.zxing.Result result) {
            String resultCodeString = result.getText();
            long resultCode = Long.parseLong(resultCodeString);
            long liner = 884851041913L;
            if (resultCode == liner) {
                Toast.makeText(MainActivity.this, "prave si naskenoval liner", Toast.LENGTH_LONG).show();
                openMainActivity2();
            } else {
                Toast.makeText(MainActivity.this, "neurcene", Toast.LENGTH_SHORT).show();
            }

            setContentView(R.layout.activity_main);
            scannerView.stopCamera();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.about:
                //Toast.makeText(this, "About us", Toast.LENGTH_LONG).show();
                openOnas();
                return true;
            case R.id.faq:
                //Toast.makeText(this, "Faq", Toast.LENGTH_LONG).show();
                openFAQ();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openMainActivity2() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void change() {


   /*     for (int i = 0; i < 6; i++) {
            //handler.postDelayed(run,10000);
            pb.setProgress(i);
            textView.setText(i + " %");


        }*/
    }

    public void openOnas() {
        Intent intent = new Intent(this, Onas.class);
        startActivity(intent);

        //finish();
    }

    public void openFAQ() {
        Intent intent = new Intent(this, Faq.class);
        startActivity(intent);
        //finishActivity(1);
    }

    public void otvorChladnicku(View view) {
        Intent i = new Intent(this, Chladnicka.class);
        startActivity(i);
        change();
    }

    /*Runnable run = new Runnable() {
        @Override
        public void run() {

            if(shouldStart = true) {

            }
        }
    };*/


    private int map(int newmin, int newmax, long oldmin, long oldmax, long a){
        System.out.println(a);
        return (int)((a - oldmin) * ((long)newmax - (long)newmin) / (oldmax - oldmin) + (long)newmin);
    }

}





