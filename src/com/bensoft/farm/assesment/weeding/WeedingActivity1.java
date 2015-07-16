/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.weeding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.bensoft.entities.FarmAssFormsMajor;
import com.bensoft.farm.assesment.AllFarmAssesmentActivity;
import com.bensoft.farm.assesment.land_prep.PloughingActivity;
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
public class WeedingActivity1 extends Activity implements View.OnClickListener {

    private DatePicker dpWeedingDate1;
    private EditText etFamilyHours;
    private EditText etHiredHours;
    private EditText etMoneyOut;
    private EditText etRemarks;
    private Button bBack;
    private Button bNext;
    private Date d1;
    private SimpleDateFormat sdf;
    private GPSTracker gpsTracker;
    private DatabaseHandler db;
    private ConnectionDetector cd;
    private String pid;
    private String user_id;
    private String cid;
    private Intent next;
    private double lat;
    private double longt;
    private final static String FORM_TYPE_ID = "23";
    private String activity_date;
    private String family_hours;
    private String hired_hours;
    private String money_out;
    private String remarks;
    private String collect_date;
    private Intent previous;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_weeding1);
       initView();
        initListeners();
        initData();
    }

    private void initView() {
        dpWeedingDate1 = (DatePicker) findViewById(R.id.dpWeedingDate1);
        etFamilyHours = (EditText) findViewById(R.id.etFamilyHours);
        etHiredHours = (EditText) findViewById(R.id.etHiredHours);
        etMoneyOut = (EditText) findViewById(R.id.etMoneyOut);
        etRemarks = (EditText) findViewById(R.id.etRemarks);
        bBack = (Button) findViewById(R.id.bBack);
        bNext = (Button) findViewById(R.id.bNext);
    }

    private void initListeners() {
        bBack.setOnClickListener(this);
        bNext.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == bBack) {
            // Handle clicks for bBack
            db.deleteSingleFarmAssFormsMajor(pid,FORM_TYPE_ID, collect_date);
            startActivity(previous);
        } else if (v == bNext) {
            // Handle clicks for bNext

            if (gpsTracker.canGetLocation) {
                // do {
                lat = gpsTracker.getLatitude();
                longt = gpsTracker.getLongitude();
            }
            //get data from text boxes
            activity_date = dpWeedingDate1.getYear() + "-" + dpWeedingDate1.getMonth() + "-" + dpWeedingDate1.getDayOfMonth();
            family_hours = etFamilyHours.getText().toString();
            hired_hours = etHiredHours.getText().toString();
            money_out = etMoneyOut.getText().toString();
            remarks = etRemarks.getText().toString();

            if (!"".equals(activity_date) && !"".equals(family_hours) && !"".equals(hired_hours) && !"".equals(money_out) && !"".equals(remarks)) {
                db.addFarmAssMajor(new FarmAssFormsMajor(pid, FORM_TYPE_ID, "none", activity_date, family_hours, hired_hours, money_out, remarks, user_id, collect_date, String.valueOf(lat), String.valueOf(longt), cid));
                startActivity(next);
            } else {
                Toast.makeText(getApplicationContext(), "Fill in all the details", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initData() {
        cd = new ConnectionDetector(WeedingActivity1.this);
        db = new DatabaseHandler(WeedingActivity1.this);
        pid = getPrefrence(WeedingActivity1.this, "pid");
        user_id = getPrefrence(WeedingActivity1.this, "user_id");
        cid = getPrefrence(WeedingActivity1.this, "cid");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //GPS
        gpsTracker = new GPSTracker(getApplicationContext());
        //
        previous = new Intent(getApplicationContext(), AllFarmAssesmentActivity.class);
        previous.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //next form
        next = new Intent(getApplicationContext(), WeedingActivity2.class);
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
}
