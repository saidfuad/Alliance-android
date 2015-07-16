/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.main;

import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.Config;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Benson
 */
public class UpdateFarmEstimateActivity extends Activity {

    private String pid;
    private EditText etNewFarmArea;
    private Button bUpdateFarmArea;
    private ConnectionDetector cd;
    private String cid;
     private String new_area;
    private String user_id;
    private DatabaseHandler db;
    private String current_area;
    private TextView tvCurrentArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //To change body of generated methods, choose Tools | Templates.
        setContentView(R.layout.activity_update_farm);
        initView();
        initListeners();
        initData();
    }

    private void initData() {
        pid = getPreference(getApplicationContext(), "pid");
        cid = getPreference(getApplicationContext(), "cid");
        user_id = getPreference(getApplicationContext(), "user_id");
        cd = new ConnectionDetector(getApplicationContext());
        db = new DatabaseHandler(getApplicationContext());
        getCurrentStoredArea();
    }

    private String getPreference(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    private void initListeners() {
        bUpdateFarmArea.setOnClickListener(new OnClickListener() {
           

            public void onClick(View arg0) {
                new_area = etNewFarmArea.getText().toString();

                if (new_area.length() > 0) {
                    if(cd.isConnectingToInternet()){
                    /*upload to server*/
                        new UploadNewFarmArea().execute();
                    }else{
                    /*save offline*/
                        
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Fill in the Farm Area", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        etNewFarmArea = (EditText) findViewById(R.id.etNewFarmArea);
        bUpdateFarmArea = (Button) findViewById(R.id.bUpdateFarmArea);
        tvCurrentArea = (TextView) findViewById(R.id.tvCurrentArea);
        try{
        tvCurrentArea.setText(current_area);
        }catch(Exception ex){
        Toast.makeText(getApplicationContext(), "Download Data Farm Data is", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentStoredArea() {
        current_area = db.getFarmArea(pid);
    }

    private class UploadNewFarmArea extends AsyncTask{
        private String result2;

        public UploadNewFarmArea() {
        }

        @Override
        protected Object doInBackground(Object... arg0) {
            if(cd.isConnectingToInternet()){
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.UPDATE_FARM_AREA);
                    HttpPost httppost = new HttpPost(Config.UPDATE_FARM_AREA);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("farm_id", pid));
                    nameValuePairs.add(new BasicNameValuePair("farm_area", new_area));
                    nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    // finish();
                }

                result2 = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result2 = sb.toString();
                    Log.e("Update Farm Area Server:", result2);
                } catch (Exception e) {
                    Log.e("Ex Farm Area: ", e.toString());
                }
                
            } else {
                Log.i("Update Farms", "No Connection to Host Computer!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result); //To change body of generated methods, choose Tools | Templates.
            showAlert(result2);
        }
        
        
    }
      /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       
                        Intent newf = new Intent(getApplicationContext(), DashBoardActivity.class);
                        newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(newf);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
