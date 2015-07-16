package com.bensoft.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.bensoft.farmersearch.ManualUnappFarmerSearch;

/**
 * Created by Benson on 3/16/2015.
 */
public class SignDocActivity extends Activity{
    private Button sign;
    private CheckBox conduct;
    private CheckBox contract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_doc);
        initView();
        initListeners();
    }

    private void initListeners() {
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager am1 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                Intent signDoc1 = new Intent(getApplicationContext(),ManualUnappFarmerSearch.class);
                signDoc1.putExtra("class","sign");
            // if (am1.isWiredHeadsetOn()) {
                    if(conduct.isChecked() && contract.isChecked()){
                    startActivity(signDoc1);}else{
                        showAlert("Please Check All Boxes");
                    }
              //  } else {
              //      showJackAlert("Please Connect Card Reader!");
               // }
            }
        });

    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignDocActivity.this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void initView() {
        sign = (Button)findViewById(R.id.bSign);
        conduct = (CheckBox)findViewById(R.id.cConductCode);
        contract = (CheckBox)findViewById(R.id.cFarmersContract);

    }
    private void showJackAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignDocActivity.this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    }
