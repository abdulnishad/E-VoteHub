package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class thankyou extends AppCompatActivity {
    Button exitvote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        exitvote=findViewById(R.id.exitvote);
        exitvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(thankyou.this, User_menu.class);
                startActivity(intent);
            }
        });
    }
}