package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        EditText textEmail = findViewById(R.id.editTextTextEmailAddress);
        Button btnSendEmail = findViewById(R.id.buttonSubmit);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email;
                email = String.valueOf(textEmail.getText());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(ResetPassword.this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("EMAIL", "Email sent.");
                                    Toast.makeText(ResetPassword.this, "Email sent successfully!",
                                            Toast.LENGTH_SHORT).show();
                                              finish();
                                }
                                else {
                                    Toast.makeText(ResetPassword.this, "Email not found. Please try again!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}