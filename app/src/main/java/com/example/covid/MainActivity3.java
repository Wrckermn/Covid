package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tv1 = (TextView) findViewById(R.id.textView4);
        tv2 = (TextView) findViewById(R.id.textView5);
        tv3 = (TextView) findViewById(R.id.textView6);
        tv4 = (TextView) findViewById(R.id.textView7);

        String jumka = getIntent().getStringExtra("jumkaa");
        String jumse = getIntent().getStringExtra("jumsee");
        String jumme = getIntent().getStringExtra("jummee");
        String jumdi = getIntent().getStringExtra("jumdii");

        tv1.setText(jumka+" orang");
        tv2.setText(jumse+" orang");
        tv3.setText(jumme+" orang");
        tv4.setText(jumdi+" orang");
    }
}