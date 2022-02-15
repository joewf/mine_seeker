package com.example.mineseeker;
/*
    Game engine
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public static final String GET_TIMES_PLAYED = "update times played";
    public static final int EMPTY = 00;
    public static final int MINE = 10;
    public static final int FOUND = 2;
    private int NUM_ROWS = 0;
    private int NUM_COLS = 0;
    private static int NUM_MINES = 0;
    private int MINE_FOUND = 0;
    private int SCAN_USED = 0;
    public static int timesPlayed;
    private TextView tvFound;
    private TextView tvScanUsed;
    private TextView tvTimesPlayed;
    Button[][] buttons = new Button[NUM_ROWS][NUM_COLS];
    int[][] buttonMine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        updateTimesPlayed(timesPlayed);
        initiateBoard();
        setEmptyButtons();
        populateButtons();

    }

    private void putText() {
        tvFound = findViewById(R.id.text_found_mines);
        tvFound.setText("Found " + MINE_FOUND + " of " + NUM_MINES + " ships");

        tvScanUsed = findViewById(R.id.text_scan_used);
        tvScanUsed.setText("Scan Used: " + SCAN_USED);
    }

    private void initiateBoard() {
        NUM_ROWS = OptionActivity.getNumRows(this);
        NUM_COLS = OptionActivity.getNumColumns(this);
        NUM_MINES = OptionActivity.getNumMines(this);
        buttons = new Button[NUM_ROWS][NUM_COLS];
        buttonMine = new int[NUM_ROWS][NUM_COLS];
    }

    private void setEmptyButtons() {
        // Set empty spaces to 0
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++){
                buttonMine[i][j] = EMPTY;
            }
        }
        randomizedMines();
    }

    private void randomizedMines() {
        // Set random mines to 1
        Random random = new Random();
        int row, col;
        for (int i = 0; i < NUM_MINES; i++) {
            row = random.nextInt(NUM_ROWS - 1);
            col = random.nextInt(NUM_COLS - 1);

            // Loop until a different mine
            while ( buttonMine[row][col] != EMPTY ) {
                row = random.nextInt(NUM_ROWS - 1);
                col = random.nextInt(NUM_COLS - 1);
            }

                buttonMine[row][col] = MINE;

        }
    }

    // Keep count of times played
    private void updateTimesPlayed(int timesPlayed) {

        // Store the number of times played
        timesPlayed = getTimesPlayed(this);
        timesPlayed++;
        SharedPreferences prefs = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(GET_TIMES_PLAYED, timesPlayed);
        editor.apply();

        // Set number of times played
        tvTimesPlayed = (TextView) findViewById(R.id.text_times_played);
        tvTimesPlayed.setText("Times Played: " + timesPlayed);
    }

    static public int getTimesPlayed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return prefs.getInt(GET_TIMES_PLAYED, 0);
    }

    public static void erasePlay(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppsPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(GET_TIMES_PLAYED);
        editor.apply();
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButton);
        for (int row = 0; row < NUM_ROWS; row++) {
            TableRow tableRow = new TableRow(this); // for each row, create a row
            // scaling tableRow
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow); // add the table row to the table

            for (int col = 0; col < NUM_COLS; col++) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                // scaling buttons by stretching all the screen
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                // Make text not click on small buttons
                button.setPadding(0,0,0,0);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });

                tableRow.addView(button); // add button for each column in the row
                buttons[row][col] = button;
            }
        }
    }
    private void gridButtonClicked(int row, int col) {
        Button button = buttons[row][col];
        int count = 0;
        // Lock Button Sizes:
        lockButtonSizes();

        // Reveal number of mines when clicking on a ship
        if (buttonMine[row][col] == FOUND) {
            for (int i = 0; i < NUM_COLS; i++) {
                if (buttonMine[row][i] == MINE) {
                    count++;
                }
            }

            for (int j = 0; j < NUM_ROWS; j++) {
                if (buttonMine[j][col] == MINE) {
                    count++;
                }
            }
            button.setText("" + count);
            button.setClickable(false);
            SCAN_USED++;
            putText();
        }

        // Reveal non mine and set count
        if (buttonMine[row][col] == EMPTY) {
            for (int i = 0; i < NUM_COLS; i++) {
                if (buttonMine[row][i] == MINE && buttonMine[row][i] != FOUND) {
                    count++;
                }
            }

            for (int j = 0; j < NUM_ROWS; j++) {
                if (buttonMine[j][col] == MINE && buttonMine[j][col] != FOUND) {
                    count++;
                }
            }
            buttonMine[row][col] = count;
            button.setText("" + count);
            button.setClickable(false);
            SCAN_USED++;
            putText();
        }

        // Reveal mine and scale image to button
        if (buttonMine[row][col] == MINE) {
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ship);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));
            buttonMine[row][col] = FOUND;
            MINE_FOUND++;
            for (int i = 0; i < NUM_COLS; i++) {
                if (!buttons[row][i].isClickable() && buttonMine[row][i] != EMPTY) {
                        buttonMine[row][i]--;
                        buttons[row][i].setText("" + buttonMine[row][i]);
                }
            }
            for (int j = 0; j < NUM_ROWS; j++) {
                if (!buttons[j][col].isClickable() && buttonMine[j][col] != EMPTY) {
                        buttonMine[j][col]--;
                        buttons[j][col].setText("" + buttonMine[j][col]);
                }
            }
            putText();
            if (MINE_FOUND == NUM_MINES) {
                setupAlertMessage();
            }
            button.setClickable(true);
        }

    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void setupAlertMessage() {
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager, "Message Dialog");
        Log.i("TAG" , "Just showed the dialog");
    }
}