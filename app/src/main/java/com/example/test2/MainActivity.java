package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //button for register here
        TextView btn = findViewById(R.id.registerHere);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        //button for forgetpassword
        TextView btn2 = findViewById(R.id.forgotPass);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ForgotPassActivity.class));
            }
        });

        //button for login
        EditText EDemail = findViewById(R.id.editTextTextEmailAddress);
        EditText EDpass = findViewById(R.id.editTextTextPassword);
        Button btn3 = findViewById(R.id.button_main);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


//REMOVE COMMENT TO ALLOW LOGIN TO PROCEED WITHOUT CREDENTIALS

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(EDemail.getText());
                InputStringHolder.getInstance().setInputString(email);
                startActivity(new Intent(MainActivity.this,MainMenu.class));

            }
        });



        /*
        COMMENT ENTIRE BLOCK  IF YOU NEED TO DEBUG WITHOUT LOGGING IN!

        //START HERE
        //btn for login logic
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;

                email = String.valueOf(EDemail.getText());
                password = String.valueOf(EDpass.getText());
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Login Successful!",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "signInWithEmail:success");
                                    startActivity(new Intent(MainActivity.this,MainMenu.class));

                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Login failed. Re-enter credentials",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
              */  //END HERE
    }
/*
    public void homelayoutButton(View view){
        setContentView(R.layout.secondlayout);
    }

    public void secondlayoutButton(View view){
        setContentView(R.layout.activity_main);
    }*/



}