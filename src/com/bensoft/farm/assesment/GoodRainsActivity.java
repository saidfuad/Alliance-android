/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.bensoft.entities.PlantingRains;
import static com.bensoft.farm.assesment.GoodRainsActivity.getPrefrence;
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
public class GoodRainsActivity extends Activity implements OnClickListener {

    private EditText etRainDate;
    private Button bSave;
    private Button bCancel;
    private String user_id;
    private String cid;
    private String pid;
    private SimpleDateFormat sdf;
    private Date d1;
    private GPSTracker gpsTracker;
    private double lat;
    private double longt;
    private DatabaseHandler db;
    private ConnectionDetector cd;
    private Intent newf;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_good_rains);
        initView();
        initListeners();
        initData();
    }

    private void initView() {
        etRainDate = (EditText) findViewById(R.id.etRainDate);
        bSave = (Button) findViewById(R.id.bSave);
        bCancel = (Button) findViewById(R.id.bCancel);
    }

    private void initListeners() {
        bSave.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }

    @Override
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
            String rain_date = etRainDate.getText().toString();
            if (rain_date != "") {
                db.addPlantingRains(new PlantingRains(pid, rain_date, collect_date, user_id, lat, longt, cid));

                startActivity(newf);
            }
        } else if (v == bCancel) {
            // Handle clicks for bCancel
            startActivity(newf);
        }
    }

    private void initData() {
        cd = new ConnectionDetector(GoodRainsActivity.this);
        db = new DatabaseHandler(GoodRainsActivity.this);
        pid = getPrefrence(GoodRainsActivity.this, "pid");
        user_id = getPrefrence(GoodRainsActivity.this, "user_id");
        cid = getPrefrence(GoodRainsActivity.this, "cid");
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //GPS
        gpsTracker = new GPSTracker(getApplicationContext());
        //
        newf = new Intent(getApplicationContext(), AllFarmAssesmentActivity.class);
        newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }
}
