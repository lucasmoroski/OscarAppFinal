package com.example.oscarapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oscarapp.R;

public class ValidavotoActivity extends AppCompatActivity {

    TextView Filme,Diretor;
    Button volta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validavoto);

        volta = (Button) findViewById(R.id.btnConfirmar);

        volta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ValidavotoActivity.this, BoasVindasActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


}