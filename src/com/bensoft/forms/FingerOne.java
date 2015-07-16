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

public class FingerOne extends Activity {

    private ViewFlipper TruitonFlipper;
    private float initialX;
    private RadioGroup soilType;
    private RadioGroup rgSoilType;
    private int selected;
    private String one;
    private String two;
    private String three;
    private String soilTypeValue;
    private RadioGroup rgWaterLogRisk, rgErosionPrev, rgCropRotation, rgRatoon, rgCropRes, rgManure, rgLandPrep, rgSeedBedPrep;
    private String seedBedPrepValue, landPrepValue, manureValue, cropResValue, ratoonValue, cropRotationValue, erosionPrevValue, waterLogRiskValue;
    public ConnectionDetector cd;
    private String cid;
    private String user_id;
    private String gen_id;
    private DatabaseHandler db;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_one);
        TruitonFlipper = (ViewFlipper) findViewById(R.id.flipper);
        rgSoilType = (RadioGroup) findViewById(R.id.rgSoilType);
        rgWaterLogRisk = (RadioGroup) findViewById(R.id.rgWaterLogRisk);
        rgErosionPrev = (RadioGroup) findViewById(R.id.rgErosionPrev);
        rgCropRotation = (RadioGroup) findViewById(R.id.rgCropRotation);
        rgRatoon = (RadioGroup) findViewById(R.id.rgRatoon);
        rgCropRes = (RadioGroup) findViewById(R.id.rgCropRes);
        rgManure = (RadioGroup) findViewById(R.id.rgManure);
        rgLandPrep = (RadioGroup) findViewById(R.id.rgLandPrep);
        rgSeedBedPrep = (RadioGroup) findViewById(R.id.rgSeedBedPrep);

        TruitonFlipper.setInAnimation(this, android.R.anim.fade_in);
        TruitonFlipper.setOutAnimation(this, android.R.anim.fade_out);

        initData();

    }

    private void initData() {
        cd = new ConnectionDetector(getApplicationContext());
        cid = getPrefrence(getApplicationContext(), "cid");
        gen_id = getPreference(getApplicationContext(), "val_farmer_id");
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

    public void onSoilTypeClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbSoilType1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    soilTypeValue = "0";
                }
                break;
            case R.id.rbSoilType2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    soilTypeValue = "1";
                }
                break;
            case R.id.rbSoilType3:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    soilTypeValue = "2";
                }
                break;
        }

    }

    public void onWaterLogClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbRisk1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    erosionPrevValue = "0";
                }
                break;
            case R.id.rbRisk2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    erosionPrevValue = "2";
                }
                break;
        }

    }

    public void onErosionPrevClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbErPrev1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    erosionPrevValue = "0";
                }
                break;
            case R.id.rbErPrev2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    erosionPrevValue = "3";
                }
                break;
        }

    }

    public void onCropRotationClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbCropRot1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    cropRotationValue = "0";
                }
                break;
            case R.id.rbCropRot2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    cropRotationValue = "3";
                }
                break;
        }

    }

    public void onRatoonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbRatoon1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    ratoonValue = "0";
                }
                break;
            case R.id.rbRatoon2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    ratoonValue = "2";
                }
                break;
        }

    }

    public void onCropResClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbCropRes1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    cropResValue = "0";
                }
                break;
            case R.id.rbCropRes2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    cropResValue = "1";
                }
                break;
            case R.id.rbCropRes3:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    cropResValue = "2";
                }
                break;
        }

    }

    public void onManureClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbManure1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    manureValue = "0";
                }
                break;
            case R.id.rbManure2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    manureValue = "2";
                }
                break;
        }

    }

    public void onLandPrepClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbLandPrep1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    landPrepValue = "0";
                }
                break;
            case R.id.rbLandPrep2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    landPrepValue = "2";
                }
                break;
        }

    }

    public void onSeedBedPrepClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbSeedBedPrep1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    seedBedPrepValue = "0";
                }
                break;
            case R.id.rbSeedBedPrep2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    seedBedPrepValue = "2";
                }
                break;
        }

    }

    public void onSaveDataClicked(View view) {
        if (cd.isConnectingToInternet()) {
            new uploadFingerOneForm().execute();
            showAlert("Data Saved");
        } else {
            //save to offline database
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            db.addFingerOne(new FingerOneBack(timeFormat.format(date), cid, user_id, soilTypeValue, waterLogRiskValue, erosionPrevValue, cropRotationValue, ratoonValue, cropResValue, manureValue, landPrepValue, seedBedPrepValue, getPreference(getApplicationContext(), "pid")));
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
        switch (view.getId()) {
            case R.id.bBack2:
                if (TruitonFlipper.getDisplayedChild() > 0) /*TruitonFlipper.setInAnimation(this, R.anim.in_left);
                 TruitonFlipper.setOutAnimation(this, R.anim.out_right);*/ {
                    TruitonFlipper.showPrevious();
                }
                break;
            case R.id.bNext2:
                // if(selected) {
                if (TruitonFlipper.getDisplayedChild() < (TruitonFlipper.getChildCount() - 1)) {
                        // break;

                    /*TruitonFlipper.setInAnimation(this, R.anim.in_right);
                     TruitonFlipper.setOutAnimation(this, R.anim.out_left);*/
                    if (rgSoilType.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 0)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else if (rgWaterLogRisk.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 1)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else if (rgErosionPrev.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 2)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else if (rgCropRotation.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 3)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else if (rgRatoon.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 4)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else if (rgCropRes.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 5)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else if (rgManure.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 6)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else if (rgLandPrep.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 7)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else if (rgSeedBedPrep.getCheckedRadioButtonId() == -1 && (TruitonFlipper.getDisplayedChild() == 8)) {
                        Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                    } else {
                        TruitonFlipper.showNext();
                    }
                    break;
                }
        }

    }

    private class uploadFingerOneForm extends AsyncTask {

        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_ONE_FORM);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(16);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("gen_id", gen_id));
                    nameValuePairs.add(new BasicNameValuePair("farm_id", getPreference(getApplicationContext(), "pid")));
                    nameValuePairs.add(new BasicNameValuePair("soil_type", soilTypeValue));
                    nameValuePairs.add(new BasicNameValuePair("water_log_risk", waterLogRiskValue));
                    nameValuePairs.add(new BasicNameValuePair("erosion_prev", erosionPrevValue));
                    nameValuePairs.add(new BasicNameValuePair("crop_rotation", cropRotationValue));
                    nameValuePairs.add(new BasicNameValuePair("ratoon", ratoonValue));
                    nameValuePairs.add(new BasicNameValuePair("crop_residues", cropResValue));
                    nameValuePairs.add(new BasicNameValuePair("manure", manureValue));
                    nameValuePairs.add(new BasicNameValuePair("land_prep", landPrepValue));
                    nameValuePairs.add(new BasicNameValuePair("seed_bed_prep", seedBedPrepValue));
                    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    nameValuePairs.add(new BasicNameValuePair("form_date", timeFormat.format(date)));
                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
                    //nameValuePairs.add(new BasicNameValuePair("date", date));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString1 = EntityUtils.toString(entity);
                    } else {
                        responseString1 = EntityUtils.toString(entity);
                    }
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                Log.e("Upload Finger One response: ", responseString1);
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
