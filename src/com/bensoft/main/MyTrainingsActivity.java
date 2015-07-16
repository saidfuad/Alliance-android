package com.bensoft.main;

import com.bensoft.utils.DatabaseHandler;
import com.bensoft.entities.TrainingType;
import com.bensoft.entities.Farm;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MyTrainingsActivity extends Activity {

    ListView listViewTrainings;
    Context context;
    private ListView listVIewTr;
    private String date_month_year;
    private DatabaseHandler db;
    private ArrayAdapter<MyData> dataAdapter;
    private MyData[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trainings);
        context = this;
        //get the ListView Reference
        listVIewTr = (ListView) findViewById(R.id.listViewTrains);

        //arrayColumns is the column name in your cursor where you're getting the data  
        // here we are displaying  SMSsender Number i.e. address and SMSBody i.e. body
        String[] arrayColumns = new String[]{"address", "body"};
        //arrayViewID contains the id of textViews
        // you can add more Views as per Requirement
        // textViewSMSSender is connected to "address" of arrayColumns
        // textViewMessageBody is connected to "body"of arrayColumns
        int[] arrayViewIDs = new int[]{R.id.textViewActualTrain};
        Intent gett = getIntent();
        db = new DatabaseHandler(getApplicationContext());
        date_month_year = gett.getStringExtra("date");
        List<TrainingType> train_list = db.getMyTrainings(date_month_year);
        items = new MyData[train_list.size()];
        int i = 0;
        for (TrainingType c : train_list) {
            if (i < train_list.size()) {
                items[i] = new MyData(c.getTType(), c.getTID());
                Log.i("Mydata:", items[i].getValue());
            }
            String log = "TId: " + c.getTID() + " ,Training type: " + c.getTType();
            Log.e(" ", log);
            i++;
        }

        dataAdapter = new ArrayAdapter<MyData>(getBaseContext(),
                R.layout.list_item_my_training);
        dataAdapter.addAll(items);

        listVIewTr.setAdapter(dataAdapter);

        // To handle the click on List View Item
        listVIewTr.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                List<Farm> train_location_list = db.getMyTrainingLocations(items[position].getValue());
                // Show The Dialog with Selected SMS
                AlertDialog dialog = new AlertDialog.Builder(context).create();
                dialog.setTitle("Details");
                dialog.setIcon(android.R.drawable.ic_dialog_info);
                dialog.setMessage("Location: " + train_location_list.get(0).getFarmName()
                        +" Latitude: "+train_location_list.get(0).getLat()+" Longitude: "+train_location_list.get(0).getLongt());
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                return;
                            }
                        });
                dialog.show();
            }

        });
    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    private class MyData {

        String text;
        String value;

        public MyData(String text, String value) {
            this.text = text;
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
            return text;
        }
    }
}
