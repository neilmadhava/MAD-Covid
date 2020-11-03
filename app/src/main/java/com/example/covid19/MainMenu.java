package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        TextView india = (TextView)findViewById(R.id.india);
        india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indiaActivity = new Intent(MainMenu.this, IndiaStats.class);
                startActivity(indiaActivity);
            }
        });

        TextView states = (TextView)findViewById(R.id.states);
        states.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statesActivity = new Intent(MainMenu.this, StatesStats.class);
                startActivity(statesActivity);
            }
        });

        TextView world = (TextView)findViewById(R.id.world);
        world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent worldActivity = new Intent(MainMenu.this, WorldStates.class);
                startActivity(worldActivity);
            }
        });
    }
}