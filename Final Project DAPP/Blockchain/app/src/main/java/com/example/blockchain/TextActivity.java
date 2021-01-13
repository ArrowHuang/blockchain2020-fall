package com.example.blockchain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {
    TextView textView;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        textView = findViewById(R.id.textView6);
        scrollView = findViewById(R.id.scrollView2);

        Bundle bundle = getIntent().getExtras();
        String text_all = bundle.getString("all_text");
        textView.setText(text_all);
    }
}