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

public class FingerThree extends Activity {
    private ViewFlipper TruitonFlipper;
    private float initialX;
    private RadioGroup rgGapfill,rgGapFillOnEmer,rgThinNum,rgThinAfterEmer;
    private String gapFillValue,gapFillOnEmerValue,thinNumValue,thinAfterEmerValue;
    public  ConnectionDetector cd;
    private String cid;
    private String user_id;
    private String gen_id;
    private DatabaseHandler db;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_three);
        TruitonFlipper = (ViewFlipper) findViewById(R.id.flipper);
        rgGapfill = (RadioGroup) findViewById(R.id.rgGapfill);
        rgGapFillOnEmer = (RadioGroup) findViewById(R.id.rgGapFillOnEmer);
        rgThinNum = (RadioGroup) findViewById(R.id.rgThinNum);
        rgThinAfterEmer = (RadioGroup) findViewById(R.id.rgThinAfterEmer);

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


    public void onGapFillClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbGapFill1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    gapFillValue = "0";
                }
                break;
            case R.id.rbGapFill2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    gapFillValue = "5";
                }
                break;
        }

    }
    public void onGapFillAfterEmerClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbGapFillOnEmer1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    gapFillOnEmerValue = "0";
                }
                break;
            case R.id.rbGapFillOnEmer2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    gapFillOnEmerValue = "5";
                }
                break;
        }

    }
    public void onThinNumClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbThinNum1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    thinNumValue ="0";
                }
                break;
            case R.id.rbThinNum2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    thinNumValue = "5";
                }
                break;
        }

    }
    public void onThinAfterEmerClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbThinAfterEmer1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    thinAfterEmerValue = "0";
                }
                break;
            case R.id.rbThinAfterEmer2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    thinAfterEmerValue = "5";
                }
                break;
        }

    }

    public void onSaveDataClicked(View view){
        if(cd.isConnectingToInternet()){
            new uploadFingerThreeForm().execute();
            showAlert("Data Uploaded to Server");
        }else{
            //save to offline database
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            db.addFingerThree(new FingerThreeBack(timeFormat.format(date),cid,user_id,gapFillValue,gapFillOnEmerValue,thinNumValue,thinAfterEmerValue,pid));
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
                        if (rgGapfill.getCheckedRadioButtonId() == -1 &&(TruitonFlipper.getDisplayedChild() ==0) ) {
                            Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                        } else if (rgGapFillOnEmer.getCheckedRadioButtonId() == -1&&(TruitonFlipper.getDisplayedChild() ==1)){
                            Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                        }else if (rgThinNum.getCheckedRadioButtonId() == -1&&(TruitonFlipper.getDisplayedChild() ==2)){
                            Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                        }
                        else if (rgThinAfterEmer.getCheckedRadioButtonId() == -1&&(TruitonFlipper.getDisplayedChild() ==3)){
                            Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();

                        }else{
                            TruitonFlipper.showNext();
                        }
                        break;
                    }
                }

        }

    private class uploadFingerThreeForm extends AsyncTask {
        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_THREE_FORM);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("farm_id", pid));
                    nameValuePairs.add(new BasicNameValuePair("gap_fill", gapFillValue));
                    nameValuePairs.add(new BasicNameValuePair("gap_fill_emer", gapFillOnEmerValue));
                    nameValuePairs.add(new BasicNameValuePair("thin_num", thinNumValue));
                    nameValuePairs.add(new BasicNameValuePair("thin_num_emer", thinAfterEmerValue));
                    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    nameValuePairs.add(new BasicNameValuePair("form_date", timeFormat.format(date)));
                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
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

                Log.e("Upload Finger Three response: ",responseString1);
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