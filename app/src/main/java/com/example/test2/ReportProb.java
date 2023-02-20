package com.example.test2;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;



import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.google.firebase.firestore.*;
import com.google.firebase.storage.*;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;



public class ReportProb extends AppCompatActivity {
    static int image1 = 0;
    static int image2 = 1;
    static int image3 = 2;
static Bitmap bitmap;
    final Calendar calendar = Calendar.getInstance();
    EditText editText, wsText, descText;
    ImageView imageView1, imageView2, imageView3;
    Button tButton, sButton1, sButton2, sButton3;
    Boolean emergencySelect = false;
    String timeInfo, locationInfo, descriptionInfo, reportUrgencyLevel, reportCategoryType, inputString, downloadUrl, image1URL, image2URL, image3URL;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    DatabaseReference database2 = FirebaseDatabase.getInstance().getReference();


    private void setTextTime() { //for calendar view
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(dateFormat.format(calendar.getTime()));
    }

    //last good here!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_prob);

        //sets the TextView to correspond to the clicked category from prob_category.
        String reportCategoryType = getIntent().getStringExtra("button_text");
        if (reportCategoryType == null) {
            Log.d("SecondActivity", "buttonText is null");
        } else {
            Log.d("SecondActivity", "buttonText: " + reportCategoryType);
        }

        TextView textViewSet = findViewById(R.id.textView25);
        textViewSet.setText(reportCategoryType);

        ImageView imageview4 = findViewById(R.id.imageViewxd);
        imageview4.setElevation(-2f);
        //sets calendar format
        editText = (EditText) findViewById(R.id.Calendar);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                setTextTime();
            }
        };

        //onclick listener for calendar
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ReportProb.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                reportUrgencyLevel = "Standard";
                emergencySelect = true;
            }
        });

        sButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sButton1.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton2.setBackgroundColor(Color.parseColor("#800000"));
                sButton3.setBackgroundColor(Color.parseColor("#e9ce9f"));
                reportUrgencyLevel = "Urgent";
                emergencySelect = true;
            }
        });

        sButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sButton1.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton2.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton3.setBackgroundColor(Color.parseColor("#800000"));
                reportUrgencyLevel = "Emergency";
                emergencySelect = true;
            }
        });

        tButton = (Button) findViewById(R.id.button18);
        tButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String email = InputStringHolder.getInstance().getInputString();

                wsText = (EditText) findViewById(R.id.editTextReportLocation);
                descText = (EditText) findViewById(R.id.editTextTextMultiLine);

                timeInfo = String.valueOf(editText.getText());
                locationInfo = String.valueOf(wsText.getText());
                descriptionInfo = String.valueOf(descText.getText());

                if (TextUtils.isEmpty(timeInfo)) {
                    Toast.makeText(ReportProb.this, "Date cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(locationInfo)) {
                    Toast.makeText(ReportProb.this, "Location cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(descriptionInfo)) {
                    Toast.makeText(ReportProb.this, "Please set a description!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Boolean.valueOf(emergencySelect == false)) {
                    Toast.makeText(ReportProb.this, "Please select an urgent level!", Toast.LENGTH_SHORT).show();
                    return;
                }


                Map<String, Object> info = new HashMap<>();

                info.put("Location", locationInfo);
                info.put("Time Reported", timeInfo);
                info.put("Description", descriptionInfo);
                info.put("Emergency Type", reportUrgencyLevel);
                info.put("Location", locationInfo);
                info.put("Report Category Type", reportCategoryType);
                info.put("Image 1 URL", image1URL);
                info.put("Image 2 URL", image2URL);
                info.put("Image 3 URL", image3URL);

                Toast.makeText(getBaseContext(), "Submitted!", Toast.LENGTH_SHORT).show();
                wsText.setText("");
                descText.setText("");
                editText.setText("");
                imageView1.setImageResource(R.drawable.insertpic);
                imageView2.setImageResource(R.drawable.insertpic);
                imageView3.setImageResource(R.drawable.insertpic);
                sButton1.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton2.setBackgroundColor(Color.parseColor("#e9ce9f"));
                sButton3.setBackgroundColor(Color.parseColor("#e9ce9f"));


                database.collection("testReceive").document(email)
                        .set(info)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

                database2.child(email).setValue(info, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if (error == null) {
                            Log.d(TAG, "Data saved successfully.");
                        } else {
                            Log.w(TAG, "Failed to save data.", error.toException());
                        }
                    }
                });

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + uri.getLastPathSegment());
            storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override

                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    // Get the download URL of the uploaded image
                    Task<Uri> downloadUrlTask = taskSnapshot.getStorage().getDownloadUrl();
                    downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            Log.d(TAG, "onSuccess: downloadUrl: " + downloadUrl);

                                if (requestCode == image1) {
                                    imageView1.setImageBitmap(bitmap);
                                    image1URL = downloadUrl;
                                    Log.d(TAG, "image1url: " + image1URL);
                                } else if (requestCode == image2) {
                                    imageView2.setImageBitmap(bitmap);
                                    image2URL = downloadUrl;
                                    Log.d(TAG, "image2url: " + image2URL);


                                } else if (requestCode == image3) {
                                    imageView3.setImageBitmap(bitmap);
                                    image3URL = downloadUrl;
                                    Log.d(TAG, "image3url: " + image3URL);
                                }

                        }
                    });
                    // Set the bitmap to the corresponding ImageView

                }
            });
        }
    }
}

