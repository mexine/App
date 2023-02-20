package com.example.test2;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ActivityC extends AppCompatActivity { //Activity that displays report details

    private LinearLayout fieldsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        fieldsContainer = findViewById(R.id.fields_container);

        // Get the button name from the extras
        String buttonName = getIntent().getStringExtra("button_name");

        // Access the "testReceive" collection
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("testReceive");

        // Query the document with the same name as the button
        collectionRef.document(buttonName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Iterate over the fields in the document and create TextViews to display them
                        for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                            String fieldName = entry.getKey();
                            Object fieldValue = entry.getValue();

                            TextView fieldTextView = new TextView(ActivityC.this);
                            String fieldText = fieldName + ": ";

                            if (fieldValue != null) {
                                fieldText += fieldValue.toString();
                            } else {
                                fieldText += "N/A";
                            }

                            fieldTextView.setText(fieldText);
                            fieldsContainer.addView(fieldTextView);
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}