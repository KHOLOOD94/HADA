package com.example.samsung.mypic1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    EditText e_name,e_email,e_password,e_confPassword;
    String name,email,password,confPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        e_name = (EditText) findViewById(R.id.Username);
        e_email = (EditText) findViewById(R.id.email);
        e_password = (EditText) findViewById(R.id.password);
        e_confPassword =(EditText) findViewById(R.id.confirmPassword)

    }


}
