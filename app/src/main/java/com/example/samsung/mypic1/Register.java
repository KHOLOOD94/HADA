package com.example.samsung.mypic1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText e_name;
    EditText e_email;
    EditText e_password;
    EditText e_confPassword;
    String name,email,password,confPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        e_name = (EditText) findViewById(R.id.Username);
        e_email = (EditText) findViewById(R.id.email);
        e_password = (EditText) findViewById(R.id.password);
        e_confPassword =(EditText) findViewById(R.id.confirmPassword);

    }

    public void createAccountBut(View view){

        name = e_name.getText().toString();
        email = e_email.getText().toString();
        password = e_password.getText().toString();
        confPassword = e_confPassword.getText().toString();

        if (!emailValidator(email.trim())) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
        }else if(!passwordValidator(password,confPassword)){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        } else {

            String method = "register".trim();
            BackgroundTask back = new BackgroundTask(this);
            back.execute(method,name,email,password,confPassword);
            finish();
        }
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean passwordValidator(String pass,String pass1){

        if(pass.trim().equals(pass1.trim())){
            return true;
        }else{
            return false;
        }

    }

}
