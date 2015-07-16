/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment;

import com.bensoft.entities.FarmOtherCrops;
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
import android.widget.Spinner;
import com.bensoft.main.R;
import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.GPSTracker;
import com.bensoft.entities.OtherCrops;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Benson
 */
public class OtherCropsActivity extends Activity implements OnClickListener {

    private Spinner spFirstCrop;
    private Spinner spSecondCrop;
    private Spinner spThirdCrop;
    private Button bSave;
    private Button bCancel;
    private DatabaseHandler db;
    private ConnectionDetector cd;
    private String user_id;
    private String cid;
    private String pid;
    private String crop_id_1, crop_id_2, crop_id_3;
    private SimpleDateFormat sdf;
    private Date d1;
    private GPSTracker gpsTracker;
    private double lat;
    private double longt;
    private Intent newf;
    private ArrayAdapter<MyData> dataAdapter5;
    private MyData[] items5;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_other_crops_1);
        initView();
        initListeners();
        initData();
    }

    private void initView() {
        spFirstCrop = (Spinner) findViewById(R.id.spFirstCrop);
        spSecondCrop = (Spinner) findViewById(R.id.spSecondCrop);
        spThirdCrop = (Spinner) findViewById(R.id.spThirdCrop);
        bSave = (Button) findViewById(R.id.bSave);
        bCancel = (Button) findViewById(R.id.bCancel);
    }

    private void loadCrops() {
        db = new DatabaseHandler(getApplicationContext());
        List<OtherCrops> cropsList = db.allOtherCrops();
        dataAdapter5 = new ArrayAdapter<MyData>(getBaseContext(),
                android.R.layout.simple_spinner_item);
        items5 = new MyData[cropsList.size()];
        int i = 0;
        for (OtherCrops r : cropsList) {
            if (i < cropsList.size()) {
                items5[i] = new MyData(r.getCropName(), r.getCropName());
                Log.e("Mydata:", items5[i].getValue());
            }

            i++;
        }
        
        dataAdapter5.addAll(items5);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter5.notifyDataSetChanged();
        spFirstCrop.setAdapter(dataAdapter5);
        spSecondCrop.setAdapter(dataAdapter5);
        spThirdCrop.setAdapter(dataAdapter5);
        db.close();
    }
    private void initListeners() {
        bSave.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        spFirstCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyData d = items5[position];
                crop_id_1 = d.getValue();

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        spSecondCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyData d = items5[position];
                crop_id_2 = d.getValue();

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        spThirdCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyData d = items5[position];
                crop_id_3 = d.getValue();

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public void onClick(View v) {

        if (v == bSave) {
            // Handle clicks for bSave
            d1 = new Date();
            String collect_date = sdf.format(d1);

            if (gpsTracker.canGetLocation) {
                // do {
                lat = gpsTracker.getLatitude();
                longt = gpsTracker.getLongitude();
            }

            db.addFarmOtherCrops(new FarmOtherCrops(pid, crop_id_1, crop_id_2, crop_id_3, user_id, collect_date, String.valueOf(lat), String.valueOf(longt), cid));
            startActivity(newf);
        } else if (v == bCancel) {
            // Handle clicks for bCancel

            startActivity(newf);
        }
    }

    private void initData() {
        cd = new ConnectionDetector(OtherCropsActivity.this);
        db = new DatabaseHandler(OtherCropsActivity.this);
        pid = getPrefrence(OtherCropsActivity.this, "pid");
        user_id = getPrefrence(OtherCropsActivity.this, "user_id");
        cid = getPrefrence(OtherCropsActivity.this, "cid");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //GPS
        gpsTracker = new GPSTracker(getApplicationContext());
        //
        newf = new Intent(getApplicationContext(), AllFarmAssesmentActivity.class);
        newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //load crops
        loadCrops();
    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
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
