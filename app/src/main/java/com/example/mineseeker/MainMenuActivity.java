package com.example.mineseeker;
/*
    Main menu where the user
    click on play game, options and help.
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    private TextView mainMenu;
    private Button playGame;
    private Button options;
    private Button help;
    private ImageView someImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setupPlayButton();
        setupOptionButton();
        setupHelpButton();

    }

    private void setupHelpButton() {
        help = (Button) findViewById(R.id.button_help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(MainMenuActivity.this, HelpActivity.class);
                startActivity(helpIntent);
            }
        });
    }

    private void setupPlayButton() {
        playGame = (Button) findViewById(R.id.button_play_game);
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(MainMenuActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });
    }

    private void setupOptionButton() {
        options = (Button) findViewById(R.id.button_options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent optionIntent = OptionActivity.makeIntent(MainMenuActivity.this);
                startActivity(optionIntent);
            }
        });
    }
}