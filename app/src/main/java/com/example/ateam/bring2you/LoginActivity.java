package com.example.ateam.bring2you;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // TODO Ifall Man får fel om att API nyckeln är expired eller invalid,
    // TODO så måste api nyckeln bytas i Json filen, mot någon av  nycklarna som ligger här: https://console.developers.google.com/apis/credentials?project=bring2you-da7a0

    private static final String TAG = "LoginActivity";

    private  FirebaseAuth mAuth;
    private EditText mUsername , mPassword;
    private Button loginButton;
   private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.about_btn).setOnClickListener(view -> about());

        mAuth = FirebaseAuth.getInstance();

        mUsername = findViewById(R.id.usernameEditText);
        mPassword = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                if(mUsername.getText().toString().equals("") && mPassword.getText().toString().equals("")){
                    mUsername.setError("No blank fields");
                    mPassword.setError("No blank fields!");
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else if(mUsername.getText().toString().equals("")){
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mUsername.setError("No blank fields!");

                    //toastMessage("no blank fields!");
                }else if(mPassword.getText().toString().equals("")){
                    loginButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    mPassword.setError("No blank fields!");
                }
                else
                mAuth.signInWithEmailAndPassword(mUsername.getText().toString(),mPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        progressBar.setVisibility(View.INVISIBLE);
                        loginButton.setVisibility(View.VISIBLE);

                        if(task.isSuccessful()){
                            toastMessage("Successfully logged in as: " + user.getEmail());
                            startActivity(new Intent(LoginActivity.this,ListActivity.class));
                        } else{
                           // toastMessage(task.getException().getMessage());
                           toastMessage("Failure login in..");
                        }
                    }
                });

            }
        });

    }

    private void about() {
       toastMessage("Hejsan");
    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
