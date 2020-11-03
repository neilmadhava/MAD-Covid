package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class loginpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        Button login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = (EditText)findViewById(R.id.editTextTextPersonName);
                EditText passwd = (EditText)findViewById(R.id.editTextTextPassword);
                if (passwd.getText().toString().contentEquals("pass")) {
                    Intent main_menu = new Intent(loginpage.this, MainMenu.class);
                    startActivity(main_menu);
                }
            }
        });
    }
}