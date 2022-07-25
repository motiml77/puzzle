package com.example.puzzle_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);

        Button button1 =findViewById(R.id.button1);
        Button button2 =findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                Intent intent = new Intent();
                intent.setClass(FailActivity.this, Activity2.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent1 = new Intent();
                intent1.setClass(FailActivity.this, MainActivity.class);
                startActivity(intent1);

                break;
        }
    }
}