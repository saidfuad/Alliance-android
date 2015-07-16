/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.herb_application;

import com.bensoft.entities.FarmAssFormsMedium;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.bensoft.entities.FarmAssFormsMajor;
import com.bensoft.entities.Villages;
import com.bensoft.farm.assesment.AllFarmAssesmentActivity;
import com.bensoft.main.FarmerRegistrationActivity;
import com.bensoft.main.R;
import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.GPSTracker;
import com.bensoft.entities.Herbicides;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Benson
 */
public class HerbApplicationActivity1 extends Activity implements OnClickListener {

    private DatePicker dpHerbAppDate;
    private EditText etFamilyHours;
    private EditText etHiredHours;
    private EditText etMoneyOut;
    private Spinner spHerbicideType;
    private EditText etHerbicideQuantity;
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
    private final static String FORM_TYPE_ID = "9";
    private String collect_date;
    private String input_id;
    private String input_quantity;
    private ArrayAdapter<MyData> dataAdapter5;
    private MyData[] items5;

    /**
     * Called when the activity is first created.
     *
     * @param icicle
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_herb_app1);
        initView();
        initListeners();
        initData();
    }

    private void initView() {
        dpHerbAppDate = (DatePicker) findViewById(R.id.dpHerbAppDate);
        etFamilyHours = (EditText) findViewById(R.id.etFamilyHours);
        etHiredHours = (EditText) findViewById(R.id.etHiredHours);
        etMoneyOut = (EditText) findViewById(R.id.etMoneyOut);
        spHerbicideType = (Spinner) findViewById(R.id.spHerbicideType);
        etHerbicideQuantity = (EditText) findViewById(R.id.etHerbicideQuantity);
        rbKnapsack = (RadioButton) findViewById(R.id.rbKnapsack);
        rbUlva = (RadioButton) findViewById(R.id.rbUlva);
        bBack = (Button) findViewById(R.id.bBack);
        bNext = (Button) findViewById(R.id.bNext);
    }

    private void initData() {
        cd = new ConnectionDetector(HerbApplicationActivity1.this);
        db = new DatabaseHandler(HerbApplicationActivity1.this);
        pid = getPrefrence(HerbApplicationActivity1.this, "pid");
        user_id = getPrefrence(HerbApplicationActivity1.this, "user_id");
        cid = getPrefrence(HerbApplicationActivity1.this, "cid");
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
        next = new Intent(getApplicationContext(), HerbApplicationActivity2.class);
        //get current date
        d1 = new Date();
        collect_date = sdf.format(d1);
        //load villages
        loadVillages();

    }

    private void loadVillages() {
        db = new DatabaseHandler(getApplicationContext());
        List<Herbicides> herbicidesList = db.getAllHerbicides();
        dataAdapter5 = new ArrayAdapter<MyData>(getBaseContext(),
                android.R.layout.simple_spinner_item);
        items5 = new MyData[herbicidesList.size()];
        int i = 0;
        for (Herbicides r : herbicidesList) {
            if (i < herbicidesList.size()) {
                items5[i] = new MyData(r.getInputType(), r.getInputID());
                Log.e("Mydata:", items5[i].getValue());
            }

            i++;
        }

        dataAdapter5.addAll(items5);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter5.notifyDataSetChanged();
        spHerbicideType.setAdapter(dataAdapter5);
        db.close();
    }

    private void initListeners() {
        rbKnapsack.setOnClickListener(this);
        rbUlva.setOnClickListener(this);
        bBack.setOnClickListener(this);
        bNext.setOnClickListener(this);
        spHerbicideType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //svillage = village.getItemAtPosition(position).toString();
                MyData d = items5[position];
                input_id = d.getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            if (rbKnapsack.isChecked()) {
                spray_method = "knapsack";
            } else {
                spray_method = "";
            }
        } else if (v == rbUlva) {
            // Handle clicks for rbUlva
            if (rbKnapsack.isChecked()) {
                spray_method = "ulva+";
            } else {
                spray_method = "";
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
            if (gpsTracker.canGetLocation) {
                // do 
                lat = gpsTracker.getLatitude();
                longt = gpsTracker.getLongitude();
            }
            //get data from text boxes
            activity_date = dpHerbAppDate.getYear() + "-" + dpHerbAppDate.getMonth() + "-" + dpHerbAppDate.getDayOfMonth();
            String family_hours = etFamilyHours.getText().toString();
            hired_hours = etHiredHours.getText().toString();
            money_out = etMoneyOut.getText().toString();
            remarks = "";
            input_id = String.valueOf(spHerbicideType.getSelectedItemPosition());
            input_quantity = etHerbicideQuantity.getText().toString();

            if (!"".equals(activity_date) && !"".equals(family_hours) && !"".equals(hired_hours) && !"".equals(money_out) && "".equals(remarks) || !"".equals(spray_method) && input_id != null && !"".equals(input_quantity)) {
                db.addFarmAssMedium(new FarmAssFormsMedium(pid, FORM_TYPE_ID, "none", activity_date, family_hours, hired_hours, money_out, input_id, input_quantity, spray_method, user_id, collect_date, String.valueOf(lat), String.valueOf(longt), cid));

                startActivity(next);
            } else {
                Toast.makeText(getApplicationContext(), "Fill in all the details", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class MyData {

        String spinnerText;
        String value;

        public MyData(String spinnerText, String value) {
            this.spinnerText = spinnerText;
            this.value = value;
        }

        public String getSpinnerText() {
            return spinnerText;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
            return spinnerText;
        }
    }
}
