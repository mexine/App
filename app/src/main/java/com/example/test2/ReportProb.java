package com.example.test2;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.*;
import android.widget.*;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;



public class ReportProb extends AppCompatActivity {
    static int image1 = 0;
    static int image2 = 1;
    static int image3 = 2;

    final Calendar calendar = Calendar.getInstance();
    EditText editText;

    EditText wsText;

    EditText descText;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    Button tButton;
    Button sButton1;
    Button sButton2;
    Button sButton3;

    private void setTextTime(){ //for calendar view
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_prob);


        ImageView  imageview4 = findViewById(R.id.imageViewxd);
        imageview4.setElevation(-2f);
        //sets calendar format
        editText=(EditText) findViewById(R.id.Calendar);
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                setTextTime();
            }
        };

        //onclick listener for calendar
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReportProb.this,date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //imageview logic
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageViewxd);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, image1);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, image2);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, image3);
            }
        });

        //button logic
        sButton1 = findViewById(R.id.btnStandard);
        sButton2 = findViewById(R.id.btnUrgent);
        sButton3 = findViewById(R.id.btnEmergency);
        sButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sButton1.setBackgroundColor(Color.parseColor("#800000"));
                sButton2.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton3.setBackgroundColor(Color.parseColor("#e9ce9f"));
            }
        });

        sButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sButton1.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton2.setBackgroundColor(Color.parseColor("#800000"));
                sButton3.setBackgroundColor(Color.parseColor("#e9ce9f"));
            }
        });

        sButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sButton1.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton2.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton3.setBackgroundColor(Color.parseColor("#800000"));
            }
        });


        //submit button logic
        wsText = (EditText) findViewById(R.id.editTextReportLocation);
        descText = (EditText) findViewById(R.id.editTextTextMultiLine);


        tButton = (Button) findViewById(R.id.button18);
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Toast.makeText(getBaseContext(), "Submitted!" , Toast.LENGTH_SHORT ).show();
                wsText.setText("");
                descText.setText("");
                editText.setText("");
                imageView1.setImageResource(R.drawable.insertpic);
                imageView2.setImageResource(R.drawable.insertpic);
                imageView3.setImageResource(R.drawable.insertpic);
                sButton1.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton2.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton3.setBackgroundColor(Color.parseColor("#e9ce9f"));
            }
        });

        String buttonText = getIntent().getStringExtra("button_text");
        if (buttonText == null) {
            Log.d("SecondActivity", "buttonText is null");
        } else {
            Log.d("SecondActivity", "buttonText: " + buttonText);
        }

        TextView textViewSet = findViewById(R.id.textView25);
        textViewSet.setText(buttonText);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if (requestCode == image1) {
                    imageView1.setImageBitmap(bitmap);
                } else if (requestCode == image2) {
                    imageView2.setImageBitmap(bitmap);
                } else if (requestCode == image3) {
                    imageView3.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

