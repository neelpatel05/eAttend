package com.eattend.miniproject.eattend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    EditText email,password;
    Button signin,signup,forgotpassword;
    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email_edit);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        forgotpassword = (Button) findViewById(R.id.forgotpassword);

        signin.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
        signup.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.email_edit);
        password = (EditText) findViewById(R.id.password);
        signin = (Button) findViewById(R.id.signin);
        signup = (Button) findViewById(R.id.signup);
        forgotpassword = (Button) findViewById(R.id.forgotpassword);

        signin.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
        signup.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
        //setContentView(R.layout.activity_main);

        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED) ||
                (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
            connected = true;
        } else {
            connected = false;
        }

        if (connected) {

            setContentView(R.layout.activity_main);
            email = (EditText) findViewById(R.id.email_edit);
            password = (EditText) findViewById(R.id.password);
            signin = (Button) findViewById(R.id.signin);
            signup = (Button) findViewById(R.id.signup);
            forgotpassword = (Button) findViewById(R.id.forgotpassword);

            signin.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
            signup.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));

        } else {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Please connect to internet first");
            alert.setTitle("Internet");
            alert.setCancelable(false);
            alert.setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                }
            });
            alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();

        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(view.getContext(), SignUpActivity.class);
                startActivityForResult(myintent,0);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String emailtext = email.getText().toString();
                String passwordtext = password.getText().toString();

                if (!(emailtext.isEmpty() || passwordtext.isEmpty())) {

                    mAuth = FirebaseAuth.getInstance();

                    mAuth.signInWithEmailAndPassword(emailtext, passwordtext)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        if(mAuth.getCurrentUser().isEmailVerified()) {

                                            Intent myintent = new Intent(view.getContext(),DashboardActivity.class);
                                            startActivityForResult(myintent,0);

                                        } else {

                                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                            alert.setMessage("Please verify your email with the verification email");
                                            alert.setTitle("Verification");
                                            alert.setCancelable(false);
                                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });
                                            alert.show();

                                        }

                                    } else {

                                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                        alert.setMessage("Either your Email or Password is incorrect");
                                        alert.setTitle("Error");
                                        alert.setCancelable(false);
                                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        alert.show();
                                    }
                                }
                            });

                } else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("Either your Email or Password is empty");
                    alert.setTitle("Error");
                    alert.setCancelable(false);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.show();

                }
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailtext = email.getText().toString();

                if (!(emailtext.isEmpty())) {
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.sendPasswordResetEmail(emailtext)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {

                                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                        alert.setMessage("Password Reset mail sent to your Mail-Id");
                                        alert.setTitle("Notice");
                                        alert.setCancelable(false);
                                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        alert.show();

                                    } else {

                                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                        alert.setMessage("Some Error occured");
                                        alert.setTitle("Error");
                                        alert.setCancelable(false);
                                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        alert.show();

                                    }
                                }
                            });

                } else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("Your Email field is empty");
                    alert.setTitle("Error");
                    alert.setCancelable(false);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.show();

                }
            }
        });
    }
}
