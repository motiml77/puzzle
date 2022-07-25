package com.example.puzzle_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT="com.example.application.example.EXTRA_TEXT";
    public static final String EXTRA_NUMBER="com.example.application.example.EXTRA_NUMBER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button =(Button) findViewById(R.id.btnEnter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openActivity2();
            }
        });

        }

        public void openActivity2(){
            EditText editText1 = (EditText) findViewById(R.id.edittext1);
            String text = editText1.getText().toString();
            EditText editText2 = (EditText) findViewById(R.id.edittext2);
            String number = (editText2.getText().toString());

            if (text.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "Please add inputs", Toast.LENGTH_LONG).show();
                return;
            }

            int num = new Integer(number);

            Intent intent = new Intent(this, Activity2.class);
            intent.putExtra(EXTRA_TEXT, text);
            intent.putExtra(EXTRA_NUMBER, num);
            startActivity(intent);
        }
    }
