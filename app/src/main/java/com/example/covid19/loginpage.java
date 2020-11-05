package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginpage extends AppCompatActivity {
    Integer attempts = 0;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        final Button login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = (EditText)findViewById(R.id.editTextTextPersonName);
                EditText passwd = (EditText)findViewById(R.id.editTextTextPassword);
                if (passwd.getText().toString().contentEquals("pass") &&
                        (user.getText().toString().contentEquals("1760339" ) ||
                                user.getText().toString().contentEquals("1760326" ) ||
                                user.getText().toString().contentEquals("1760310" )
                                )) {
                    Intent main_menu = new Intent(loginpage.this, MainMenu.class);
                    startActivity(main_menu);
                }
                else {
                    attempts +=1;
                    String toastMsg = "Invalid username/password\nAttempts left: " + String.valueOf(3-attempts);
                    Toast toast = Toast.makeText(loginpage.this, toastMsg , Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 10);
                    toast.show();

                    Log.d(TAG, "Attempts: " + attempts);

                    if (attempts == 3){
                        login.setEnabled(false);
                    }
                }
            }
        });
    }
}