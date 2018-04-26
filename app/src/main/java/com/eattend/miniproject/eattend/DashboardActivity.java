package com.eattend.miniproject.eattend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    String uname;
    TextView name, email, mob, eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        name = (TextView)findViewById(R.id.dashboard_name);
        email = (TextView)findViewById(R.id.dashboard_email);
        mob = (TextView)findViewById(R.id.dashboard_mob);
        eid = (TextView)findViewById(R.id.dashboard_eid);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

            /*name.setText(user.getEmail());
            uname = user.getDisplayName();*/

        } else {

            Toast.makeText(DashboardActivity.this,"Some Error",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    public void location(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("uname",uname);
        startActivity(intent);
    }

}
