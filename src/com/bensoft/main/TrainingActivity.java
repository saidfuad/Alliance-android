package com.bensoft.main;

import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.ExpandableListAdapter;
import com.bensoft.utils.MyData;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.Config;
import com.bensoft.entities.FarmerTime;
import com.bensoft.entities.Training2;
import com.bensoft.entities.Training;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artifex.mupdfdemo.MuPDFActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by user on 1/26/2015.
 */
public class TrainingActivity extends Activity {
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().toString();
    private ArrayAdapter<MyData> dataAdapter1;
    private MyData[] items;
    private Spinner trains;

    //private ListView trainings;
    private Button stopTrainAttendance;
    private String farmer_id;
    private ImageView farmer_pic;
    private ArrayList<String> train_id_list;
    private ArrayList<String> train_date_list;
    private ArrayList<String> train_name_list;
    private String cid;
    private String fid2;
    private String fname2;
    private String lname2;
    private TextView tvname, tvfid, tvarea, tvperi;
    private TextView farmer_details;
    private String responseString1;
    private ExpandableListView expListView;
    private ExpandableListAdapter listAdapter;
    private ArrayList<MyData> listDataHeader;
    private HashMap<MyData, List<MyData>> listDataChild;
    private MyData[] items2;
    private List<Training> trainingList;
    private DatabaseHandler db;
    private ExpandableListView elv;
    private TextView trainings;
    private Cursor childCursor;
    String[] childValue;
    private int count = 0;
    private List<String> tidList, startTimeList, stopTimeList, dateList;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private Cursor mGroupsCursor;
    private MyExpandableListAdapter mAdapter;
    private ArrayList<String> childValues;
    private String tid;
    private Button bStart;
    private Button bStop;
    private int _childPosition;
    private ArrayList<String> childIds;
    private LinearLayout l1;
    private String endTime;
    private int firstVisiblePosition;
    private boolean[] groupExpandedArray;
    private TextView train_type;
    private List<String> clickedArray;
    private LinearLayout vwParentRow;
    private int x;
    private DigitalClock timer;
    private boolean running;
    private MediaPlayer ok;
    private MediaPlayer read_error;
    private ConnectionDetector cd;
    private String fid;
    private String f_time;
    private String f_date;
    private ArrayList<String> swipe_count;
    private Button startTrainAttendance;
    private AlertDialog.Builder search;
    private boolean match;
    private int count2 = 0;
    private String spec_fid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        Intent intent = getIntent();
        clickedArray = null;
        //farmer_id = intent.getStringExtra("farmer_id");
        cid = getPrefrence(getApplicationContext(), "cid");
        //Bitmap potrait = BitmapFactory.decodeFile(SD_CARD_PATH + "/farmer_details/" + farmer_id + ".jpg");
        //farmer_pic.setImageBitmap(potrait);
        db = new DatabaseHandler(TrainingActivity.this);
        //tid,train_start_time,train_date,cid
        initView();
        cd = new ConnectionDetector(TrainingActivity.this);

