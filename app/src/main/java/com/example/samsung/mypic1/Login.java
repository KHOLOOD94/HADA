package com.example.samsung.mypic1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText UsernameEt, PasswordEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        UsernameEt = (EditText)findViewById(R.id.Username);
        PasswordEt = (EditText)findViewById(R.id.password);
    }

    public void login(View view){
    String username = UsernameEt.getText().toString();
    String password = PasswordEt.getText().toString();
    String method = "login";
    BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,username,password);
}
}
