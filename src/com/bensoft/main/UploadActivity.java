package com.bensoft.main;

/**
 * Created by user on 12/27/2014.
 */
import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.Config;
import com.bensoft.utils.GPSTracker;
import com.bensoft.entities.Farmers;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bensoft.main.AndroidMultiPartEntity.ProgressListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UploadActivity extends Activity {
    // LogCat tag
    //  private static final String TAG = MainActivity.class.getSimpleName();

    long totalSize = 0;
    TextView tFName, tLName, tGender, tId, tPhone, tPost, tEmail, tCountry, tRegion, tDistrict, tWard, tVillage, tSubVillage, tEstFarmArea;
    String fname, lname, id_no, phone, district, ward, email, village, subvillage, post, gender, country, region, estfarmarea;
    private ProgressBar progressBar;
    private String filePat = null;
    // private String fingerPath = null;
    private TextView txtPercentage;
    private ImageView imgPreview;
    private VideoView vidPreview;
    private Button btnUpload;
    private String imagePath;
    private double lat, longt;
    private GPSTracker gpsTracker;
    private String slat, slongt;
    private TextView tLat, tLongt;
    private String cid;
    private String reg_date;
    private SimpleDateFormat sdf;
    private Date d1;
    private String vid;
    private String svid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        tFName = (TextView) findViewById(R.id.tFName);
        tLName = (TextView) findViewById(R.id.tLName);
        tGender = (TextView) findViewById(R.id.tGender);
        tId = (TextView) findViewById(R.id.tId);
        tPhone = (TextView) findViewById(R.id.tPhone);
        tEmail = (TextView) findViewById(R.id.tEmail);
        tPost = (TextView) findViewById(R.id.tPost);
        tCountry = (TextView) findViewById(R.id.tCountry);
        tRegion = (TextView) findViewById(R.id.tRegion);
        tDistrict = (TextView) findViewById(R.id.tDistrict);
        tWard = (TextView) findViewById(R.id.tWard);
        tVillage = (TextView) findViewById(R.id.tVillage);
        tSubVillage = (TextView) findViewById(R.id.tSubVillage);
        tEstFarmArea = (TextView) findViewById(R.id.tEstFarmArea);
        tLat = (TextView) findViewById(R.id.tvLat);
        tLongt = (TextView) findViewById(R.id.tvLongt);
        //GPS
        gpsTracker = new GPSTracker(getApplicationContext());
        // Receiving the data from previous activity
        // image or video path that is captured in fingerprint activity
        Intent intent = getIntent();

        //fingerPath = getPrefrence(getApplicationContext(),"fingerPath");
        imagePath = getPrefrence(getApplicationContext(), "imagePath");
        cid = getPrefrence(getApplicationContext(), "cid");
        //  Log.d("filePath3: ", fingerPath.toString());
        //Bitmap fingerPath= intent.getParcelableExtra("photo");
        fname = intent.getStringExtra("fname");
        lname = intent.getStringExtra("lname");
        gender = intent.getStringExtra("gender");
        id_no = intent.getStringExtra("id_no");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        post = intent.getStringExtra("post");
        reg_date = intent.getStringExtra("reg_date");
        village = intent.getStringExtra("village");
        vid = intent.getStringExtra("vid");
        subvillage = intent.getStringExtra("subvillage");
        svid = intent.getStringExtra("svid");
        estfarmarea = intent.getStringExtra("estfarmarea");
        //lat = intent.getStringExtra("lat");
        //longt = intent.getStringExtra("longt");
        //getGPS position

        if (gpsTracker.canGetLocation) {
            // do {
            lat = gpsTracker.getLatitude();
            longt = gpsTracker.getLongitude();
            // } while (lat == 0.0 || longt == 0.0);
            slat = String.valueOf(lat);
            slongt = String.valueOf(longt);

            tLat.setText(slat);
            tLongt.setText(slongt);

            Log.e("lat: ", slat);
            Log.e("long: ", slongt);

        } else {
            //turn on gps
            gpsTracker.showSettingsAlert(UploadActivity.this);
            // do {
            lat = gpsTracker.getLatitude();
            longt = gpsTracker.getLongitude();
            // } while (lat == 0.0 || longt == 0.0);
            slat = String.valueOf(lat);
            slongt = String.valueOf(longt);

            tLat.setText(slat);
            tLongt.setText(slongt);

            Log.e("lat: ", slat);
            Log.e("long: ", slongt);

        }

        //show Other farmer details
        tFName.setText(fname);
        tLName.setText(lname);
        tGender.setText(gender);
        tId.setText(id_no);
        tPhone.setText(phone);
        tEmail.setText(email);
        tPost.setText(post);
        tVillage.setText(village);
        tSubVillage.setText(subvillage);

        // boolean flag to identify the media type, image or video
        boolean isImage = intent.getBooleanExtra("isImage", true);

        if (imagePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // uploading the file to server
                ConnectionDetector cdd = new ConnectionDetector(getApplicationContext());
                if (cdd.isConnectingToInternet()) {
                    new UploadFileToServer().execute();
                } else {

                    if (gpsTracker.canGetLocation) {
                        // do {
                        lat = gpsTracker.getLatitude();
                        longt = gpsTracker.getLongitude();
                        // }while(lat==0.0 || longt==0.0);

                        if (fname.length() == 0 || lname.length() == 0 || id_no.length() == 0 || phone.length() == 0 || email.length() == 0 || post.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                //save to offline database
                                Intent i = new Intent(getApplicationContext(), UploadActivity.class);
                                imagePath = getPrefrence(getApplicationContext(), "imagePath");
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                // Inserting Farmers
                                Log.e("Insert: ", "Inserting ..");
                                //add farmers
                                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                d1 = new Date();

                                db.addFarmer(new Farmers(fname, lname, gender, id_no, phone, email, post, vid, svid, imagePath, String.valueOf(lat), String.valueOf(longt),estfarmarea, sdf.format(d1), cid));
                               // Toast.makeText(getApplicationContext(), "Data saved offline", Toast.LENGTH_SHORT).show();

                                // Reading all Farmers
                                Log.e("Reading: ", "Reading all farmers..");
                                List<Farmers> farmers = db.getAllFarmers();
                                int count = db.getFarmersCount();
                                Log.e("Count: ", String.valueOf(count));
                                for (Farmers cn : farmers) {
                                    String log = "Id: " + cn.getID() + " ,FName: " + cn.getFName() + " ,LName: " + cn.getLName() + " ,Gender: " + cn.getGender() + " ,ID number: " + cn.getIDNO() + " ,Phone: " + cn.getPhoneNumber() + " ,Email: " + cn.getEmail() + " ,Post: " + cn.getPost()
                                            + " ,ImagePath: " + cn.getPicPath() + " Date: " + cn.getRegDate() + " Lat: " + cn.getLat() + "Long: " + cn.getLong();
                                    // Writing Farmers to log
                                    Log.i(" ", log);
                                }
                                db.close();
                                
                            } catch (Exception ex) {
                                Log.e("Exception:", ex.getMessage());
                            }
                        }

                        //
                        Log.e("lat: ", String.valueOf(lat));
                        Log.e("long: ", String.valueOf(longt));
                        Intent r = new Intent("android.location.GPS_ENABLED_CHANGE");
                        r.putExtra("enabled", false);
                        sendBroadcast(r);
                        gpsTracker.stopUsingGPS();
                        showAlert("Data Saved Offline");
                    } else {
                        //turn on gps
                        gpsTracker.showSettingsAlert(UploadActivity.this);
                        lat = gpsTracker.getLatitude();
                        longt = gpsTracker.getLongitude();

                        if (fname.length() == 0 || lname.length() == 0 || id_no.length() == 0 || phone.length() == 0 || email.length() == 0 || post.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                               //save to offline database
                                Intent i = new Intent(getApplicationContext(), UploadActivity.class);
                                imagePath = getPrefrence(getApplicationContext(), "imagePath");
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                // Inserting Farmers
                                Log.e("Insert: ", "Inserting ..");
                                //add farmers
                                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                d1 = new Date();

                                db.addFarmer(new Farmers(fname, lname, gender, id_no, phone, email, post, vid, svid, imagePath, String.valueOf(lat), String.valueOf(longt),estfarmarea, sdf.format(d1), cid));
                               // Toast.makeText(getApplicationContext(), "Data saved offline", Toast.LENGTH_SHORT).show();

                                // Reading all Farmers
                                Log.e("Reading: ", "Reading all farmers..");
                                List<Farmers> farmers = db.getAllFarmers();
                                int count = db.getFarmersCount();
                                Log.e("Count: ", String.valueOf(count));
                                for (Farmers cn : farmers) {
                                    String log = "Id: " + cn.getID() + " ,FName: " + cn.getFName() + " ,LName: " + cn.getLName() + " ,Gender: " + cn.getGender() + " ,ID number: " + cn.getIDNO() + " ,Phone: " + cn.getPhoneNumber() + " ,Email: " + cn.getEmail() + " ,Post: " + cn.getPost()
                                            + " ,ImagePath: " + cn.getPicPath() + " Date: " + cn.getRegDate() + " Lat: " + cn.getLat() + "Long: " + cn.getLong();
                                    // Writing Farmers to log
                                    Log.i(" ", log);
                                }
                                db.close();
                                //fingerPath = null;
                                //ToastUtil.showToast(getApplicationContext(), );
                               // Intent newf = new Intent(getApplicationContext(), DashBoardActivity.class);
                              //  newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               // startActivity(newf);
                               // showAlert("Data Saved Offline");
                            } catch (Exception e) {
                                Log.e("Exception:", e.getMessage());
                            }
                        }

                        Log.e("lat: ", String.valueOf(lat));
                        Log.e("long: ", String.valueOf(longt));
                        Intent r = new Intent("android.location.GPS_ENABLED_CHANGE");
                        r.putExtra("enabled", false);
                        sendBroadcast(r);
                        gpsTracker.stopUsingGPS();
                        showAlert("Data Saved Offline");

                    }
                }
            }
        });

    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    /**
     * Displaying captured image/video on the screen
     */
    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

            imgPreview.setImageBitmap(bitmap);
        } else {
            imgPreview.setVisibility(View.GONE);

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
                        //
                        fname = "";
                        lname = "";
                        gender = "";
                        id_no = "";
                        phone = "";
                        email = "";
                        post = "";
                        village = "";

                        Intent newf = new Intent(getApplicationContext(), DashBoardActivity.class);
                        newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(newf);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Uploading the file to server
     */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        String responseString = null;

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                // File sourceFile = new File(fingerPath);
                File sourceFile2 = new File(imagePath);
                // Adding files data to http body
                // entity.addPart("image2", new FileBody(sourceFile));
               entity.addPart("image", new FileBody(sourceFile2));
                // Extra parameters if you want to pass to server
                entity.addPart("fname", new StringBody(fname));
                entity.addPart("lname", new StringBody(lname));
                entity.addPart("gender", new StringBody(gender));
                entity.addPart("id_no", new StringBody(id_no));
                entity.addPart("phone", new StringBody(phone));
                entity.addPart("email", new StringBody(email));
                entity.addPart("post", new StringBody(post));
                entity.addPart("village", new StringBody(vid));
                entity.addPart("subvillage", new StringBody(svid));
                entity.addPart("estfarmarea", new StringBody(estfarmarea));
                entity.addPart("lat", new StringBody(slat));
                entity.addPart("longt", new StringBody(slongt));
                entity.addPart("reg_date", new StringBody(reg_date));
                entity.addPart("cid", new StringBody(cid));
                entity.addPart("user_id", new StringBody(getPrefrence(getApplicationContext(), "user_id")));
                totalSize = entity.getContentLength();
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

            } catch (Exception e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("SERVER: ", "Response from server: " + responseString);

            gpsTracker.stopUsingGPS();
            Intent r = new Intent("android.location.GPS_ENABLED_CHANGE");
            r.putExtra("enabled", false);
            sendBroadcast(r);
            // showing the server response in an alert dialog
            showAlert("SERVER \n" + result);

            // super.onPostExecute(result);
            //finish();
        }

    }

}
