package com.bensoft.main;

import com.bensoft.utils.MyData;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.entities.KmlMetaData;
import com.bensoft.entities.Farm;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.bensoft.forms.AllFormsActivity;

import java.util.List;

public class PlotSelectionActitivty extends Activity {

    private Spinner sPlots;
    private Button next;
    private DatabaseHandler db;
    private MyData[] items;
    private ArrayAdapter<String> farmsAdapter;
    private String gen_id;
    private String s;
    private Intent to_which;
    private String to_which2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_selection_actitivty);

        initView();
        initData();
        initListeners();
        loadFarms(gen_id);
    }

    private void loadFarms(String _gen_id) {

        List<Farm> farmsList = db.getAllFarms2(_gen_id);
        if (farmsList.size() > 0) {
            farmsAdapter = new ArrayAdapter<String>(getBaseContext(),
                    android.R.layout.simple_spinner_item);
            items = new MyData[farmsList.size()];
            int i = 0;
            //for (int i = 0; i < countriesList.size(); i++) {
            for (Farm r : farmsList) {
                if (i < farmsList.size()) {
                    items[i] = new MyData(r.getFarmName(), r.getFarmID(), r.getFarmSize());
                    Log.e("Farms data:", items[i].getFarmSize());
                    farmsAdapter.add(items[i].getFarmName() + " Area: " + items[i].getFarmSize());
                }
                //dataAdapter2.add(new MyData(r.getRegionName(), r.getRegionID()));
                String log = "Farm Id: " + r.getFarmID() + " ,Farm name: " + r.getFarmName() + " ,Farm Size: " + r.getFarmSize();
                Log.e("Farms List: ", log);
                i++;
            }
            //}
            //  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
            //          android.R.layout.simple_spinner_item);
            //farmsAdapter.addAll(items);
            farmsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            farmsAdapter.notifyDataSetChanged();
            sPlots.setAdapter(farmsAdapter);
            next.setEnabled(true);
        } else {
            showAlert("This Farmer Has Not Registered any Farm");
            next.setEnabled(false);
        }
        db.close();

    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(final String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PlotSelectionActitivty.this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        //  }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void initListeners() {
        sPlots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyData d = items[position];
                s = d.getFarmID().toString();
                Log.i("S :: ",s);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (to_which2.equals("farm_update")) {
                    Intent update_farm_est = new Intent(getApplicationContext(), UpdateFarmEstimateActivity.class);
                    SaveFarmID(getApplicationContext(), "pid", s);
                    startActivity(update_farm_est);
                } else if (to_which2.equals("planimeter")) {
                    DatabaseHandler db2 = new DatabaseHandler(getApplicationContext());
                    db2.addKmlSupportData(new KmlMetaData(gen_id, getPrefrence(getApplicationContext(), "user_id"), getPrefrence(getApplicationContext(), "cid"),s));
                    List<KmlMetaData> kmlmetadata = db2.getAllKmlMeta(gen_id);
                    for (KmlMetaData cn : kmlmetadata) {
                        //sample farmers
                        String log = "FID: " + cn.getFID() + " ,USERID: " + cn.getUserID() + " ,Farm_id: " + cn.getFarmID();                        
                       Log.i("Farm Data: ",log);
                    }
                    Intent planimeter = getPackageManager().getLaunchIntentForPackage("com.vistechprojects.planimeter");
                    startActivity(planimeter);
                } else {
                    Intent forms = new Intent(getApplicationContext(), AllFormsActivity.class);
                    SaveFarmID(getApplicationContext(), "pid", s);
                    startActivity(forms);
                }
            }
        });
    }

    private void initData() {
        db = new DatabaseHandler(getApplicationContext());
        gen_id = getPreference(getApplicationContext(), "val_gen_id");
        to_which = getIntent();
        to_which2 = to_which.getStringExtra("to_which");
    }

    private String getPreference(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    public static void SaveFarmID(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("mypref", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }

    private void initView() {
        sPlots = (Spinner) findViewById(R.id.sPlots);
        next = (Button) findViewById(R.id.bNext);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plot_selection_actitivty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }
}
