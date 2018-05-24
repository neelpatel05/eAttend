package com.eattend.miniproject.eattend;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button signup;
    EditText name,eid,email,mob,password;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signup = (Button)findViewById(R.id.signup_edit);
        name = (EditText)findViewById(R.id.name_edit);
        eid = (EditText)findViewById(R.id.eid_edit);
        email = (EditText)findViewById(R.id.email_edit);
        mob = (EditText)findViewById(R.id.mob_edit);
        password = (EditText)findViewById(R.id.password_edit);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailtext = email.getText().toString();
                String passwordtext = password.getText().toString();
                auth.createUserWithEmailAndPassword(emailtext,passwordtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser user = auth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name.getText().toString()).build();
                            user.updateProfile(profileUpdates);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference();
                            String uid = user.getUid();
                            myRef.child("users").child(uid).child("email").setValue(email.getText().toString());
                            myRef.child("users").child(uid).child("name").setValue(name.getText().toString());
                            myRef.child("users").child(uid).child("eid").setValue(eid.getText().toString());
                            myRef.child("users").child(uid).child("mob").setValue(mob.getText().toString());
                            myRef.child("users").child(uid).child("attendance").setValue("0");

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                                                alert.setTitle("Success");
                                                alert.setMessage("Your Account is created \n A Verification mail is sent to you mail id");
                                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        setContentView(R.layout.activity_main);
                                                    }
                                                });
                                                alert.setCancelable(false);
                                                alert.show();

                                            }
                                        }
                                    });

                        } else {
                            String msg = task.getException().getLocalizedMessage();
                            Toast.makeText(SignUpActivity.this, msg ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
