package com.bensoft.forms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bensoft.utils.Config;
import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.main.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FingerFive extends Activity {
    private ViewFlipper TruitonFlipper;
    private float initialX;
    private RadioGroup soilType;
    private RadioGroup rgSoilType;
    private int selected;
    private String one;
    private String two;
    private String three;
    private String soilTypeValue;
    private RadioGroup rgWaterLogRisk,rgErosionPrev,rgCropRotation,rgRatoon,rgCropRes,rgManure,rgLandPrep,rgSeedBedPrep;
    private String seedBedPrepValue,landPrepValue,manureValue,cropResValue,ratoonValue,cropRotationValue,erosionPrevValue,waterLogRiskValue;
    public  ConnectionDetector cd;
    private String cid;
    private String user_id;
    private String gen_id;
    private RadioGroup rgPestLevel;
    private RadioGroup rgSafeUsageCans;
    private RadioGroup rgCorrectPesticide;
    private RadioGroup rgPestAbsDuration;
    private RadioGroup rgScoutSign;
    private RadioGroup rgPestDamage;
    private RadioGroup rgEmptyCans;
    private RadioGroup rgPegBoard;
    private RadioGroup rgScoutMethod;
    private RadioGroup rgSprayingTime;
    private String safeUsageCansValue;
    private String scoutSignValue;
    private String pestDamageValue;
    private String pestLevelValue;
    private String sprayTimeValue;
    private String scoutMethodValue;
    private String correctPesticideValue;
    private String emptyCansValue;
    private String pestAbsDurrationValue;
    private String pegBoardValue;
    private DatabaseHandler db;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_five);
        TruitonFlipper = (ViewFlipper) findViewById(R.id.flipper);
        rgPestLevel = (RadioGroup) findViewById(R.id.rgPestLevel);
        rgPestDamage = (RadioGroup) findViewById(R.id.rgPestDamage);
        rgScoutSign = (RadioGroup) findViewById(R.id.rgScoutSign);
        rgEmptyCans = (RadioGroup) findViewById(R.id.rgEmptyCans);
        rgPegBoard = (RadioGroup) findViewById(R.id.rgPegBoard);
        rgScoutMethod = (RadioGroup) findViewById(R.id.rgScoutMethod);
        rgSprayingTime = (RadioGroup) findViewById(R.id.rgSprayingTime);
        rgPestAbsDuration = (RadioGroup) findViewById(R.id.rgPestAbsDuration);
        rgCorrectPesticide = (RadioGroup) findViewById(R.id.rgCorrectPesticide);
        rgSafeUsageCans = (RadioGroup) findViewById(R.id.rgSafeUsageCans);


        TruitonFlipper.setInAnimation(this, android.R.anim.fade_in);
        TruitonFlipper.setOutAnimation(this, android.R.anim.fade_out);

        initData();

    }

    private void initData() {
        cd = new ConnectionDetector(getApplicationContext());
        cid = getPrefrence(getApplicationContext(),"cid");
        gen_id = getPreference(getApplicationContext(),"val_farmer_id");
        user_id = getPreference(getApplicationContext(), "user_id");
        pid = getPreference(getApplicationContext(), "pid");
        db = new DatabaseHandler(getApplicationContext());
    }

    private String getPreference(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }


    public void onPestLevelClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbPestLevel1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    pestLevelValue = "0";
                }
                break;
            case R.id.rbPestLevel2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    pestLevelValue = "2";
                }
                break;
        }

    }
    public void onPestDamageClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbPestDamage1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    pestDamageValue = "0";
                }
                break;
            case R.id.rbPestDamage2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    pestDamageValue = "2";
                }
                break;
        }

    }
    public void onLastScoutClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbScoutSign1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    scoutSignValue ="0";
                }
                break;
            case R.id.rbScoutSign2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    scoutSignValue = "2";
                }
                break;
        }

    }
    public void onEmptyPestCansClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbEmptyCans1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    emptyCansValue = "0";
                }
                break;
            case R.id.rbEmptyCans2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    emptyCansValue = "2";
                }
                break;
        }

    }
    public void onPegBoardClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbPegBoard1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    pegBoardValue = "0";
                }
                break;
            case R.id.rbPegBoard2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    pegBoardValue = "2";
                }
                break;
        }

    }
    public void onScoutMethodClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbScoutMethod1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    scoutMethodValue = "0";
                }
                break;
            case R.id.rbScoutMethod2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    scoutMethodValue = "2";
                }
                break;
        }

    }
    public void onSprayTimeClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbSprayingTime1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    sprayTimeValue = "0";
                }
                break;
            case R.id.rbSprayingTime2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    sprayTimeValue = "2";
                }
                break;
        }

    }
    public void onPestAbsClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbPestAbsDuration1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    pestAbsDurrationValue = "0";
                }
                break;
            case R.id.rbPestAbsDuration2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    pestAbsDurrationValue = "2";
                }
                break;
        }

    }
    public void onCorrectPestUseClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbCorrectPesticide1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    correctPesticideValue = "0";
                }
                break;
            case R.id.rbCorrectPesticide2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    correctPesticideValue = "2";
                }
                break;
        }

    }
    public void onSafePestUseClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbSafeUsageCans1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    safeUsageCansValue = "0";
                }
                break;
            case R.id.rbSafeUsageCans2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    safeUsageCansValue = "2";
                }
                break;
        }

    }

    public void onSaveDataClicked(View view){
        if(cd.isConnectingToInternet()){
            new uploadFingerFiveForm().execute();
            showAlert("Data Uploaded to Server");
        }else{
            //save to offline database
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            db.addFingerFive(new FingerFiveBack(timeFormat.format(date),cid,user_id,pestLevelValue,pestDamageValue,scoutSignValue,emptyCansValue,pegBoardValue,scoutMethodValue,sprayTimeValue,pestAbsDurrationValue,correctPesticideValue,safeUsageCansValue,pid));
            showAlert("Data Saved Offline");
        }

    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void onNavClicked(View view) {
       switch(view.getId()) {
           case R.id.bBack2:
               if (TruitonFlipper.getDisplayedChild() > 0)


				/*TruitonFlipper.setInAnimation(this, R.anim.in_left);
                TruitonFlipper.setOutAnimation(this, R.anim.out_right);*/

                   TruitonFlipper.showPrevious();
               break;
           case R.id.bNext2:

                   // Toast.makeText(getApplicationContext(),"Displayed child: "+String.valueOf(TruitonFlipper.getChildCount()),Toast.LENGTH_LONG).show();

                   // if(selected) {
                   if (TruitonFlipper.getDisplayedChild() < (TruitonFlipper.getChildCount() - 1)) {
                       // break;

				/*TruitonFlipper.setInAnimation(this, R.anim.in_right);
                TruitonFlipper.setOutAnimation(this, R.anim.out_left);*/
                       if (rgPestLevel.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 0)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgPestDamage.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 1)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgScoutSign.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 2)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgEmptyCans.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 3)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgPegBoard.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 4)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgScoutMethod.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 5)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgSprayingTime.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 6)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgPestAbsDuration.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 7)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgCorrectPesticide.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 8)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else if (rgSafeUsageCans.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 9)) {
                           Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                       } else {
                           TruitonFlipper.showNext();
                       }
                       break;
                   }
       }

    }

    private class uploadFingerFiveForm extends AsyncTask {
        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_FIVE_FORM);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
                    nameValuePairs.add(new BasicNameValuePair("farm_id", pid));
                    nameValuePairs.add(new BasicNameValuePair("pest_level", pestLevelValue));
                    nameValuePairs.add(new BasicNameValuePair("pest_damage", pestDamageValue));
                    nameValuePairs.add(new BasicNameValuePair("last_scout", scoutSignValue));
                    nameValuePairs.add(new BasicNameValuePair("empty_cans", emptyCansValue));
                    nameValuePairs.add(new BasicNameValuePair("peg_board_avail", pegBoardValue));
                    nameValuePairs.add(new BasicNameValuePair("scout_method", scoutMethodValue));
                    nameValuePairs.add(new BasicNameValuePair("spray_time", sprayTimeValue));
                    nameValuePairs.add(new BasicNameValuePair("pest_abs_duration", pestAbsDurrationValue));
                    nameValuePairs.add(new BasicNameValuePair("correct_use_pesticide", correctPesticideValue));
                    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Date date = new Date();
                    nameValuePairs.add(new BasicNameValuePair("form_date", timeFormat.format(date)));
                    nameValuePairs.add(new BasicNameValuePair("safe_usage_cans", safeUsageCansValue));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString1 = EntityUtils.toString(entity);
                    } else {
                        responseString1 = "Error occurred! Http Status Code: "
                                + statusCode;
                    }
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                Log.e("Upload Finger Five response: ",responseString1);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }
    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }
}