        tidList = new ArrayList<String>();
        startTimeList = new ArrayList<String>();
        stopTimeList = new ArrayList<String>();
        dateList = new ArrayList<String>();
        fillData();
        initListeners();
        timer.setVisibility(View.VISIBLE);
        startService2();
        showAlert("You Can Now Start Taking Attendace");
        swipe_count = new ArrayList<String>();
    }

    private void startService2() {
        running = true;
        Log.e("Starting Service", "");
    }

    private void initView() {
        elv = (ExpandableListView) TrainingActivity.this.findViewById(R.id.lvExp);
        trainings = (TextView) findViewById(R.id.lblListItem);
        timer = (DigitalClock) findViewById(R.id.digitalClock);
        stopTrainAttendance = (Button) findViewById(R.id.bStopTrainAtendance);


    }

    public String getChildValue(int childPosition) {
        childValues = new ArrayList<String>();
        childIds = new ArrayList<String>();
        childValues.clear();
        if (childCursor.moveToFirst()) {
            do {
                //training.setTID(childCursor.getString(childCursor.getColumnIndexOrThrow("train_type")));
                String cv = childCursor.getString(childCursor.getColumnIndexOrThrow("tid"));

                childValues.add(cv);
            } while (childCursor.moveToNext());
        }
        childCursor.moveToFirst();
        Log.e("Child Value: ", childValues.get(childPosition));
        return childValues.get(childPosition);
    }


    private void initListeners() {

        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(final ExpandableListView parent, final View v,
                                        final int groupPosition, int childPosition, long id) {
                // Your child click code here

                _childPosition = childPosition;
                //if(childPosition == null){
                vwParentRow = (LinearLayout) v;
                train_type = (TextView) vwParentRow.getChildAt(0);
                try {
                    if (clickedArray == null) {
                        clickedArray = new ArrayList<String>(childCursor.getCount());
                    }
                } catch (Exception e) {
                }

                Log.e("ID2: ", String.valueOf(childPosition));
                if (childPosition > -1) {
                    boolean isClicked = false;
                    for (int i = 0; i < clickedArray.size(); i++) {
                        if (clickedArray.get(i) == Integer.toString(childPosition)) {
                            isClicked = true;
                        }
                    }

                        tid = getChildValue(childPosition);
                        showAlertStartLesson("Do You Want To Start " + train_type.getText() + " Lesson?", getChildValue(childPosition));

                }
              /*  bStart = (Button) v.findViewById(R.id.bStart);
                bStop = (Button) v.findViewById(R.id.bStop);
                bStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Clicked: ", "Start");
                        bStart.setTag(childPosition);
                        Log.e("ChildPosition: ", String.valueOf(childPosition));
                        // bStart.setEnabled(false);
                        //get the row the clicked button is in
                        bStart.setEnabled(false);
                        train_type.setEnabled(false);

                        String tid = getChildValue(childPosition);
                        //get current time and save child value in database
                        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        timeFormat = new SimpleDateFormat("HH:mm:ss");

                        Date date = new Date();

                        String train_date = dateFormat.format(date);
                        String train_start_time = timeFormat.format(date);

                        Log.e("Date: ", train_date);
                        Log.e("Time: ", train_start_time);

                        tidList.add(tid);
                        dateList.add(train_date);
                        startTimeList.add(train_start_time);

                    }
                });
                bStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Clicked: ", "Stop");
                        // tid = getChildValue(childPosition);
                        //getChildValue(childPosition);
                        //crazyMethod();
                        Date date = new Date();
                        endTime = timeFormat.format(date);
                        stopTimeList.add(endTime);
                        bStop.setEnabled(false);
                    }
                });*/

                return true;
            }
        });

        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {


            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Your group click code here
                if (elv.isGroupExpanded(groupPosition)) {
                    int numberOfGroups = mAdapter.getGroupCount();
                    groupExpandedArray = new boolean[numberOfGroups];
                    for (int i = 0; i < numberOfGroups; i++) {
                        groupExpandedArray[i] = elv.isGroupExpanded(i);
                    }
                    elv.collapseGroup(groupPosition);
                } else {
                    elv.expandGroup(groupPosition);
                }
                return true;
            }
        });


        stopTrainAttendance.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //stop timer
                //timer.setVisibility(View.INVISIBLE);
                stopService2();
                // startTrainAttendance.setEnabled(true);

            }
        });

    }

    private void stopService2() {

        running = false;
        showAlert3("Training Attendance Stoppped!");
        Log.e("Stoping Service", "");
    }


    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    private void fillData() {

        mGroupsCursor = db.fetchGroup();
        TrainingActivity.this.startManagingCursor(mGroupsCursor);
        mGroupsCursor.moveToFirst();
        Log.e("Error: ", "");
        elv = (ExpandableListView) TrainingActivity.this.findViewById(R.id.lvExp);
        mAdapter = new MyExpandableListAdapter(TrainingActivity.this,
                mGroupsCursor,
                R.layout.list_group,                     // Your row layout for a group
                new String[]{"train_cat", "_id"},                      // Field(s) to use from group cursor
                new int[]{R.id.lblListHeader},                 // Widget ids to put group data into
                R.layout.list_item,                 // Your row layout for a child
                new String[]{"train_type", "_id", "tid"},  // Field(s) to use from child cursors
                new int[]{R.id.lblListItem});          // Widget ids to put child data into
        elv.setAdapter(mAdapter);                       // set the list adapter.

    }

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {
        public MyExpandableListAdapter(Context context, Cursor cursor, int groupLayout,
                                       String[] groupFrom, int[] groupTo, int childLayout,
                                       String[] childrenFrom, int[] childrenTo) {
            super(context, cursor, groupLayout,
                    groupFrom, groupTo, childLayout,
                    childrenFrom, childrenTo);

        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            String idColumn = groupCursor.getString(groupCursor.getColumnIndex("tcatid"));
            childCursor = db.fetchTrainCats(idColumn);
            Log.e("Error2: ", "");
            childCursor.moveToFirst();
            return childCursor;
        }

    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrainingActivity.this);
        builder.setMessage(message).setTitle("Attendance")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void showAlert3(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TrainingActivity.this);
        builder.setMessage(message).setTitle("Attendance")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //kill process
                        Intent newf = new Intent(getApplicationContext(), DashBoardActivity.class);
                        newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(newf);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showAlertStartLesson(String message, final String id1) {
        final String id2 = id1;
        AlertDialog.Builder builder = new AlertDialog.Builder(TrainingActivity.this);
        builder.setMessage(message).setTitle("START LESSON")
                .setCancelable(true).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e("ID: ", String.valueOf(id2));
                        // train_type.setBackgroundColor(getResources().getColor(R.color.disabled));
                        // clickedArray.add(String.valueOf(id2));
                        //update on database
                        //db.updateTrainingStatus(String.valueOf(id2));
                        // vwParentRow.removeView(train_type);
                        // db.deleteTraining(id2);

                        List<Training2> stuff = db.getAllTrainings();
                        for (Training cn : stuff) {
                            //sample farmers
                            String log = "TID: " + cn.getTID() + " ,TTYPE: " + cn.getTType();
                            // Writing Farmers to log
                            Log.e("TSTATUS LOG: ", log);
                            //upload to server
                        }

                        Log.e("Future file path: ", train_type.getText().toString() + ".pdf");
                        Uri uri;
                        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                        String ip = SP.getString("ip", "NULL");
                        String language = SP.getString("language", "NULL");
                        if (language.equals("1")) {
                            uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/train_mats/" + "Coach-the-Farmer-swa" + ".pdf");
                        } else {
                            uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/train_mats/" + "Coach-the-Farmer-en" + ".pdf");
                        }
                        Log.e("Path: ", Environment.getExternalStorageDirectory().getAbsolutePath() + "/train_mats/" + train_type.getText() + ".pdf");
                        Intent tab = new Intent(getApplicationContext(), MuPDFActivity.class);


                        timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Date date = new Date();

                        String train_start_time = timeFormat.format(date);
                        tab.setAction(Intent.ACTION_VIEW);
                        tab.setData(uri);
                        tab.putExtra("normal", "normal2");
                        tab.putExtra("tid", tid);
                        tab.putExtra("train_time", train_start_time);
                        // newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(tab);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class uploadAssignedTrainings extends AsyncTask<Void, Void, Void> {

        private String _tdate;
        private String _tname;

        /*  private String _tid;

          public uploadAssignedTrainings(String _tid,String _tname,String _tdate){
              this._tid = _tid;
              this._tdate = _tdate;
              this._tname = _tname;
          }*/
        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FARM_ASSIGN_TRAININGS_URL);

            String responseString;
            for (int i = 0; i < tidList.size(); i++) {
                try {
                    AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                            new AndroidMultiPartEntity.ProgressListener() {

                                @Override
                                public void transferred(long num) {
                                    //  publishProgress((int) ((num / (float) totalSize) * 100));
                                }
                            });

                    // Extra parameters if you want to pass to server
                    entity.addPart("tid", new StringBody(tidList.get(i)));
                    entity.addPart("train_date", new StringBody(dateList.get(i)));
                    entity.addPart("train_start_time", new StringBody(startTimeList.get(i)));
                    entity.addPart("train_stop_time", new StringBody(stopTimeList.get(i)));
                    entity.addPart("cid", new StringBody(cid));
                    //entity.addPart("date_for_train", new StringBody(now);


                    long totalSize = entity.getContentLength();
                    httppost.setEntity(entity);

                    // Making server call
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString1 = EntityUtils.toString(r_entity);
                    } else {
                        responseString1 = "Error occurred! Http Status Code: "
                                + statusCode;
                    }

                } catch (ClientProtocolException e) {
                    responseString = e.toString();
                } catch (IOException e) {
                    responseString1 = e.toString();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Server response: " + responseString1, Toast.LENGTH_SHORT).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            showAlert(responseString1);
        }
    }

    @Override
    protected void onPause() {
        Log.e("onPause", "");
        int numberOfGroups = mAdapter.getGroupCount();
        groupExpandedArray = new boolean[numberOfGroups];
        for (int i = 0; i < numberOfGroups; i++) {
            groupExpandedArray[i] = elv.isGroupExpanded(i);
        }
        firstVisiblePosition = elv.getFirstVisiblePosition();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e("onResume", "");
        try {
            for (int i = 0; i < groupExpandedArray.length; i++) {
                if (groupExpandedArray[i] == true)
                    elv.expandGroup(i);
            }
            elv.setSelection(firstVisiblePosition);
        } catch (Exception e) {
        }
        swipe_count.clear();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.e("onRestart", "");
        try {
            for (int i = 0; i < groupExpandedArray.length; i++) {
                if (groupExpandedArray[i] == true)
                    elv.expandGroup(i);
            }
            elv.setSelection(firstVisiblePosition);
        } catch (Exception e) {
        }
         swipe_count.clear();
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.e("onStart", "");
        try {
            for (int i = 0; i < groupExpandedArray.length; i++) {
                if (groupExpandedArray[i] == true)
                    elv.expandGroup(i);
            }
            elv.setSelection(firstVisiblePosition);
        } catch (Exception e) {
        }
        super.onRestart();
    }
    
     

    public static Map<String, ?> getApprovedFarmer(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "approvedfarmers", ctx.MODE_PRIVATE);
        Map<String, ?> result = pref.getAll();
        return result;
    }

    public String getApprovedFarmerID(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "approvedfarmers", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_farmer_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                //launch dialog to search for farmer manually
                timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date date = new Date();

                f_time = timeFormat.format(date);

                search = new AlertDialog.Builder(this);
                search.setTitle("Attendance");
                search.setMessage("Search for Farmer Using ID");

// Set an EditText view to get user input
                final EditText search_value = new EditText(this);
                search.setView(search_value);

                search.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final String found = search_value.getText().toString();
                        // Do something with value!
                        //loop through farmers list
                        if (found.length() == 15) {

                            Map<String, ?> allEntries = getApprovedFarmer(getApplicationContext());

                            for (final Map.Entry<String, ?> entry : allEntries.entrySet()) {
                                Log.e("map values", entry.getKey() + ": " + entry.getValue().toString());
                                String syslist = entry.getKey();
                                if (found.trim().equals(syslist.trim())) {
                                    match = true;
                                    spec_fid = entry.getValue().toString();
                                    Log.i("Found: " + found, "List ID: " + syslist);

                                }
                            }
                            if (match) {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        //  showAlert("Farmer Found \n" + found);
                                        swipe_count.add(found);

                                        int i = 0;
                                        for (i = 0; i < swipe_count.size(); i++) {
                                            if (swipe_count.get(i).trim() == found.trim()) {
                                                count2++;
                                                Log.e("mmmm: " + swipe_count.get(i), "mmmm" + found.trim());
                                            }
                                        }
                                        Log.i("List Size: ", String.valueOf(swipe_count.size()));
                                        Log.i("Match Count: ", String.valueOf(count2));
                                        List<FarmerTime> ft = null;
                                        if (count2 % 2 == 0) {
                                            // showAlert2("Time Out " + f_time + " \n" + fid3);
                                            //start a transparent activity
                                           // ft = db.getFarmerTimeID(spec_fid,f_time);
                                            Intent trans = new Intent(getApplicationContext(), TransparentActivity.class);
                                            trans.putExtra("time", "Time Out: " + f_time);
                                            trans.putExtra("f_time", f_time);
                                            trans.putExtra("spec_fid", spec_fid);
                                            trans.putExtra("fid",found);
                                            match =false;
                                            startActivity(trans);

                                        } else {
                                            // showAlert2("Time In " + f_time + " \n" + fid3);
                                            //ft = db.getFarmerTimeID(spec_fid,f_time);
                                            Intent trans = new Intent(getApplicationContext(), TransparentActivity.class);
                                            trans.putExtra("time", "Time In: " + f_time);
                                            trans.putExtra("f_time", f_time);
                                            trans.putExtra("spec_fid", spec_fid);
                                            trans.putExtra("fid",found);
                                            match =false;
                                            startActivity(trans);

                                        }

                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Intent trans = new Intent(getApplicationContext(), TransparentActivity.class);
                                        trans.putExtra("time", "Time In: " + f_time);
                                        trans.putExtra("fid", "Unknown");
                                        startActivity(trans);
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    //Toast.makeText(getApplicationContext(), "Farmer Not Found", Toast.LENGTH_LONG).show();
                                    //alert

                                    showAlert("Please Verify Farmer ID");
                                }
                            });
                        }
                    }
                });

                search.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                search.show();
                break;
        }
        return true;
    }

    private class uploadFarmerTimes extends AsyncTask {

        private String responseString;
        String _fid,f_date_time;

        public uploadFarmerTimes(String _fid,String f_date_time) {
            this._fid = _fid;
            this.f_date_time = f_date_time;

        }

        @Override
        protected Object doInBackground(Object[] params) {
            Log.e("Uploding farmer times", "");
            //check if database is empty
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());

            if (cd.isConnectingToInternet() == true) {
                // if (count > 0) {

                responseString = null;

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(Config.FARMER_TIME_UPLOAD_URL);

                try {
                    AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                            new AndroidMultiPartEntity.ProgressListener() {

                                @Override
                                public void transferred(long num) {
                                    //  publishProgress((int) ((num / (float) totalSize) * 100));
                                }
                            });


                    // Extra parameters if you want to pass to server
                    entity.addPart("fid", new StringBody(_fid));
                    entity.addPart("f_date_time", new StringBody(f_date_time));
                    entity.addPart("user_id", new StringBody(getPrefrence(getApplicationContext(),"user_id")));
                    entity.addPart("cid", new StringBody(cid));
                    httppost.setEntity(entity);

                    // Making server call
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString = EntityUtils.toString(r_entity);
                    } else {
                        responseString = "Error occurred! Http Status Code: "
                                + statusCode;
                    }

                    // responseString = e.toString();
                } catch (Exception e) {
                    // responseString = e.toString();
                }

                //   return responseString;
                try {
                    Log.e("Server response:", responseString);
                } catch (Exception ex) {
                }
                // }
                //delete all info from tables


                //clear table farmers


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

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Server response: " + responseString, Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}



