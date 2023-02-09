package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProbCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prob_category);

        Button btnStructure = findViewById(R.id.button);
        Button btnRoofing = findViewById(R.id.button17);
        Button btnBuildExterior = findViewById(R.id.button9);
        Button btnBuildInterior = findViewById(R.id.button13);
        Button btnPlumbing = findViewById(R.id.button11);
        Button btnElectrical = findViewById(R.id.button14);
        Button btnFurniture = findViewById(R.id.button10);
        Button btnOthers = findViewById(R.id.button16);
        btnStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btnStructure.getText().toString();
                Intent intent = new Intent(ProbCategory.this, ReportProb.class);
                intent.putExtra("button_text", buttonText);
                startActivity(intent);
            }
        });
        btnRoofing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btnRoofing.getText().toString();
                Intent intent = new Intent(ProbCategory.this, ReportProb.class);
                intent.putExtra("button_text", buttonText);
                startActivity(intent);
            }
        });
        btnBuildExterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btnBuildExterior.getText().toString();
                Intent intent = new Intent(ProbCategory.this, ReportProb.class);
                intent.putExtra("button_text", buttonText);
                startActivity(intent);
            }
        });
        btnBuildInterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btnBuildInterior.getText().toString();
                Intent intent = new Intent(ProbCategory.this, ReportProb.class);
                intent.putExtra("button_text", buttonText);
                startActivity(intent);
            }
        });
        btnPlumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btnPlumbing.getText().toString();
                Intent intent = new Intent(ProbCategory.this, ReportProb.class);
                intent.putExtra("button_text", buttonText);
                startActivity(intent);
            }
        });
        btnElectrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btnElectrical.getText().toString();
                Intent intent = new Intent(ProbCategory.this, ReportProb.class);
                intent.putExtra("button_text", buttonText);
                startActivity(intent);
            }
        });
        btnFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btnFurniture.getText().toString();
                Intent intent = new Intent(ProbCategory.this, ReportProb.class);
                intent.putExtra("button_text", buttonText);
                startActivity(intent);
            }
        });
        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btnOthers.getText().toString();
                Intent intent = new Intent(ProbCategory.this, ReportProb.class);
                intent.putExtra("button_text", buttonText);
                startActivity(intent);
            }
        });


    }
}