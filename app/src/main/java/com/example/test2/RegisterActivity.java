package com.example.test2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        TextView btn = findViewById(R.id.login);
        Button btnRegister = findViewById(R.id.button3);
        EditText EDFname = findViewById(R.id.editTextTextPersonName);
        EditText EDLname = findViewById(R.id.editTextTextPersonName2);
        EditText EDemail = findViewById(R.id.editTextTextEmailAddress2);
        EditText EDpass = findViewById(R.id.editTextTextPassword2);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname,lname,email,password;

                fname = String.valueOf(EDFname.getText());
                lname = String.valueOf(EDLname.getText());
                email = String.valueOf(EDemail.getText());
                password = String.valueOf(EDpass.getText());
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseFirestore database = FirebaseFirestore.getInstance();

                Map<String, Object> info = new HashMap<>();
                info.put("firstName", fname);
                info.put("lastName", lname);


                database.collection("loginName").document(email)
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



                CollectionReference usersRef = database.collection("loginName");
                //DatabaseReference usersRef1 = database.getReference("FirstName");


                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Account created!",
                                            Toast.LENGTH_SHORT).show();
                                           startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });




    }
}