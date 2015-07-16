package com.bensoft.main;

import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.Config;
import com.bensoft.entities.FarmerTime;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class TransparentActivity extends Activity {

    private String fid;
    private Bitmap famerPhoto;
    private ImageView imageView2;
    private Button ok;
    private TextView time;
    private TextView famerID;
    private String time2;
    private String clean_time;
    private String id;
    private String ftime;
    private String spec_fid;
    private String f_time;
    private String TAG = "TransParentActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);

        initData();
        initView();


    }

    private void initData() {
        Intent i = getIntent();
        fid = i.getStringExtra("fid");
        time2 = i.getStringExtra("time");
        f_time = i.getStringExtra("f_time");
        spec_fid = i.getStringExtra("spec_fid");
        Log.i(TAG,fid+" "+time2+" "+f_time+" "+spec_fid);
        try {
            famerPhoto = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/farmer_details/" + fid + ".jpg");
            // imageView2.setImageBitmap(famerPhoto);
        } catch (Exception ex) {
            Log.e("Image Ex: ", ex.getMessage());
        }
    }

    private void initView() {

        AlertDialog.Builder alertadd = new AlertDialog.Builder(TransparentActivity.this);
        alertadd.setTitle("Attendance");

        LayoutInflater factory = LayoutInflater.from(TransparentActivity.this);
        final View view = factory.inflate(R.layout.dialog_main, null);
        try {
            ImageView image = (ImageView) view.findViewById(R.id.imageView);
            image.setImageBitmap(famerPhoto);
        } catch (Exception ex) {
            Log.e("Image Ex2: ", ex.getMessage());
        }
        TextView text = (TextView) view.findViewById(R.id.tvFID);
        text.setText("Farmer ID: " + fid);
        TextView text2 = (TextView) view.findViewById(R.id.tvTime);
        text2.setText(time2);

        alertadd.setView(view);
        alertadd.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                if (cd.isConnectingToInternet()) {

                    new uploadFarmerTimes(spec_fid, f_time).execute();
                } else {
                    //insert data into database
                    db.addFarmerTimes(new FarmerTime(spec_fid, f_time, getPrefrence(getApplicationContext(), "user_id"), getPrefrence(getApplicationContext(), "cid")));

                    List<FarmerTime> allFarmerTimes = db.getAllFarmerTimes();
                    for (FarmerTime cn : allFarmerTimes) {
                        //sample farmers
                        String log = "FID: " + cn.getFID() + " ,FTIME: " + cn.getFTime();
                        // Writing Farmers to log
                        Log.e("Famrmer times: ", log);
                        //upload to server
                    }
                }
                finish();
            }
        });
        alertadd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {


                finish();
            }
        });
        alertadd.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transparent, menu);
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
            ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
            if (cd.isConnectingToInternet() == true) {
                // if (count > 0) {


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
                    entity.addPart("cid", new StringBody(getPrefrence(getApplicationContext(),"cid")));
                    httppost.setEntity(entity);

                    // Making server call
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity r_entity = response.getEntity();

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        // Server response
                        responseString = EntityUtils.toString(r_entity);
                    } else {
                        responseString = EntityUtils.toString(r_entity);
                    }

                    // responseString = e.toString();
                } catch (Exception e) {
                    // responseString = e.toString();
                }

                //   return responseString;
                try {
                    Log.e("Server response:", responseString+f_date_time+fid);
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
    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }
}
