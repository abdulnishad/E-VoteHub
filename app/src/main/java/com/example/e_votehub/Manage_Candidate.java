package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Manage_Candidate extends AppCompatActivity {
    Button btnaddcandiate,btnviewcandidate;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_candidate);
        btnaddcandiate=findViewById(R.id.btnaddcandidate);
        btnviewcandidate=findViewById(R.id.btnviewcandidate);

        btnaddcandiate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manage_Candidate.this,Add_candidate.class);
                startActivity(intent);
            }
        });
        btnviewcandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manage_Candidate.this, CandidateList.class);

                startActivity(intent);
            }
        });

    }
}