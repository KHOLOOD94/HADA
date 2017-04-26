package com.example.samsung.mypic1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddEscorts extends AppCompatActivity {

    EditText esc_name , esc_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addescorts);

        esc_name = (EditText)findViewById(R.id.editTextName);
        esc_phone = (EditText) findViewById(R.id.editTextPhone);
    }

    public void addEscorts(View view){

        String name = esc_name.getText().toString();
        String phone = esc_phone.getText().toString();



    }
}
