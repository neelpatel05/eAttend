package com.eattend.miniproject.eattend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class QRActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;
    public String qrText,uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        Bundle extras = getIntent().getExtras();
        uname = extras.getString("uname");


        qrScan = new IntentIntegrator(this);

        qrScan.initiateScan();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Result not found", Toast.LENGTH_LONG).show();
            }
            else{
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    qrText = obj.getString("name");
                    if(qrText.equalsIgnoreCase(uname)) {
                        Intent intent = new Intent(this, FinalActivity.class);
                        intent.putExtra("uname", uname);
                        startActivity(intent);
                    }

                }
                catch (JSONException e){
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
