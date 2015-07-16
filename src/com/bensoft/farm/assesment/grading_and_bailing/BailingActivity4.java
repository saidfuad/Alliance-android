/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.grading_and_bailing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.bensoft.entities.FarmAssFormsMajor;
import com.bensoft.farm.assesment.AllFarmAssesmentActivity;
import com.bensoft.farm.assesment.land_prep.PotholingActivity;
import com.bensoft.main.R;
import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.GPSTracker;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Benson
 */
public class BailingActivity4 extends Activity implements View.OnClickListener {

    private DatePicker dpBailingDate4;
    private EditText etFamilyHours;
    private EditText etHiredHours;
    private EditText etMoneyOut;
    private EditText etRemarks;
    private Button bBack;
    private Button bSave;
    private ConnectionDetector cd;
    private DatabaseHandler db;
    private String pid;
    private String user_id;
    private String cid;
    private SimpleDateFormat sdf;
    private GPSTracker gpsTracker;
    private Intent newf;
    private Intent next;
    private Intent previous;
    private Date d1;
    private double lat;
    private double longt;
    private String hired_hours;
    private String money_out;
    private String remarks;
    private String activity_date;
    private final static String FORM_TYPE_ID = "129";
    private String collect_date;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_bailing4);
        initView();
        initListeners();
        initData();
    }

    private void initView() {
        dpBailingDate4 = (DatePicker) findViewById(R.id.dpBailingDate4);
        etFamilyHours = (EditText) findViewById(R.id.etFamilyHours);
        etHiredHours = (EditText) findViewById(R.id.etHiredHours);
        etMoneyOut = (EditText) findViewById(R.id.etMoneyOut);
        etRemarks = (EditText) findViewById(R.id.etRemarks);
        bBack = (Button) findViewById(R.id.bBack);
        bSave = (Button) findViewById(R.id.bSave);
    }

    private void initListeners() {
        bBack.setOnClickListener(this);
        bSave.setOnClickListener(this);
    }

    private void initData() {
        cd = new ConnectionDetector(BailingActivity4.this);
        db = new DatabaseHandler(BailingActivity4.this);
        pid = getPrefrence(BailingActivity4.this, "pid");
        user_id = getPrefrence(BailingActivity4.this, "user_id");
        cid = getPrefrence(BailingActivity4.this, "cid");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //GPS
        gpsTracker = new GPSTracker(getApplicationContext());
        //
        newf = new Intent(getApplicationContext(), AllFarmAssesmentActivity.class);
        newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //previous form
        previous = new Intent(getApplicationContext(), GradingActivity4.class);
        //next form
        // next = new Intent(getApplicationContext(), AllFarmAssesmentActivity.class);
        //get current date
        d1 = new Date();
        collect_date = sdf.format(d1);

    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    public void onClick(View v) {
        if (v == bBack) {
            // Handle clicks for bBack
            // delete from farm ass form major by form type id and collect_date
            //if (db.checkFarmAssFormsMajor(FORM_TYPE_ID, collect_date)) {
            //delete particular form
            db.deleteSingleFarmAssFormsMajor(pid,FORM_TYPE_ID, collect_date);
            //}
            startActivity(previous);
        } else if (v == bSave) {
            // Handle clicks for bSave
            //get data from text boxes
            activity_date = dpBailingDate4.getYear() + "-" + dpBailingDate4.getMonth() + "-" + dpBailingDate4.getDayOfMonth();
            String family_hours = etFamilyHours.getText().toString();
            hired_hours = etHiredHours.getText().toString();
            money_out = etMoneyOut.getText().toString();
            remarks = etRemarks.getText().toString();
            d1 = new Date();
            collect_date = sdf.format(d1);

            if (gpsTracker.canGetLocation) {
                // do 
                lat = gpsTracker.getLatitude();
                longt = gpsTracker.getLongitude();
            }

            db.addFarmAssMajor(new FarmAssFormsMajor(pid, FORM_TYPE_ID, "none", activity_date, family_hours, hired_hours, money_out, remarks, user_id, collect_date, String.valueOf(lat), String.valueOf(longt), cid));
            startActivity(newf);
        }
    }
}
