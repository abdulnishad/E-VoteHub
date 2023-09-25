package com.example.e_votehub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Admin_main extends AppCompatActivity {
    ImageButton btn1,btn2,btn3,btn4,btn5,btn6;
    Button btn7;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        btn1=findViewById(R.id.menu1);
        btn2=findViewById(R.id.menu2);
        btn3=findViewById(R.id.menu3);
        btn4=findViewById(R.id.menu4);
        btn5=findViewById(R.id.menu5);
        btn6=findViewById(R.id.menu6);
        btn7=findViewById(R.id.btn7);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin_main.this, Manage_Candidate.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin_main.this, View_Result.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin_main.this, ManageUsersActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin_main.this, Manage_Vote.class);
                startActivity(intent);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin_main.this, VoteListActivity.class);
                startActivity(intent);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin_main.this, admin_register.class);
                startActivity(intent);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Admin_main.this, "Logged out succefully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Admin_main.this,startpage.class);
                startActivity(intent);
            }
        });

    }
}