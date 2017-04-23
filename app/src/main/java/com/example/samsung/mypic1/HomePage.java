package com.example.samsung.mypic1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
    }

    public void createAccount(View view){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
    public void login(View view){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}
