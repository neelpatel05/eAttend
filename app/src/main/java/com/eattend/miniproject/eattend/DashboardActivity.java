package com.eattend.miniproject.eattend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        name = (TextView)findViewById(R.id.dashboard_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

            name.setText(user.getEmail());

        } else {

            Toast.makeText(DashboardActivity.this,"Some Error",Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    public void location(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
