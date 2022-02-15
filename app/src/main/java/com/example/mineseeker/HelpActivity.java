package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
/*
    Describe the game
 */

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView tvHelp = findViewById(R.id.text_help);
        tvHelp.setText(R.string.help);
    }
}
