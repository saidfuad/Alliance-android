/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.land_prep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.bensoft.entities.FarmAssFormsMajor;
import com.bensoft.farm.assesment.AllFarmAssesmentActivity;
import static com.bensoft.farm.assesment.land_prep.PloughingActivity.getPrefrence;
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
public class PloughingActivity extends Activity implements OnClickListener {

    private RadioButton rbTractor;
    private RadioButton rbOxen;
    private DatePicker dpPloughDate;
    private EditText etHiredHours;
    private EditText etMoneyOut;
    private EditText etRemarks;
    private Button bBack;
    private Button bNext;
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
    private EditText etFamilyHours;
    private String hired_hours;
    private String money_out;
    private String remarks;
    private String activity_date;
    private final static String FORM_TYPE_ID = "3";
    private String collect_date;
    private String plough_method;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_ploughing);
        initView();
        initListeners();
        initData();
    }

    private void initView() {
        rbTractor = (RadioButton) findViewById(R.id.rbTractor);
        rbOxen = (RadioButton) findViewById(R.id.rbOxen);
        dpPloughDate = (DatePicker) findViewById(R.id.dpPloughDate);
        etFamilyHours = (EditText) findViewById(R.id.etFamilyHours);
        etHiredHours = (EditText) findViewById(R.id.etHiredHours);
        etMoneyOut = (EditText) findViewById(R.id.etMoneyOut);
        etRemarks = (EditText) findViewById(R.id.etRemarks);
        bBack = (Button) findViewById(R.id.bBack);
        bNext = (Button) findViewById(R.id.bNext);
    }

    private void initListeners() {
        rbTractor.setOnClickListener(this);
        rbOxen.setOnClickListener(this);
        bBack.setOnClickListener(this);
        bNext.setOnClickListener(this);

    }

    private void initData() {
        cd = new ConnectionDetector(PloughingActivity.this);
        db = new DatabaseHandler(PloughingActivity.this);
        pid = getPrefrence(PloughingActivity.this, "pid");
        user_id = getPrefrence(PloughingActivity.this, "user_id");
        cid = getPrefrence(PloughingActivity.this, "cid");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //GPS
        gpsTracker = new GPSTracker(getApplicationContext());
        //
        newf = new Intent(getApplicationContext(), AllFarmAssesmentActivity.class);
        newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //previous form
        previous = new Intent(getApplicationContext(), LandClearActivity.class);
        //next form
        next = new Intent(getApplicationContext(), RippingActivity.class);
        //get current date
        d1 = new Date();
        collect_date = sdf.format(d1);
        //gps coordinate        
        if (gpsTracker.canGetLocation) {
            // do {
            lat = gpsTracker.getLatitude();
            longt = gpsTracker.getLongitude();
        }

    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    public void onClick(View v) {
        if (v == rbTractor) {
            // Handle clicks for rbTractor
            if (rbTractor.isChecked()) {
                plough_method = "tractor";
            } else {
                plough_method = "";
            }
        } else if (v == rbOxen) {
            // Handle clicks for rbOxen
            if (rbOxen.isChecked()) {
                plough_method = "oxen";
            } else {
                plough_method = "";
            }
        } else if (v == bBack) {
            // Handle clicks for bBack
            // delete from farm ass form major by form type id and collect_date
            //if (db.checkFarmAssFormsMajor(FORM_TYPE_ID, collect_date)) {
            //delete particular form
            db.deleteSingleFarmAssFormsMajor(pid, FORM_TYPE_ID, collect_date);
            //}
            startActivity(previous);
        } else if (v == bNext) {
            // Handle clicks for bNext

            //get data from text boxes
            activity_date = dpPloughDate.getYear() + "-" + dpPloughDate.getMonth() + "-" + dpPloughDate.getDayOfMonth();
            String family_hours = etFamilyHours.getText().toString();
            hired_hours = etHiredHours.getText().toString();
            money_out = etMoneyOut.getText().toString();
            remarks = etRemarks.getText().toString();

            if (!"".equals(activity_date) && !"".equals(plough_method) && !"".equals(family_hours) && !"".equals(hired_hours) && !"".equals(money_out) && !"".equals(remarks)) {
                db.addFarmAssMajor(new FarmAssFormsMajor(pid, FORM_TYPE_ID, plough_method, activity_date, family_hours, hired_hours, money_out, remarks, user_id, collect_date, String.valueOf(lat), String.valueOf(longt), cid));
                startActivity(next);
            } else {
                Toast.makeText(getApplicationContext(), "Fill in all the details", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); //To change body of generated methods, choose Tools | Templates.
        startActivity(previous);
    }

}
