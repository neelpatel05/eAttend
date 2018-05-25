package com.eattend.miniproject.eattend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DashboardActivity extends AppCompatActivity {

    String uname;
    TextView name, email, mob, eid, attendance;
    ProgressBar progressBar;
    FirebaseUser user;
    String year = String.valueOf(new GregorianCalendar().get(Calendar.YEAR));
    String month = String.valueOf(new GregorianCalendar().get(Calendar.MONTH) + 1);
    String date = String.valueOf(new GregorianCalendar().get(Calendar.DAY_OF_MONTH));

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        name = (TextView)findViewById(R.id.dashboard_name);
        email = (TextView)findViewById(R.id.dashboard_email);
        mob = (TextView)findViewById(R.id.dashboard_mob);
        eid = (TextView)findViewById(R.id.dashboard_eid);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        attendance = (TextView) findViewById(R.id.dashboard_attendance);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child("users").child(user.getUid()).child("email").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    email.setText(value);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DashboardActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            myRef.child("users").child(user.getUid()).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    name.setText(value);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DashboardActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            myRef.child("users").child(user.getUid()).child("mob").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    mob.setText(value);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DashboardActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            myRef.child("users").child(user.getUid()).child("eid").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    eid.setText(value);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DashboardActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            myRef.child("users").child(user.getUid()).child("attendance").child(year).child(month).child("total").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int total = dataSnapshot.getValue(Integer.class);
                    float percent = (total*100)/Integer.parseInt(date);
                    String text = String.valueOf(percent) + "%";
                    attendance.setText(text);
                    progressBar.setMax(Integer.parseInt(date));
                    progressBar.setProgress(total);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            /*name.setText(user.getEmail());
            uname = user.getDisplayName();*/

        } else {

            Toast.makeText(DashboardActivity.this,"Some Error",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void signOut (View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void location(View view){
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        if(user != null) {
            DatabaseReference myRef = database.getReference("users").child(user.getUid()).child("attendance")
                    .child(year)
                    .child(month)
                    .child(date);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue(String.class);
                    if(value != null) {
                        if(value.equals("p"))
                            Toast.makeText(DashboardActivity.this, "Attendance Is already done", Toast.LENGTH_LONG).show();
                        else {
                            Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(DashboardActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(DashboardActivity.this, "You need to login again!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
