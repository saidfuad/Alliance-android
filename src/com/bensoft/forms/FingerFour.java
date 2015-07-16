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

public class FingerFour extends Activity {
    private ViewFlipper TruitonFlipper;
    private float initialX;
    private RadioGroup soilType;
    private RadioGroup rgSoilType;
    private int selected;
    private String one;
    private String two;
    private String three;
    private String soilTypeValue;
    private RadioGroup rgFirstBranch,rgFoliar,rgWeeds;
    private String branchesValue,foliarValue,weedsValue;
    public  ConnectionDetector cd;
    private String cid;
    private String user_id;
    private String gen_id;
    private DatabaseHandler db;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_four);
        TruitonFlipper = (ViewFlipper) findViewById(R.id.flipper);

        rgFirstBranch = (RadioGroup) findViewById(R.id.rgFirstBranch);
        rgFoliar = (RadioGroup) findViewById(R.id.rgFoliar);
        rgWeeds = (RadioGroup) findViewById(R.id.rgWeeds);

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


    public void onFirstBranchClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbFirstBranch1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "0";
                }
                break;
            case R.id.rbFirstBranch2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "1";
                }
                break;
            case R.id.rbFirstBranch3:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "2";
                }
                break;
            case R.id.rbFirstBranch4:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "3";
                }
                break;
            case R.id.rbFirstBranch5:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "4";
                }
                break;
            case R.id.rbFirstBranch6:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "5";
                }
                break;
            case R.id.rbFirstBranch7:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "6";
                }
                break; case R.id.rbFirstBranch8:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "7";
                }
                break;
            case R.id.rbFirstBranch9:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "8";
                }
                break;
            case R.id.rbFirstBranch10:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "9";
                }
                break;
            case R.id.rbFirstBranch11:
                if (checked) {
                    // Toast.makeText(getApplicationContext(), "Three was Clicked", Toast.LENGTH_LONG).show();
                    branchesValue = "10";
                }
                break;

        }

    }
    public void onFoliarClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbFoliar1:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "One was Clicked", Toast.LENGTH_LONG).show();
                    foliarValue = "0";
                }
                break;
            case R.id.rbFoliar2:
                if (checked) {

                    //Toast.makeText(getApplicationContext(), "Two was Clicked", Toast.LENGTH_LONG).show();
                    foliarValue = "5";
                }
                break;
        }

    }
    public void onWeedsClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked

        switch (view.getId()) {
            case R.id.rbWeeds1:
                if (checked) {
                    weedsValue ="0";
                }
                break;
            case R.id.rbWeeds2:
                if (checked) {
                    weedsValue = "5";
                }
                break;
        }

    }


    public void onSaveDataClicked(View view){
        if(cd.isConnectingToInternet()){
            new uploadFingerFourForm().execute();
            showAlert("Data Uploaded to Server");
        }else{
            //save to offline database
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            db.addFingerFour(new FingerFourBack(timeFormat.format(date),cid,user_id,branchesValue,foliarValue,weedsValue,pid));
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
                        if (rgFirstBranch.getCheckedRadioButtonId() == -1&&(TruitonFlipper.getDisplayedChild() ==0)){
                            Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                        }else if (rgFoliar.getCheckedRadioButtonId() == -1&&(TruitonFlipper.getDisplayedChild() ==1)){
                            Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                        }
                        else if (rgWeeds.getCheckedRadioButtonId() == -1&&(TruitonFlipper.getDisplayedChild() ==2)){
                            Toast.makeText(getApplicationContext(), "Please Select One Value ", Toast.LENGTH_SHORT).show();
                        }else{
                            TruitonFlipper.showNext();
                        } break;
                    }
        }

    }

    private class uploadFingerFourForm extends AsyncTask {
        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_FOUR_FORM);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("first_branch", branchesValue));
                    nameValuePairs.add(new BasicNameValuePair("foliar", foliarValue));
                    nameValuePairs.add(new BasicNameValuePair("weeds", weedsValue));
                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
                    SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    nameValuePairs.add(new BasicNameValuePair("form_date", timeFormat.format(date)));
                    nameValuePairs.add(new BasicNameValuePair("farm_id", pid));
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

                Log.e("Upload Finger Four response: ",responseString1);
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