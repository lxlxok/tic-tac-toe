package com.dealfaro.luca.androidcmps121;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int[][] crossMatrix = new int[3][3];
    private int[][] circleMatrix = new int[3][3];
    private int[][] viewIdMatrix = new int[3][3];
    private int num = 0;
    private boolean gameOver = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }
        viewIdMatrix[0][0] = R.id.imageButton00;
        viewIdMatrix[0][1] = R.id.imageButton01;
        viewIdMatrix[0][2] = R.id.imageButton02;
        viewIdMatrix[1][0] = R.id.imageButton10;
        viewIdMatrix[1][1] = R.id.imageButton11;
        viewIdMatrix[1][2] = R.id.imageButton12;
        viewIdMatrix[2][0] = R.id.imageButton20;
        viewIdMatrix[2][1] = R.id.imageButton21;
        viewIdMatrix[2][2] = R.id.imageButton22;
    }

    // start a new game restart activity
    public void newGame(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // configure the state of view when rotate the screen
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
            View v = this.findViewById(android.R.id.content);
            reload(v);

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
            View v = this.findViewById(android.R.id.content);
            reload(v);
        }
    }

    // reload the view state before rotated
    public void reload(View v) {
        for (int i = 0; i < circleMatrix.length; i++) {
            for(int j = 0; j < circleMatrix[0].length; j++) {
                if (circleMatrix[i][j] == 1) {
                    ImageButton vv = (ImageButton) findViewById(viewIdMatrix[i][j]);
                    vv.setImageResource(R.drawable.circle);
                }
            }
        }

        for (int i = 0; i < crossMatrix.length; i++) {
            for (int j = 0; j < crossMatrix[0].length; j++) {
                if (crossMatrix[i][j] == 1) {
                    ImageButton vv = (ImageButton) findViewById(viewIdMatrix[i][j]);
                    vv.setImageResource(R.drawable.cross);
                }
            }
        }

        checkResult(v);

    }

    // check the result of game
    public void checkResult(View v) {
        TextView c = (TextView) findViewById(R.id.wonView);
        if (checkMatrix(crossMatrix)) {
            highLight(R.drawable.crossred, crossMatrix);
            c.setText("X won!");
            c.setVisibility(View.VISIBLE);
            gameOver = true;
        } else if (checkMatrix(circleMatrix)) {
            highLight(R.drawable.circlered, circleMatrix);
            c.setText("O won!");
            c.setVisibility(View.VISIBLE);
            gameOver = true;
        } else if (num >= 9) {
            c.setText("Tie");
            c.setVisibility(View.VISIBLE);
            gameOver = true;
        }
    }

    // highlight the item that is same in a line
    public void highLight(int ResId, int[][] m) {

        for (int i = 0; i < m.length; i++) {
            int flag = 1;
            for (int j = 0; j < m[i].length; j++) {
                flag = flag & m[i][j];
            }
            if (flag == 1) {
                for (int j = 0; j < m[i].length; j++) {
                    ImageButton vv = (ImageButton) findViewById(viewIdMatrix[i][j]);
                    vv.setImageResource(ResId);
                }
                return;
            }
        }

        for (int j = 0; j < m.length; j++) {
            int flag = 1;
            for (int i = 0; i < m.length; i++) {
                flag = flag & m[i][j];
            }
            if (flag == 1) {
                for(int i = 0; i < m.length; i++) {
                    ImageButton vv = (ImageButton) findViewById(viewIdMatrix[i][j]);
                    vv.setImageResource(ResId);
                }
                return;
            }
        }

        int flag = 1;
        for (int i = 0; i < m.length; i++) {
            flag = flag & m[i][i];
        }
        if (flag == 1) {
            for (int i = 0; i < m.length; i++) {
                ImageButton vv = (ImageButton) findViewById(viewIdMatrix[i][i]);
                vv.setImageResource(ResId);
            }
            return;
        }

        flag = 1;
        for (int i = 0; i < m.length; i++) {
            flag = flag & m[i][m.length - i - 1];
        }
        if (flag == 1) {
            for (int i = 0; i < m.length; i++) {
                ImageButton vv = (ImageButton) findViewById(viewIdMatrix[i][m.length - i - 1]);
                vv.setImageResource(ResId);
            }
            return;
        }

    }

    // check if x or o  matrix have three item in a line
    public boolean checkMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            int flag = 1;
            for (int j = 0; j < m[i].length; j++) {
                flag = flag & m[i][j];
            }
            if (flag == 1) {
                return true;
            }
        }

        for (int j = 0; j < m.length; j++) {
            int flag = 1;
            for (int i = 0; i < m.length; i++) {
                flag = flag & m[i][j];
            }
            if (flag == 1) {
                return true;
            }
        }

        int flag = 1;
        for (int i = 0; i < m.length; i++) {
            flag = flag & m[i][i];
        }
        if (flag == 1) {
            return true;
        }

        flag = 1;
        for (int i = 0; i < m.length; i++) {
            flag = flag & m[i][m.length - i - 1];
        }
        if (flag == 1) {
            return true;
        }

        return false;
    }

    // count number of button that have been clicked
    public int countItem(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    sum++;
                }
            }
        }
        return sum;
    }

    // the function to activate when a button is clicked
    public void clickBoard(View v) {
        int circleNum = countItem(circleMatrix);
        int crossNum = countItem(crossMatrix);
        num = circleNum + crossNum;
        // Get id of button
        int i = v.getId();
        // More useful, let's get the tag.
        String t = (String) v.getTag();
        int x = Character.getNumericValue(t.charAt(0));
        int y = Character.getNumericValue(t.charAt(1));
        // no effect for already exit button
        if (crossMatrix[x][y] == 1 || circleMatrix[x][y] == 1 || num >= 9 || gameOver) {
            return;
        }
        // If you want to put a cross on it.
        ImageButton vv = (ImageButton) v;
        // decide which item to put
        if (crossNum <= circleNum) {
            vv.setImageResource(R.drawable.cross);
            crossMatrix[x][y] = 1;
        } else {
            vv.setImageResource(R.drawable.circle);
            circleMatrix[x][y] = 1;
        }
        circleNum = countItem(circleMatrix);
        crossNum = countItem(crossMatrix);
        num = circleNum + crossNum;
        checkResult(v);
    }

}
