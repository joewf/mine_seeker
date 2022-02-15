package com.example.mineseeker;
/*
    User can pick the number
    of rows, columns and mines.
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OptionActivity extends AppCompatActivity {

    public static final String GET_NUM_MINES = "Num mines";
    public static final String GET_ROW = "row size";
    public static final String GET_COLUMN = "column size";

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        createRadioGroupRow();
        createRadioGroupColumn();
        createRadioGroupMine();

        /*int savedMine = getNumMines(this);
        Toast.makeText(this, "Saved mines: " + savedMine,
                Toast.LENGTH_SHORT).show();*/
        resetTimesPlayed();

    }

    // Radio row
    private void createRadioGroupRow() {
        RadioGroup boardSizeGroup = (RadioGroup) findViewById(R.id.radio_row);
        int [] array_row = getResources().getIntArray(R.array.num_rows);

        // Create radio buttons
        for(int i = 0; i < array_row.length; i++) {
            final int numRow = array_row[i];

            RadioButton rowButton = new RadioButton(this);
            rowButton.setText(numRow + " rows");

            // TODO: set on-clicks callbacks
            rowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(OptionActivity.this, "You clicked " +
                            numRow, Toast.LENGTH_SHORT).show();
                    saveRowSize(numRow);
                }
            });

            // Add to board radio group
            boardSizeGroup.addView(rowButton);

            // Select default board size button
            if (numRow == getNumRows(this)) {
                rowButton.setChecked(true);
            }
        }
    }

    private void saveRowSize(int row) {
        SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(GET_ROW, row);
        editor.apply();
    }

    static public int getNumRows(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
        // Default value is set to 0 (s: must be the value you wanna retrieve)
        return prefs.getInt(GET_ROW, 4);
    }


    // Radio Column
    private void createRadioGroupColumn() {
        RadioGroup boardSizeGroup = (RadioGroup) findViewById(R.id.radio_column);
        int [] arrayColumn = getResources().getIntArray(R.array.num_columns);

        // Create radio buttons
        for(int i = 0; i < arrayColumn.length; i++) {
            final int numColumn = arrayColumn[i];

            RadioButton columnButton = new RadioButton(this);
            columnButton.setText(numColumn + " columns");

            // TODO: set on-clicks callbacks
            columnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(OptionActivity.this, "You clicked " +
                            numColumn, Toast.LENGTH_SHORT).show();
                    saveColumnSize(numColumn);
                }
            });

            // Add to board radio group
            boardSizeGroup.addView(columnButton);

            // Select default board size button
            if (numColumn == getNumColumns(this)) {
                columnButton.setChecked(true);
            }
        }
    }

    private void saveColumnSize(int column) {
        SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(GET_COLUMN, column);
        editor.apply();
    }

    static public int getNumColumns(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
        // Default value is set to 0 (s: must be the value you wanna retrieve)
        return prefs.getInt(GET_COLUMN, 6);
    }


    // Radio mine
    private void createRadioGroupMine() {
        RadioGroup mineGroup = (RadioGroup) findViewById(R.id.radio_mines);
        int [] array_mines = getResources().getIntArray(R.array.num_mines);

        // Create radio buttons
        for(int j = 0; j < array_mines.length; j++) {
            final int numMines = array_mines[j];

            RadioButton mineButton = new RadioButton(this);
            mineButton.setText(numMines + " mines");

            // TODO: set on-clicks callbacks
            mineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(OptionActivity.this, "You clicked " +
                            numMines, Toast.LENGTH_SHORT).show();
                    saveNumMines(numMines);
                }
            });

            // Add to mines radio group
            mineGroup.addView(mineButton);

            // Select default mine button:
            if (numMines == getNumMines(this)) {
                mineButton.setChecked(true);
            }
        }
    }

    private void saveNumMines(int num_mines) {
        SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(GET_NUM_MINES, num_mines);
        editor.apply();
    }

    static public int getNumMines(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);
        // Default value is set to 0 (s: must be the value you wanna retrieve)
        return prefs.getInt(GET_NUM_MINES, 6);
    }

    public void resetTimesPlayed() {
        Button erase = (Button) findViewById(R.id.button_erase_time_played);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameActivity.erasePlay(OptionActivity.this);
                Toast.makeText(OptionActivity.this, "Data Erased!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*private void saveData(int number, String str) {
        SharedPreferences prefs = this.getSharedPreferences("OptionPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(str, number);
        editor.apply();
    }

    static public int getData(Context context, String str) {
        SharedPreferences prefs = context.getSharedPreferences("OptionPrefs", MODE_PRIVATE);
        // Default value is set to 0 (s: must be the value you wanna retrieve)
        return prefs.getInt(str, 0);
    }*/
}