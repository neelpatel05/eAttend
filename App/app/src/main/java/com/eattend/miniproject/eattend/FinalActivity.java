package com.eattend.miniproject.eattend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Date;
import java.util.GregorianCalendar;

public class FinalActivity extends AppCompatActivity {
    String attendance;
    public int att;
    String year = String.valueOf(new GregorianCalendar().get(Calendar.YEAR));
    String month = String.valueOf(new GregorianCalendar().get(Calendar.MONTH) + 1);
    String date = String.valueOf(new GregorianCalendar().get(Calendar.DAY_OF_MONTH));

    public void onBackPressed() {
        Intent intent = new Intent(FinalActivity.this, DashboardActivity.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Bundle extras = getIntent().getExtras();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(user != null) {
            final DatabaseReference myRef = database.getReference("users").child(user.getUid());
            myRef.child("attendance").child(year).child(month).child("total").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    att = dataSnapshot.getValue(Integer.class);
                    att++;


                    myRef.child("attendance").child(year).child(month).child(date).setValue("p");
                    myRef.child("attendance").child(year).child(month).child("total").setValue(att);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(FinalActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(FinalActivity.this, "You need to login again!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(FinalActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
