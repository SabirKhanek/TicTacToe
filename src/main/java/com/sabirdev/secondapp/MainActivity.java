package com.sabirdev.secondapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 1;
    int[] cellsState = {3,3,3,3,3,3,3,3,3};
    int[][] winConditions = {{0,1,2}, {3,4,5}, {6,7,8},
                             {0,3,6}, {1,4,7}, {2,5,8},
                             {0,4,8}, {2,4,6}};
    ImageView[] views = new ImageView[9];
    int[] wonCells = new int[3];
    int winner = 0;
    boolean griddeactive = false;


    public void drawTurn(View view) {
        ImageView img = (ImageView) view;
        int tag = Integer.parseInt(img.getTag().toString());
        views[tag] = img;
        if(griddeactive) {
            resetBoard();
        }
        if (cellsState[tag] == 3) {
            img.setTranslationY(-1000f);
            cellsState[tag] = activePlayer;
            if(activePlayer == 1) {
                img.setImageResource(R.drawable.x);
                activePlayer = 2;
                TextView status = findViewById(R.id.status);
                status.setTextColor(Color.parseColor("#0000FF"));
                status.setText("O's turn - tap the cell to play!");

            }
            else {
                activePlayer = 1;
                img.setImageResource(R.drawable.o);
                TextView status = findViewById(R.id.status);
                status.setTextColor(Color.parseColor("#FF0000"));
                status.setText("X's turn - tap the cell to play!");
            }
            img.animate().translationYBy(1000f).setDuration(250);
        }
        else {
            Toast.makeText(getApplicationContext(), "Cell is already marked!", Toast.LENGTH_SHORT).show();
        }
        if(checkConditions()) {
            TextView status = findViewById(R.id.status);
            status.setTextColor(Color.parseColor("#00FF00"));
            if(winner == 1) {
                Toast.makeText(getApplicationContext(), "X's winner \uD83D\uDE0D \uD83D\uDC4F", Toast.LENGTH_SHORT).show();
                status.setText("X's winner! Tap to reset!");
                griddeactive = true;
            }
            else {
                Toast.makeText(getApplicationContext(), "O's winner \uD83D\uDE0D \uD83D\uDC4F", Toast.LENGTH_SHORT).show();
                status.setText("O's winner! Tap to reset!");
                griddeactive = true;
            }
            return;
        }

        if(isDraw()) {
            TextView status = findViewById(R.id.status);
            status.setTextColor(Color.parseColor("#00FF00"));
            Toast.makeText(getApplicationContext(), "It's a tie! \uD83D\uDC4F", Toast.LENGTH_SHORT).show();
            status.setText("It's a tie! - Tap to reset!");
            griddeactive = true;
        }
    }

    public boolean isDraw() {
        for (int state :
                cellsState) {
            if (state == 3) {
                return false;
            }
        }
        return true;
    }

    public boolean checkConditions() {
        for (int i = 0; i < 8; i++) {
            if(cellsState[winConditions[i][0]] == cellsState[winConditions[i][1]] &&
                    cellsState[winConditions[i][1]] == cellsState[winConditions[i][2]] &&
                    cellsState[winConditions[i][0]] != 3 ) {
                if(cellsState[winConditions[i][0]] == 1) {
                    winner = 1;
                }
                else {
                    winner = 2;
                }

                for (int j = 0; j < 3; j++) {
                    wonCells[j] = winConditions[i][j];
                }

                for (int cell :
                        wonCells) {
                    if(winner == 1) {
                        views[cell].setImageResource(R.drawable.x_red);
                    }
                    else {
                        views[cell].setImageResource(R.drawable.o_red);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void resetBoard() {
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(android.R.color.transparent);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(android.R.color.transparent);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(android.R.color.transparent);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(android.R.color.transparent);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(android.R.color.transparent);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(android.R.color.transparent);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(android.R.color.transparent);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(android.R.color.transparent);
        ((ImageView) findViewById(R.id.imageView9)).setImageResource(android.R.color.transparent);

        for (int i = 0; i < 9; i++) {
            cellsState[i] = 3;
        }
        griddeactive = false;
        activePlayer = winner;
        winner = 0;
        if(activePlayer == 1) {
            TextView status = findViewById(R.id.status);
            status.setTextColor(Color.parseColor("#0000FF"));
            status.setText("O's turn - tap the cell to play!");
        }
        else {
            TextView status = findViewById(R.id.status);
            status.setTextColor(Color.parseColor("#FF0000"));
            status.setText("X's turn - tap the cell to play!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}