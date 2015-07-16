/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.soil_fertility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.bensoft.entities.FarmAssFormsMedium;
import com.bensoft.farm.assesment.AllFarmAssesmentActivity;
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
public class FoliarFeedActivity2 extends Activity implements View.OnClickListener{

    private DatePicker dpFoliarFeedAppDate2;
    private EditText etFamilyHours;
    private EditText etHiredHours;
    private EditText etMoneyOut;
    private Spinner spFoliarFeedType;
    private EditText etFoliarFeedQuantity;
    private RadioButton rbKnapsack;
    private RadioButton rbUlva;
    private Button bBack;
    private Button bNext;
          private String spray_method = "";
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
    private final static String FORM_TYPE_ID = "21";
    private String collect_date;
    private String input_id;
    private String input_quantity;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_foliar_feed2);
    initView();
        initListeners();
        initData();
    }

    private void initView() {
        dpFoliarFeedAppDate2 = (DatePicker) findViewById(R.id.dpFoliarFeedAppDate2);
        etFamilyHours = (EditText) findViewById(R.id.etFamilyHours);
        etHiredHours = (EditText) findViewById(R.id.etHiredHours);
        etMoneyOut = (EditText) findViewById(R.id.etMoneyOut);
        spFoliarFeedType = (Spinner) findViewById(R.id.spFoliarFeedType);
        etFoliarFeedQuantity = (EditText) findViewById(R.id.etFoliarFeedQuantity);
        rbKnapsack = (RadioButton) findViewById(R.id.rbKnapsack);
        rbUlva = (RadioButton) findViewById(R.id.rbUlva);
        bBack = (Button) findViewById(R.id.bBack);
        bNext = (Button) findViewById(R.id.bNext);
    }

    private void initData() {
        cd = new ConnectionDetector(FoliarFeedActivity2.this);
        db = new DatabaseHandler(FoliarFeedActivity2.this);
        pid = getPrefrence(FoliarFeedActivity2.this, "pid");
        user_id = getPrefrence(FoliarFeedActivity2.this, "user_id");
        cid = getPrefrence(FoliarFeedActivity2.this, "cid");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //GPS
        gpsTracker = new GPSTracker(getApplicationContext());
        //
        newf = new Intent(getApplicationContext(), AllFarmAssesmentActivity.class);
        newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //previous form
        previous = new Intent(getApplicationContext(), AllFarmAssesmentActivity.class);
        previous.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //next form
        next = new Intent(getApplicationContext(), FoliarFeedActivity2.class);
        //get current date
        d1 = new Date();
        collect_date = sdf.format(d1);

    }

    private void initListeners() {
        rbKnapsack.setOnClickListener(this);
        rbUlva.setOnClickListener(this);
        bBack.setOnClickListener(this);
        bNext.setOnClickListener(this);
    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    public void onClick(View v) {
        if (v == rbKnapsack) {
            // Handle clicks for rbKnapsack
            spray_method = "0";
        } else if (v == rbUlva) {
            // Handle clicks for rbUlva
            spray_method = "1";
        } else if (v == bBack) {
            // Handle clicks for bBack
            // delete from farm ass form major by form type id and collect_date
            //if (db.checkFarmAssFormsMajor(FORM_TYPE_ID, collect_date)) {
            //delete particular form
            db.deleteSingleFarmAssFormsMajor(pid,FORM_TYPE_ID,collect_date);
            //}
            startActivity(previous);
        } else if (v == bNext) {
            // Handle clicks for bNext
            if (gpsTracker.canGetLocation) {
                // do 
                lat = gpsTracker.getLatitude();
                longt = gpsTracker.getLongitude();
            }
            //get data from text boxes
            activity_date = dpFoliarFeedAppDate2.getYear() + "-" + dpFoliarFeedAppDate2.getMonth() + "-" + dpFoliarFeedAppDate2.getDayOfMonth();
            String family_hours = etFamilyHours.getText().toString();
            hired_hours = etHiredHours.getText().toString();
            money_out = etMoneyOut.getText().toString();
            remarks = "";
            input_id = String.valueOf(spFoliarFeedType.getSelectedItemPosition());
            input_quantity = etFoliarFeedQuantity.getText().toString();

            if (!"".equals(activity_date) && !"".equals(family_hours) && !"".equals(hired_hours) && !"".equals(money_out) && "".equals(remarks) || !"".equals(spray_method) && input_id != null && !"".equals(input_quantity)) {
                db.addFarmAssMedium(new FarmAssFormsMedium(pid, FORM_TYPE_ID, "none", activity_date, family_hours, hired_hours, money_out, input_id, input_quantity, spray_method, user_id, collect_date, String.valueOf(lat), String.valueOf(longt), cid));
                startActivity(next);
            } else {
                Toast.makeText(getApplicationContext(), "Fill in all the details", Toast.LENGTH_LONG).show();
            }
        }
    }

}