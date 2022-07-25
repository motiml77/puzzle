package com.example.puzzle_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class Activity3<mGridView> extends AppCompatActivity {
    private static GestureDetectGridView mGridView;

    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private static int mColumnWidth, mColumnHeight;

    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";

    private static String[] tileList;

    private static Context StaticActive;


    @SuppressLint("SetTextI18n")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);

        Intent intent=getIntent();
        String text = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        int number=intent.getIntExtra(MainActivity.EXTRA_NUMBER,0);

        init(number,text);
        scramble();
        setDimensions(number, text);

        Thread timer;
        timer = new Thread() {
            public void run() {
                try {
                    sleep(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent();
                    intent.setClass(Activity3.this, FailActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }



    private void setDimensions(Integer age, String name) {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext(), age,name);
            }
        });
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;

    }

    private static void display(Context context, Integer age,String name) {
        ArrayList<Button>buttons=new ArrayList<>();
        Button button;

        if (age<5){
            for (int i = 0; i < tileList.length; i++) {
                button = new Button(context);

                if (tileList[i].equals("0"))
                    button.setBackgroundResource(R.drawable.king_1);
                else if (tileList[i].equals("1"))
                    button.setBackgroundResource(R.drawable.king_2);
                else if (tileList[i].equals("2"))
                    button.setBackgroundResource(R.drawable.king_3);
                else if (tileList[i].equals("3"))
                    button.setBackgroundResource(R.drawable.king_4);
                else if (tileList[i].equals("4"))
                    button.setBackgroundResource(R.drawable.king_5);
                else if (tileList[i].equals("5"))
                    button.setBackgroundResource(R.drawable.king_6);
                else if (tileList[i].equals("6"))
                    button.setBackgroundResource(R.drawable.king_7);
                else if (tileList[i].equals("7"))
                    button.setBackgroundResource(R.drawable.king_8);
                else if (tileList[i].equals("8"))
                    button.setBackgroundResource(R.drawable.king_9);

                buttons.add(button);
            }

        }

        else {
            for (int i = 0; i < tileList.length; i++) {
                button = new Button(context);

                if (tileList[i].equals("0"))
                    button.setBackgroundResource(R.drawable.parrot_1);
                else if (tileList[i].equals("1"))
                    button.setBackgroundResource(R.drawable.parrot_2);
                else if (tileList[i].equals("2"))
                    button.setBackgroundResource(R.drawable.parrot_3);
                else if (tileList[i].equals("3"))
                    button.setBackgroundResource(R.drawable.parrot_4);
                else if (tileList[i].equals("4"))
                    button.setBackgroundResource(R.drawable.parrot_5);
                else if (tileList[i].equals("5"))
                    button.setBackgroundResource(R.drawable.parrot_6);
                else if (tileList[i].equals("6"))
                    button.setBackgroundResource(R.drawable.parrot_7);
                else if (tileList[i].equals("7"))
                    button.setBackgroundResource(R.drawable.parrot_8);
                else if (tileList[i].equals("8"))
                    button.setBackgroundResource(R.drawable.parrot_9);

                buttons.add(button);
            }


        }


        mGridView.setAdapter(new CustomAdapter(buttons,mColumnWidth,mColumnHeight));


    }

    private static void swap(Context context, int currentPosition, int swap, Integer age, String name) {
        String newPosition = tileList[currentPosition + swap];
        tileList[currentPosition + swap] = tileList[currentPosition];
        tileList[currentPosition] = newPosition;
        display(context, age, name);

        if (isSolved()) {
            Toast.makeText(context, name + ", YOU WIN!", Toast.LENGTH_SHORT).show();
            mGridView.setNumColumns(COLUMNS);
            mGridView.setAge(age);
            mGridView.setName(name);
            tileList=new String[DIMENSIONS];


        }
    }

    public static void moveTiles(Context context, String direction, int position, Integer age, String name) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1,age,name);
            else if (direction.equals(down)) swap(context, position, COLUMNS,age,name);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1, age,name);
            else if (direction.equals(down)) swap(context, position, COLUMNS,age,name);
            else if (direction.equals(right)) swap(context, position, 1,age,name);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1,age,name);
            else if (direction.equals(down)) swap(context, position, COLUMNS,age,name);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS,age,name);
            else if (direction.equals(right)) swap(context, position, 1,age,name);
            else if (direction.equals(down)) swap(context, position, COLUMNS,age,name);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS,age,name);
            else if (direction.equals(left)) swap(context, position, -1,age,name);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS,age,name);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS,age,name);
            else if (direction.equals(right)) swap(context, position, 1,age,name);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS,age,name);
            else if (direction.equals(left)) swap(context, position, -1,age,name);
            else if (direction.equals(right)) swap(context, position, 1,age,name);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS,age,name);
            else if (direction.equals(left)) swap(context, position, -1,age,name);
            else if (direction.equals(right)) swap(context, position, 1,age,name);
            else swap(context, position, COLUMNS,age,name);
        }
    }

    private static void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private void init(Integer age, String name){
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);
        mGridView.setAge(age);
        mGridView.setName(name);

        tileList=new String[DIMENSIONS];
        for (int i=0; i<DIMENSIONS;i++)
            tileList[i]=String.valueOf(i);
    }
    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }


}