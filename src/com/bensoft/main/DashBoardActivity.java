package com.bensoft.main;

import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.Config;
import com.bensoft.entities.FarmerTime;
import com.bensoft.entities.TrainingType;
import com.bensoft.entities.SignedDoc;
import com.bensoft.entities.InputsFromServer;
import com.bensoft.entities.KmlMetaData;
import com.bensoft.utils.GPSTracker;
import com.bensoft.utils.ImageAdapter;
import com.bensoft.entities.Farmers;
import com.bensoft.entities.Countries;
import com.bensoft.entities.Farm;
import com.bensoft.entities.Wards;
import com.bensoft.entities.Villages;
import com.bensoft.entities.SubVillages;
import com.bensoft.entities.Training2;
import com.bensoft.entities.Training;
import com.bensoft.entities.Regions;
import com.bensoft.entities.Districts;
import com.bensoft.entities.Assigns;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.artifex.mupdfdemo.ChoosePDFActivity;
import com.bensoft.entities.FarmAssFormsMajor;
import com.bensoft.entities.FarmAssFormsMedium;
import com.bensoft.farmersearch.ManualFarmerSearch;
import com.bensoft.utils.ToastUtil;
import com.bensoft.forms.AllFormsActivity;
import com.bensoft.forms.FingerFiveBack;
import com.bensoft.forms.FingerFourBack;
import com.bensoft.forms.FingerOneBack;
import com.bensoft.forms.FingerThreeBack;
import com.bensoft.forms.FingerTwoBack;
import com.bensoft.entities.FoliarFeed;
import com.bensoft.entities.Herbicides;
import com.bensoft.entities.OtherCrops;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashBoardActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().toString();
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int TAKE_PHOTO_CODE = 1;
    private static WindowManager mWindowManager;
    private static Camera mCamera;
    private Uri fileUri; // file url to store image/video
    private ImageButton news, map_out, farm_inputs, training, tmaterials, settings;
    private int count;
    private String imagePath;
    private long totalSize;
    private ConnectionDetector cd;
    private String fingerPath;
    private ProgressDialog dialog;
    private String cid;
    private DatabaseHandler db;
    private ImageButton allForms;
    private SimpleDateFormat dateFormat;
    private String saved_date;
    private Date d2;
    private String today;
    private ImageButton schedule;
    private GridView gridview;
    private File f2;

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Error: ", "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
    private String vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        initView();
        initListeners();
        new turnOnGps().execute();
        db = new DatabaseHandler(getApplicationContext());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        d2 = new Date();
        today = dateFormat.format(d2);
    }

    class turnOnGps extends AsyncTask {

        final GPSTracker gps = new GPSTracker(getApplication());

        @Override
        protected Object doInBackground(Object[] params) {

            if (!gps.isGPSEnabled) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gps.showSettingsAlert(DashBoardActivity.this);
                    }
                });

            }
            //  do {
            gps.getLatitude();
            gps.getLongitude();
            if (gps.getLatitude() > 0.0 & gps.getLongitude() > 0.0) {
                Log.e("Lat: ", String.valueOf(gps.getLatitude()));
                Log.e("Longt : ", String.valueOf(gps.getLongitude()));
            }
            /// }while(gps.getLongitude()==0.0||gps.getLatitude()==0.0);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (gps.getLatitude() == 0.0 && gps.getLongitude() == 0.0) {
                // new turnOnGps().execute();
            }
            super.onPostExecute(o);
        }
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.gridview);
        ImageAdapter image_adapter = new ImageAdapter(DashBoardActivity.this);
        gridview.setAdapter(image_adapter);
        dialog = new ProgressDialog(DashBoardActivity.this);

        cd = new ConnectionDetector(getApplicationContext());
        //get cid
        cid = getPrefrence(getApplicationContext(), "cid");
        vid = getPrefrence(getApplicationContext(), "vid");
        Log.e("CID: ", cid);

    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    public void initListeners() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                //  Toast.makeText(getApplicationContext(), "Clicked ", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        if (db.getVillageCount() > 0) {
                            captureImage();
                        } else {
                            showAlert("Download data first!");
                        }
                        break;
                    case 1:

                        Intent sign = new Intent(getApplicationContext(), SignDocActivity.class);
                        AudioManager am4 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        //  if (am4.isWiredHeadsetOn()) {
                        startActivity(sign);
                        // } else {
                        //     showJackAlert("Please Connect Card Reader!");
                        // }

                        break;
                    case 2:
                        // if (cd.isConnectingToInternet()) {
                        //  AudioManager am9 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        //  if (am2.isWiredHeadsetOn()) {
                        Intent mapi = new Intent(DashBoardActivity.this, ManualFarmerSearch.class);
                        mapi.putExtra("class", "planimeter");

                        startActivity(mapi);
                        //   } else {
                        //     showJackAlert("Please Connect Card Reader!");
                        //  }
                        //  } else {
                        //     ToastUtil.showToast(getApplicationContext(), "No Connection to Host Computer!");
                        // }
                        break;
                    case 3:
                        Intent verify = new Intent(DashBoardActivity.this, FarmInputsActivity.class);
                        AudioManager am2 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        //  if (am2.isWiredHeadsetOn()) {
                        startActivity(verify);
                        //  } else {
                        //     showJackAlert("Please Connect Card Reader!");
                        // }
                        db.close();
                        break;
                    case 4:
                        if (db.getTrainingCount() > 0) {
                            Intent veri = new Intent(getApplicationContext(), TrainingActivity.class);
                            AudioManager am3 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            //  if (am3.isWiredHeadsetOn()) {
                            startActivity(veri);
                            //  } else {
                            //     showJackAlert("Please Connect Card Reader!");
                            // }
                        } else {
                            showAlert("Download data first!");
                        }
                        break;
                    case 5:
                        Intent pdfviewer = new Intent(getApplicationContext(), ChoosePDFActivity.class);
                        startActivity(pdfviewer);
                        break;
                    case 6:
                        Intent calender = new Intent(getApplicationContext(), CalenderActivity.class);
                        startActivity(calender);
                        break;
                    case 7:
                        if (db.getFarmCount() > 0) {
                            Intent veri2 = new Intent(getApplicationContext(), AllFormsActivity.class);
                            // veri2.putExtra("class", "forms");
                            AudioManager amform = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            //    if (amform.isWiredHeadsetOn()) {
                            startActivity(veri2);
                            //   } else {
                            //       showJackAlert("Please Connect Card Reader!");
                            //    }
                        } else {
                            showAlert("Download data first!");
                        }
                        db.close();
                        break;
                    case 8:
                        try {
                            Intent soda = getPackageManager().getLaunchIntentForPackage("com.techneos.soda.client.micro");
                            startActivity(soda);
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Install Soda", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 9:
                        if (db.getFarmCount() > 0) {
                            Intent veri2 = new Intent(getApplicationContext(), ManualFarmerSearch.class);
                            veri2.putExtra("class", "farm_update");
                            AudioManager amform = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            //  if (amform.isWiredHeadsetOn()) {
                            startActivity(veri2);
                            //  } else {
                            //      showJackAlert("Please Connect Card Reader!");
                            //   }
                        } else {
                            showAlert("Download data first!");
                        }
                        break;
                    case 10:
                        Intent settings = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(settings);
                        break;
                }
            }
        });
        File farmer_details = new File(SD_CARD_PATH + "/farmer_details");
        if (!farmer_details.exists()) {
            farmer_details.mkdir();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }


    /*
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        SavePrefrence(getApplicationContext(), "imagePath", fileUri.getPath());
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Bitmap photo = (Bitmap) data.getExtras().get("data");
                // Intent intent22 = new Intent(this, FormActivity.class);
                //to fingerprint class

//if(fileUri.getPath()==null) {
                Log.e("Image Path: ", getPrefrence(getApplicationContext(), "imagePath"));
//}
                f2 = new File(getPrefrence(getApplicationContext(), "imagePath"));

                if (f2.exists()) {
                    Intent intent22 = new Intent(this, FarmerRegistrationActivity.class);
                    // fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent22.putExtra("cid", getPrefrence(getApplicationContext(), "cid"));
                    //  SavePrefrence(getApplicationContext(), "imagePath", f2.getAbsolutePath());
                    // intent22.putExtra("imagePath", getPrefrence(getApplicationContext(), "imagePath"));
                    Log.e("filePath1: ", getPrefrence(getApplicationContext(), "imagePath"));
                    startActivity(intent22);
                } else {
                    captureImage();
                }
                //finish();
            }
        }
    }

    public static void SavePrefrence(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("mypref", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    /*  @Override
     protected void onSaveInstanceState(Bundle savedInstanceState2) {

     savedInstanceState2.putString("file_uri", getPrefrence(getApplicationContext(), "imagePath"));
     super.onSaveInstanceState(savedInstanceState2);
     }

     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState3) {
     fileUri = Uri.parse(getPrefrence(getApplicationContext(),"imagePath"));
     f2 = new File(getPrefrence(getApplicationContext(), "imagePath"));
     super.onRestoreInstanceState(savedInstanceState3);
     }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_upload_data:
                new uploadFarmers().execute();
                new uploadKml().execute();
                new uploadAssignedTrainings().execute();
                new uploadFarmerTimes().execute();
                new uploadSignedDocs().execute();
                //uploads for farm assesment
                new UploadFarmAssMajor().execute();
                new uploadFingerOneForm().execute();
                new uploadFingerTwoForm().execute();
                new uploadFingerThreeForm().execute();
                new uploadFingerFourForm().execute();
                new uploadFingerFiveForm().execute();
                break;

            case R.id.menu_download_data:
                //download farmers list
                new downloadFarmersList().execute();
                new downloadApprovedFarmersList().execute();
                new downloadAssignedInputs().execute();
                new downloadAllTrainMats().execute();
                new downloadFarms().execute();
                new downloadAllSubvillages().execute();
                new downloadAllHerbicides().execute();
                new downloadAllFoliarFeed().execute();
                new downloadAllOtherCrops().execute();
                new downloadAllVillages().execute();
                

                //download trainings
                try {
                    Log.e("Saved Date: ", getPrefrence(getApplicationContext(), "saved_date"));
                } catch (Exception ex) {
                }
                Log.e("Today: ", today);
                //   if(!today.equals(getPrefrence(getApplicationContext(),"saved_date"))) {
                new downloadTraining().execute();
                //  }
                new downloadTrainingCategories().execute();
                new downloadTrainingTypes().execute();

                break;
            default:
                return super.onOptionsItemSelected(item);
            //break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }

    private class uploadFarmers extends AsyncTask<Void, Void, Void> {

        private String responseString;
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute(); //To change body of generated methods, choose Tools | Templates.
            if (cd.isConnectingToInternet()) {
                if (!dialog.isShowing()) {
                    dialog.setMessage("Uploading data from server, please wait.");
                    dialog.setCancelable(true);
                    dialog.show();
                }
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            //check if database is empty
            int count = db.getFarmersCount();
            Log.i("Farmer Count: ", String.valueOf(count));
            if (cd.isConnectingToInternet() == true) {
                if (count > 0) {
                    List<Farmers> farmers = db.getAllFarmers();

                    for (Farmers cn : farmers) {
                        //sample farmers
                        String log = "Id: " + cn.getID() + " ,FName: " + cn.getFName() + " ,LName: " + cn.getLName() + " ,Gender: " + cn.getGender() + " ,ID number: " + cn.getIDNO() + " ,Phone: " + cn.getPhoneNumber() + " ,Email: " + cn.getEmail() + " ,Post: " + cn.getPost() + " ,Village: " + cn.getVillage() + " ,Subvillage: " + cn.getSubVillage() + " ,ImagePath: " + cn.getPicPath() + " ,Date: " + cn.getRegDate();
                        // Writing Farmers to log
                        Log.e(" ", log);
                        //upload to server
                        responseString = null;

                        HttpClient httpclient = new DefaultHttpClient();
                        Log.e("", Config.FILE_UPLOAD_URL);
                        HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

                        try {
                            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                                    new AndroidMultiPartEntity.ProgressListener() {

                                        @Override
                                        public void transferred(long num) {
                                            //  publishProgress((int) ((num / (float) totalSize) * 100));
                                        }
                                    });
                            imagePath = cn.getPicPath();
                            // fingerPath = cn.getFingerPath();
                            Log.d("ImagePath:", imagePath.toString());
                            // Log.d("FingerPath:", fingerPath.toString());
                            File imageFile = new File(imagePath);
                            //  File fingerFile = new File(fingerPath);

                            // Adding file data to http body
                            entity.addPart("image", new FileBody(imageFile));
                            // entity.addPart("image2", new FileBody(fingerFile));

                            // Extra parameters if you want to pass to server
                            entity.addPart("fname", new StringBody(cn.getFName()));
                            entity.addPart("lname", new StringBody(cn.getLName()));
                            entity.addPart("gender", new StringBody(cn.getGender()));
                            entity.addPart("id_no", new StringBody(String.valueOf(cn.getIDNO())));
                            entity.addPart("phone", new StringBody(cn.getPhoneNumber()));
                            entity.addPart("email", new StringBody(cn.getEmail()));
                            entity.addPart("post", new StringBody(cn.getPost()));
                            entity.addPart("village", new StringBody(cn.getVillage()));
                            entity.addPart("subvillage", new StringBody(cn.getSubVillage()));
                            entity.addPart("estfarmarea", new StringBody(cn.getEstFarmArea()));
                            entity.addPart("lat", new StringBody(cn.getLat()));
                            entity.addPart("longt", new StringBody(cn.getLong()));
                            entity.addPart("reg_date", new StringBody(cn.getRegDate()));
                            entity.addPart("cid", new StringBody(getPrefrence(getApplicationContext(), "cid")));
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
                                responseString = EntityUtils.toString(r_entity);
                            }

                        } catch (ClientProtocolException e) {
                            responseString = e.toString();
                        } catch (IOException e) {
                            responseString = e.toString();
                        }

                        //   return responseString;
                    }
                    //delete all info from tables
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Farmers Synced", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i("Data synced!", "");
                    Log.i("Server response:", responseString);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "No farmer data to sync!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result); //To change body of generated methods, choose Tools | Templates.
            //clear table farmers
            db.clearFarmers();
            db.close();
        }

    }

    private class uploadKml extends AsyncTask<Void, Void, Void> {

        private Exception exception;
        DatabaseHandler db5;

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                db5 = new DatabaseHandler(getApplicationContext());
                //upload to server
                String responseString2 = "";

                HttpClient httpclient = new DefaultHttpClient();
                Log.e("", Config.KML_UPLOAD_URL);
                HttpPost httppost = new HttpPost(Config.KML_UPLOAD_URL);

                try {
                    AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                            new AndroidMultiPartEntity.ProgressListener() {

                                @Override
                                public void transferred(long num) {
                                    //  publishProgress((int) ((num / (float) totalSize) * 100));
                                }
                            });
                    //get all file in a directory
                    String name = null, length = null;
                    File sdCardRoot = Environment.getExternalStorageDirectory();
                    File yourDir = new File(sdCardRoot, "/com.vistechprojects.planimeter/data/");
                    File[] contents = yourDir.listFiles();
                    if (contents.length > 0) {
                        for (File f : yourDir.listFiles()) {
                            String ext = FilenameUtils.getExtension(f.getName());
                            Log.e("Ext:", ext);
                            if (f.getName().length() == 19 && ext.matches("kml")) {
                                if (f.isFile()) {
                                    name = f.getName();
                                }
                                // Do your stuff
                                Log.i("File name:", name);
                                Log.i("Clean File name:", name.replace(".kml", "").trim());
                                Log.i("File length:", String.valueOf(name.length()));

                                String filePath2 = SD_CARD_PATH + "/com.vistechprojects.planimeter/data/" + name;
                                Log.i("Kml path:", filePath2.toString());
                                File sourceFile = new File(filePath2);

                                // Adding file data to http body
                                List<KmlMetaData> kml_meta = db5.getAllKmlMeta(name.replace(".kml", "").trim());
                                for (KmlMetaData cn : kml_meta) {
                                    //sample farmers
                                    String log = "FID: " + cn.getFID() + " ,USERID: " + cn.getUserID() + " ,Farm_id: " + cn.getFarmID();
                                    Log.i("Farm Data: ", log);
                                }
                                if (kml_meta.size() > 0) {
                                    entity.addPart("kml", new FileBody(sourceFile));
                                    for (KmlMetaData cn : kml_meta) {
                                        entity.addPart("cid", new StringBody(cn.getCID()));
                                        entity.addPart("user_id", new StringBody(cn.getUserID()));
                                        entity.addPart("farm_id", new StringBody(cn.getFarmID()));
                                    }

                                    Date date = new Date();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    entity.addPart("map_date", new StringBody(dateFormat.format(date)));

                                    totalSize = entity.getContentLength();
                                    httppost.setEntity(entity);

                                    // Making server call
                                    HttpResponse response = httpclient.execute(httppost);
                                    HttpEntity r_entity = response.getEntity();

                                    int statusCode = response.getStatusLine().getStatusCode();
                                    if (statusCode == 200) {
                                        // Server response
                                        responseString2 = EntityUtils.toString(r_entity);
                                        f.delete();
                                    } else {
                                        responseString2 = EntityUtils.toString(r_entity);
                                    }
                                    Log.e("KML Server Response:", responseString2);
                                } else {
                                    Log.i("Empty KML Meta: ", "...");
                                }
                            } else {
                                f.delete();
                            }
                            final String finalResponseString = responseString2;
                            //delete all info from tables
                            //  if ((responseString2.trim()).equals("true")) {
                            Log.e("Server ResponseAndroid:", responseString2);

                            //   } else {
                            //    runOnUiThread(new Runnable() {
                            //    @Override
                            //    public void run() {
                            //       Toast.makeText(getApplicationContext(), finalResponseString, Toast.LENGTH_SHORT).show();
                            //     }
                            //  });
                        }
                        // }
                    } else if (contents == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "No Farms to Sync", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "No Farms to Sync", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    // Log.e("KMLException:", e.getMessage());
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            db5.clearKmlMetaData();
            db5.close();

        }
    }

    private class downloadTraining extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_ALL_TRAININGS);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_ALL_TRAININGS);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("user_id", getPrefrence(getApplicationContext(), "user_id")));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("EXT training from server:", result);
                } catch (Exception e) {
                    Log.e("Ex trainings : ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] train_type_id = new String[JA.length()];
                        String[] ext_train_date = new String[JA.length()];
                        String[] farm_id = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);
                            train_type_id[i] = json.getString("train_type_id");
                            ext_train_date[i] = json.getString("ext_train_date");
                            farm_id[i] = json.getString("farm_id");

                        }
                        db.clearTrainings();
                        for (int i = 0; i < train_type_id.length; i++) {
                            //add to database

                            db.addTraining(new Training2(train_type_id[i], ext_train_date[i], farm_id[i]));
                        }
                        List<Training2> trains = db.getAllTrainings();
                        for (Training2 cn : trains) {
                            String log = "TTypeId: " + cn.getTID() + " ,TDate1: " + cn.getTDate1() + " ,FarmID: " + cn.getFarmID();
                            // Writing Trainings to log
                            Log.e("Offline database:", log);
                        }
                        db.close();

                    } else {
                        ToastUtil.showToast(getApplicationContext(), "No Training data on server!");
                    }
                } catch (Exception e) {

                    Log.e("ExExt Training: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //download only once
            Date d1 = new Date();

            saved_date = dateFormat.format(d1);
            SavePrefrence(getApplicationContext(), "saved_date", saved_date);

        }
    }

    private class downloadTrainingCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_ALL_TRAINING_CATS);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_ALL_TRAINING_CATS);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("TRAINING CATS from server:", result);
                } catch (Exception e) {
                    Log.e("Ex trainings : ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] train_cat_id = new String[JA.length()];
                        String[] train_cat = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);
                            train_cat_id[i] = json.getString("train_cat_id");
                            train_cat[i] = json.getString("train_cat");

                        }
                        //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                        db.clearTrainingsCats();
                        // List<InputsFromServer> inputs = db.getAllInputs();
                        for (int i = 0; i < JA.length(); i++) {
                            //add to database

                            db.addTrainingCat(new Training(train_cat_id[i], train_cat[i]));
                            //list1.add(input_cat[i]);
                            // list2.add(input_type[i]);
                        }
                        List<Training> trains = db.getAllTrainingsCats();
                        //int count = db.getTrainsCount();
                        //Log.d("Count: ", String.valueOf(count));
                        for (Training cn : trains) {
                            String log = "TCatID Id: " + cn.getTCatID2() + " ,TCat: " + cn.getTCat2();
                            // Writing Trainings to log
                            Log.e("Offline database:", log);
                        }
                        db.close();

                    } else {
                        ToastUtil.showToast(getApplicationContext(), "No TrainingCat data on server!");
                    }
                } catch (Exception e) {

                    Log.e("ExceptionTrainCatType: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    private class downloadTrainingTypes extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_ALL_TRAINING_TYPES);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_ALL_TRAINING_TYPES);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("TRAINING TYPES from server:", result);
                } catch (Exception e) {
                    Log.e("Ex trainings : ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] train_cat_id = new String[JA.length()];
                        String[] train_type_id = new String[JA.length()];
                        String[] train_type = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);
                            train_cat_id[i] = json.getString("train_cat_id");
                            train_type_id[i] = json.getString("train_type_id");
                            train_type[i] = json.getString("train_type");

                        }
                        //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                        db.clearTrainingsTypes();
                        // List<InputsFromServer> inputs = db.getAllInputs();
                        for (int i = 0; i < JA.length(); i++) {
                            //add to database

                            db.addTrainingType(new TrainingType(train_cat_id[i], train_type_id[i], train_type[i]));
                            //list1.add(input_cat[i]);
                            // list2.add(input_type[i]);
                        }
                        List<TrainingType> trains = db.getAllTrainingsTypes();
                        //int count = db.getTrainsCount();
                        //Log.d("Count: ", String.valueOf(count));
                        for (TrainingType cn : trains) {
                            String log = "TID Id: " + cn.getTID() + " ,TType: " + cn.getTType();
                            // Writing Trainings to log
                            Log.e("Offline database:", log);
                        }
                        db.close();

                    } else {
                        ToastUtil.showToast(getApplicationContext(), "No TrainingCat data on server!");
                    }
                } catch (Exception e) {

                    Log.e("ExceptionTrainType: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    private class dowloadLibrary extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_ALL_LIBRARY);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_ALL_LIBRARY);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    // finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Library Names from server:", result);
                } catch (Exception e) {
                    Log.e("Ex Library: ", e.toString());
                }
                try {
                    if (result.length() > 0) {
                        List<String> namesList = Arrays.asList(result.split(","));
                        for (int i = 0; i < namesList.size(); i++) {
                            if (i == namesList.size() - 1) {
                                String last1 = namesList.get(i);
                                String last2 = last1.substring(0, last1.length() - 1);
                                new downloadLibraryPdf(last2).execute();
                            }

                            //download each file
                            new downloadLibraryPdf(namesList.get(i)).execute();

                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "No Library data on server", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {

                    Log.e("ExceptionPDFLIB ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }
    /*get all pdf file names and download each*/

    private class downloadAllTrainMats extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_ALL_LIBRARY);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_ALL_LIBRARY);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Pdfs from server:", result);
                } catch (Exception e) {
                    Log.e("Ex Pdf Names: ", e.toString());
                }
                try {
                    if (result.length() > 0) {
                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] file_name = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            file_name[i] = json.getString("file_url");

                            //download each file
                            new downloadAllPdfs(file_name[i]).execute();
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "No Training data on server", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {

                    Log.e("ExceptionFarmersList: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    private class downloadAllPdfs extends AsyncTask<String, String, String> {

        private final String file_name;
        private int lenghtOfFile;

        public downloadAllPdfs(String s) {
            this.file_name = s;
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            if (cd.isConnectingToInternet()) {
                Log.e("Downloading Pdfs.", "..");
                try {
                    Log.e("", Config.TRAIN_MATS);
                    URL url = new URL(Config.TRAIN_MATS + "/" + file_name);
                    //url
                    Log.e("PDF URE: ", url.toString());
                    HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                    conection.setRequestMethod("POST");
                    conection.setDoOutput(true);
                    conection.getOutputStream().write(cid.getBytes("UTF-8"));
                    conection.connect();
                    // getting file length
                    lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(conection.getInputStream(), 8193);
                    File f = new File("/sdcard/train_mats/");
                    if (!f.exists()) {
                        f.mkdir();
                    }
                    //FileUtils.cleanDirectory(SD_CARD_PATH);

                    // Output stream to write file
                    OutputStream output = new FileOutputStream("/sdcard/train_mats/" + file_name);
                    Log.e("PDFiles: ", "/sdcard/train_mats/" + file_name);
                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Old Pdf Error: ", e.getMessage());
                }
            } else {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
        }
    }

    private class downloadLibraryPdf extends AsyncTask<String, String, String> {

        private final String file_name;
        private int lenghtOfFile;

        public downloadLibraryPdf(String s) {
            this.file_name = s;
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            if (cd.isConnectingToInternet()) {
                Log.e("Downloading Pdf Library.", "..");
                try {
                    Log.e("", Config.TRAIN_MATS);
                    URL url = new URL(Config.TRAIN_MATS + "/" + file_name);
                    Log.e("PDF URE: ", url.toString());
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8193);
                    File f = new File("/sdcard/train_mats/");
                    if (!f.exists()) {
                        f.mkdir();
                    }
                    //FileUtils.cleanDirectory(SD_CARD_PATH);

                    // Output stream to write file
                    OutputStream output = new FileOutputStream("/sdcard/train_mats/" + file_name);
                    Log.e("PDFiles: ", "/sdcard/train_mats/" + file_name);
                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Library Error: ", e.getMessage());
                }
            } else {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(s);
        }
    }

    private class downloadAllVillages extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_ALL_VILLAGES);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_ALL_VILLAGES);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("VIllagesException: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Villages Objects from server:", result);
                } catch (Exception e) {
                    Log.e("Ex Download Structures: ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] villageid = new String[JA.length()];
                        String[] wardid = new String[JA.length()];
                        String[] villagename = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            villageid[i] = json.getString("village_id");
                            wardid[i] = json.getString("ward_id");
                            villagename[i] = json.getString("village_name");

                        }
                        //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                        db.clearVillages();
                        // List<InputsFromServer> inputs = db.getAllInputs();
                        for (int i = 0; i < villageid.length; i++) {
                            //add to database

                            db.addVillages(new Villages(villageid[i], wardid[i], villagename[i]));
                            //list1.add(input_cat[i]);
                            // list2.add(input_type[i]);
                        }
                        //  List<Countries> countries = db.getAllCountries();
                        //   int count = db.getFarmersCount();
                        //   Log.e("Count: ", String.valueOf(count));
                        //    for (Countries cn : countries) {
                        //       String log = "Country: " + cn.getCountryName();
                        //      // Writing Farmers to log
                        //      Log.e("Offline Countries table:", log);
                        //   }
                        db.close();

                    } else {
                        ToastUtil.showToast(getApplicationContext(), "No Village data on server!");
                    }
                } catch (Exception e) {

                    Log.e("ExceptionVillages: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    private class downloadAllSubvillages extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_ALL_SUBVILLAGES);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_ALL_SUBVILLAGES);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Structure Sub villages from server:", result);
                } catch (Exception e) {
                    Log.e("Ex Download Structures: ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] subvillageid = new String[JA.length()];
                        String[] villageid = new String[JA.length()];
                        String[] subvillagename = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            subvillageid[i] = json.getString("subvillage_id");
                            villageid[i] = json.getString("village_id");
                            subvillagename[i] = json.getString("subvillage_name");

                        }
                        //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                        db.clearSubvillages();
                        // List<InputsFromServer> inputs = db.getAllInputs();
                        for (int i = 0; i < subvillageid.length; i++) {
                            //add to database

                            db.addSubvillages(new SubVillages(subvillageid[i], villageid[i], subvillagename[i]));
                            //list1.add(input_cat[i]);
                            // list2.add(input_type[i]);
                        }
                        //List<SubVillages> subvillages = db.getAllSubVillages();
                        //int count = db.getFarmersCount();
                        // Log.e("Count: ", String.valueOf(count));
                        //  for (SubVillages cn : subvillages) {
                        //   String log = "Subvillage: " + cn.getSubVillageName();
                        // Writing Farmers to log
                        //    Log.e("Offline Subvillages table:", log);
                        //    }
                        //   db.close();

                    } else {
                        ToastUtil.showToast(getApplicationContext(), "No Sub Village data on server!");
                    }

                } catch (Exception e) {

                    Log.e("ExceptionSubVillages: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    private class downloadAllHerbicides extends AsyncTask<Void, Void, Void> {

        private DatabaseHandler db;

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.GET_ALL_HERBIBIDES);
                    HttpPost httppost = new HttpPost(Config.GET_ALL_HERBIBIDES);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Herbicides  from server:", result);
                } catch (Exception e) {
                    Log.e("Ex Herbicides: ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] input_id = new String[JA.length()];
                        String[] input_type = new String[JA.length()];
                        db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            input_id[i] = json.getString("input_id");
                            input_type[i] = json.getString("input_type");

                        }
                        //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                        db.clearHerbicides();
                        for (int i = 0; i < input_id.length; i++) {
                            //add to database
                            db.addHerbicides(new Herbicides(input_id[i], input_type[i]));

                        }

                    } else {
                        ToastUtil.showToast(getApplicationContext(), "No Herbicide data on server!");
                    }

                } catch (Exception e) {

                    Log.e("ExceptionHerbicides: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    private class downloadAllFoliarFeed extends AsyncTask<Void, Void, Void> {

        private DatabaseHandler db;

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.GET_ALL_FOLIAR_FEED);
                    HttpPost httppost = new HttpPost(Config.GET_ALL_FOLIAR_FEED);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("FoliarFeed  from server:", result);
                } catch (Exception e) {
                    Log.e("Ex FoliarFeed: ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] input_id = new String[JA.length()];
                        String[] input_type = new String[JA.length()];
                        db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            input_id[i] = json.getString("input_id");
                            input_type[i] = json.getString("input_type");

                        }
                        //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                        db.clearHerbicides();
                        for (int i = 0; i < input_id.length; i++) {
                            //add to database
                            db.addFoliarFeed(new FoliarFeed(input_id[i], input_type[i]));

                        }

                    } else {
                        ToastUtil.showToast(getApplicationContext(), "No FoliarFeed data on server!");
                    }

                } catch (Exception e) {

                    Log.e("ExceptionFoliarFeed: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    private class downloadAllOtherCrops extends AsyncTask<Void, Void, Void> {

        private DatabaseHandler db;

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.GET_ALL_OTHER_CROPS);
                    HttpPost httppost = new HttpPost(Config.GET_ALL_OTHER_CROPS);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Other crops  from server:", result);
                } catch (Exception e) {
                    Log.e("Ex Other crops: ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] crop_id = new String[JA.length()];
                        String[] crop_name = new String[JA.length()];
                        db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            crop_id[i] = json.getString("crop_id");
                            crop_name[i] = json.getString("crop_name");

                        }
                        //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                        db.clearOtherCrops();
                        for (int i = 0; i < crop_id.length; i++) {
                            //add to database
                            db.addOtherCrops(new OtherCrops(crop_id[i], crop_name[i]));

                        }

                    } else {
                        ToastUtil.showToast(getApplicationContext(), "No Other Crops data on server!");
                    }

                } catch (Exception e) {

                    Log.e("ExceptionOtherCrops: ", e.toString());

                }
            } else {

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            db.close();
        }
    }

    private class downloadAssignedInputs extends AsyncTask<Void, Void, Void> {

        private String responseString;

        @Override
        protected Void doInBackground(Void... params) {

            //check if database is empty
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            count = db.getAssignedCount();
            if (cd.isConnectingToInternet()) {

                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.GET_ALL_ASSINGNED_INPUTS);
                    HttpPost httppost = new HttpPost(Config.GET_ALL_ASSINGNED_INPUTS);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("village_id", vid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                //if (responseString.length() > 0) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    responseString = sb.toString();
                    Log.e("Assigned Inputs from server:", responseString);

                    if (responseString.length() > 0) {

                        JSONArray JA = new JSONArray(responseString);
                        JSONObject json;
                        String[] assfinputs_id = new String[JA.length()];
                        String[] input_id = new String[JA.length()];
                        String[] input_type = new String[JA.length()];
                        String[] input_price = new String[JA.length()];
                        String[] input_unit = new String[JA.length()];
                        String[] input_total = new String[JA.length()];
                        String[] fid = new String[JA.length()];
                        String[] gen_id = new String[JA.length()];
                        String[] rcpt_num = new String[JA.length()];
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            assfinputs_id[i] = json.getString("assfinputs_id");
                            input_id[i] = json.getString("input_id");
                            input_type[i] = json.getString("input_type");
                            input_price[i] = json.getString("input_price");
                            input_unit[i] = json.getString("input_unit");
                            input_total[i] = json.getString("input_total");
                            fid[i] = json.getString("fid");
                            gen_id[i] = json.getString("gen_id");
                            rcpt_num[i] = json.getString("receipt_num");

                        }
                        //clear table assigned inputs table                   
                        db.clearAsignedInputs();
                        for (int i = 0; i < JA.length(); i++) {
                            db.addAssignedInput(new Assigns(assfinputs_id[i], input_id[i], input_type[i],
                                    input_unit[i], input_price[i], input_total[i], fid[i], gen_id[i], rcpt_num[i]));
                        }
                        Log.e("Assigned Count: ", "" + db.getAssignedCount());
                    }
                } catch (Exception ex) {
                    Log.e("Ex Assigned Inputs: ", ex.getMessage());
                }

                // }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            db.close();
            return null;
        }
    }

    private class uploadAssignedTrainings extends AsyncTask<Void, Void, Void> {

        private String responseString;

        @Override
        protected Void doInBackground(Void... params) {

            //check if database is empty
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            count = db.getAssignedTrainsCount();
            if (cd.isConnectingToInternet() == true) {
                if (count > 0) {
                    List<Training> assignedTrainings = db.getAllAssignedTrainings();

                    for (Training cn : assignedTrainings) {
                        //sample farmers
                        String log = "TId: " + cn.getTID() + " ,TStartTIme: " + cn.getTStartTime() + " ,TstopTime: " + cn.getTStopTime() + " ,CID: " + cn.getCID();
                        // Writing Farmers to log
                        Log.e(" ", log);
                        responseString = null;

                        HttpClient httpclient = new DefaultHttpClient();
                        Log.e("", Config.FARM_ASSIGN_TRAININGS_URL);
                        HttpPost httppost = new HttpPost(Config.FARM_ASSIGN_TRAININGS_URL);

                        try {
                            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                                    new AndroidMultiPartEntity.ProgressListener() {

                                        @Override
                                        public void transferred(long num) {
                                            //  publishProgress((int) ((num / (float) totalSize) * 100));
                                        }
                                    });

                            // Extra parameters if you want to pass to server
                            entity.addPart("tid", new StringBody(cn.getTID()));
                            entity.addPart("train_start_time", new StringBody(cn.getTStartTime()));
                            entity.addPart("train_stop_time", new StringBody(cn.getTStopTime()));
                            entity.addPart("lat", new StringBody(cn.getTLat()));
                            entity.addPart("longt", new StringBody(cn.getTLongt()));
                            entity.addPart("user_id", new StringBody(getPrefrence(getApplicationContext(), "user_id")));
                            entity.addPart("cid", new StringBody(cn.getCID()));
                            //entity.addPart("date_for_train", new StringBody(now);

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

                        } catch (ClientProtocolException e) {
                            responseString = e.toString();
                        } catch (IOException e) {
                            responseString = e.toString();
                        }

                        //   return responseString;
                    }
                    //delete all info from tables
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Assined Trainings uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.d("Data synced!", "");
                    try {
                        Log.e("Server response:", responseString);
                    } catch (Exception ex) {
                    }
                    //clear table farmers
                    db.clearAssignedTrainings();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "No Training Data To Sync!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            db.close();
            return null;
        }
    }

    private class downloadFarmersList extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute(); //To change body of generated methods, choose Tools | Templates.
            if (cd.isConnectingToInternet()) {
                if (!dialog.isShowing()) {
                    dialog.setMessage("Downloading data from server, please wait.");
                    dialog.setCancelable(true);
                    dialog.show();
                }
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server

                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.NOT_CONT_FARMERS_LIST);
                    HttpPost httppost = new HttpPost(Config.NOT_CONT_FARMERS_LIST);
                    //send cid
                    // Log.e("IP:", Config.FARM_GET_FARMERS_LIST);
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("village_id", vid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Farmers list server:" + vid + " " + cid, result);
                } catch (Exception e) {
                    Log.e("Ex Farmers list: ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] fid = new String[JA.length()];
                        String[] gen_id = new String[JA.length()];
                        //String[] card_uid = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            fid[i] = json.getString("fid");
                            gen_id[i] = json.getString("gen_id");
                            //card_uid[i] = json.getString("card_uid");

                        }
                        clearFarmersList(getApplicationContext());
                        if (JA.length() > 0) {
                            for (int i = 0; i < JA.length(); i++) {
                                //store in preferences
                                SaveFarmersList(getApplicationContext(), gen_id[i], fid[i]);
                                // SaveFarmerCardUID(getApplicationContext(), card_uid[i], gen_id[i]);
                            }
                        }

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(getApplicationContext(), "No Farmers data on server!");
                            }
                        });
                    }
                } catch (Exception e) {

                    Log.e("ExceptionFarmersList: ", e.toString());

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }

    private class downloadApprovedFarmersList extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_APPROVED_FARMERS_LIST);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_APPROVED_FARMERS_LIST);
                    //send cid
                    // Log.e("IP:", Config.FARM_GET_FARMERS_LIST);
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("village_id", vid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Approved Farmers list server:", result + " " + vid + " " + cid);
                } catch (Exception e) {
                    Log.e("Ex Approved Farmers list: ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] fid = new String[JA.length()];
                        String[] gen_id = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            fid[i] = json.getString("fid");
                            gen_id[i] = json.getString("gen_id");

                        }
                        clearApprovedFarmersList(getApplicationContext());
                        if (JA.length() > 0) {
                            for (int i = 0; i < JA.length(); i++) {
                                //store in preferences
                                SaveApprovedFarmersList(getApplicationContext(), gen_id[i], fid[i]);
                            }
                        }

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(getApplicationContext(), "No Farmers data on server!");
                            }
                        });
                    }
                } catch (Exception e) {

                    Log.e("ExceptionFarmersList: ", e.toString());

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }

    public static void SaveFarmersList(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("farmers", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }

    public static void SaveFarmerCardUID(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("card_uid", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }

    public static void SaveApprovedFarmersList(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("approvedfarmers", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }

    public static void clearApprovedFarmersList(Context ctx) {
        ctx.getSharedPreferences("approvedfarmers", ctx.MODE_PRIVATE)
                .edit().clear();
    }

    public static void clearFarmersList(Context ctx) {
        ctx.getSharedPreferences("farmers", ctx.MODE_PRIVATE)
                .edit().clear();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
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
                        //download farmers list
                        new downloadFarmersList().execute();
                        new downloadApprovedFarmersList().execute();
                        new downloadAssignedInputs().execute();
                        new downloadAllTrainMats().execute();
                        new downloadFarms().execute();
                        new downloadAllSubvillages().execute();
                        new downloadAllHerbicides().execute();
                        new downloadAllFoliarFeed().execute();
                        new downloadAllOtherCrops().execute();
                        new downloadAllVillages().execute();

                        //download trainings
                        try {
                            Log.e("Saved Date: ", getPrefrence(getApplicationContext(), "saved_date"));
                        } catch (Exception ex) {
                        }
                        Log.e("Today: ", today);
                        //   if(!today.equals(getPrefrence(getApplicationContext(),"saved_date"))) {
                        new downloadTraining().execute();
                        //  }
                        new downloadTrainingCategories().execute();
                        new downloadTrainingTypes().execute();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showJackAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardActivity.this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class uploadFingerOneForm extends AsyncTask {

        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                DatabaseHandler db2 = new DatabaseHandler(getApplicationContext());
                count = db2.getFingerOneCount();
                if (count > 0) {
                    List<FingerOneBack> finger = db2.getAllFingerOne();

                    for (FingerOneBack cn : finger) {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            Log.e("", Config.UPLOAD_FINGER_ONE_FORM);
                            HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_ONE_FORM);
                            //send cid
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                            nameValuePairs.add(new BasicNameValuePair("cid", cn.getCID()));
                            nameValuePairs.add(new BasicNameValuePair("soil_type", cn.getSoilType()));
                            nameValuePairs.add(new BasicNameValuePair("water_log_risk", cn.getWLRisk()));
                            nameValuePairs.add(new BasicNameValuePair("erosion_prev", cn.getEPrev()));
                            nameValuePairs.add(new BasicNameValuePair("crop_rotation", cn.getCRotation()));
                            nameValuePairs.add(new BasicNameValuePair("ratoon", cn.getRatoon()));
                            nameValuePairs.add(new BasicNameValuePair("crop_residues", cn.getCResidues()));
                            nameValuePairs.add(new BasicNameValuePair("manure", cn.getManure()));
                            nameValuePairs.add(new BasicNameValuePair("land_prep", cn.getLPrep()));
                            nameValuePairs.add(new BasicNameValuePair("seed_bed_prep", cn.getSBedPrep()));
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            nameValuePairs.add(new BasicNameValuePair("form_date", dateFormat.format(date)));
                            nameValuePairs.add(new BasicNameValuePair("user_id", cn.getUserID()));
                            nameValuePairs.add(new BasicNameValuePair("farm_id", cn.getFarmID()));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();

                            int statusCode = response.getStatusLine().getStatusCode();
                            if (statusCode == 200) {
                                // Server response
                                responseString1 = EntityUtils.toString(entity);
                            } else {
                                responseString1 = EntityUtils.toString(entity);
                            }
                            Log.e("Upload Finger One response: ", responseString1);
                        } catch (Exception e) {
                            Log.e("Exception: ", e.toString());
                            //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                            //  finish();
                        }
                    }
                } else {
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            db.clearFingerOne();
        }
    }

    private class uploadFingerTwoForm extends AsyncTask {

        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                count = db.getFingerTwoCount();
                if (count > 0) {
                    List<FingerTwoBack> finger = db.getAllFingerTwo();

                    for (FingerTwoBack cn : finger) {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            Log.e("", Config.UPLOAD_FINGER_TWO_FORM);
                            HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_TWO_FORM);
                            //send cid
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                            nameValuePairs.add(new BasicNameValuePair("cid", cn.getCID()));
                            nameValuePairs.add(new BasicNameValuePair("correct_seed", cn.getCorectSeed()));
                            nameValuePairs.add(new BasicNameValuePair("row_spacing", cn.getRowSpacing()));
                            nameValuePairs.add(new BasicNameValuePair("seed_per_stat", cn.getSeedPerStat()));
                            nameValuePairs.add(new BasicNameValuePair("planting_time", cn.getPlantingTIme()));
                            nameValuePairs.add(new BasicNameValuePair("farm_id", cn.getFarmID()));
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            nameValuePairs.add(new BasicNameValuePair("form_date", dateFormat.format(date)));
                            nameValuePairs.add(new BasicNameValuePair("user_id", cn.getUserID()));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();

                            int statusCode = response.getStatusLine().getStatusCode();
                            if (statusCode == 200) {
                                // Server response
                                responseString1 = EntityUtils.toString(entity);
                            } else {
                                responseString1 = EntityUtils.toString(entity);
                            }
                            Log.e("Upload Finger Two response: ", responseString1);
                        } catch (Exception e) {
                            Log.e("Exception: ", e.toString());
                            //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                            //  finish();
                        }
                    }
                } else {
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            db.clearFingerTwo();
        }
    }

    private class uploadFingerThreeForm extends AsyncTask {

        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                count = db.getFingerThreeCount();
                if (count > 0) {
                    List<FingerThreeBack> finger = db.getAllFingerThree();

                    for (FingerThreeBack cn : finger) {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            Log.e("", Config.UPLOAD_FINGER_THREE_FORM);
                            HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_THREE_FORM);
                            //send cid
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                            nameValuePairs.add(new BasicNameValuePair("cid", cn.getCID()));
                            nameValuePairs.add(new BasicNameValuePair("gap_fill", cn.getGapFill()));
                            nameValuePairs.add(new BasicNameValuePair("gap_fill_emer", cn.getFillOnEmer()));
                            nameValuePairs.add(new BasicNameValuePair("thin_num", cn.getThinNum()));
                            nameValuePairs.add(new BasicNameValuePair("thin_num_emer", cn.getThinNumEmer()));
                            nameValuePairs.add(new BasicNameValuePair("farm_id", getPrefrence(getApplicationContext(), "pid")));
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            nameValuePairs.add(new BasicNameValuePair("form_date", dateFormat.format(date)));
                            nameValuePairs.add(new BasicNameValuePair("user_id", cn.getUserID()));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();

                            int statusCode = response.getStatusLine().getStatusCode();
                            if (statusCode == 200) {
                                // Server response
                                responseString1 = EntityUtils.toString(entity);
                            } else {
                                responseString1 = EntityUtils.toString(entity);
                            }
                            Log.e("Upload Finger Three response: ", responseString1);
                        } catch (Exception e) {
                            Log.e("Exception: ", e.toString());
                            //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                            //  finish();
                        }
                    }
                } else {
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            db.clearFingerThree();
        }
    }

    private class uploadFingerFourForm extends AsyncTask {

        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                count = db.getFingerFourCount();
                if (count > 0) {
                    List<FingerFourBack> finger = db.getAllFingerFour();

                    for (FingerFourBack cn : finger) {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            Log.e("", Config.UPLOAD_FINGER_FOUR_FORM);
                            HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_FOUR_FORM);
                            //send cid
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                            nameValuePairs.add(new BasicNameValuePair("cid", cn.getCID()));
                            nameValuePairs.add(new BasicNameValuePair("first_branch", cn.getFirstBranch()));
                            nameValuePairs.add(new BasicNameValuePair("foliar", cn.getFoliar()));
                            nameValuePairs.add(new BasicNameValuePair("weeds", cn.getWeeds()));
                            nameValuePairs.add(new BasicNameValuePair("farm_id", cn.getFarmID()));
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            nameValuePairs.add(new BasicNameValuePair("form_date", dateFormat.format(date)));
                            nameValuePairs.add(new BasicNameValuePair("user_id", cn.getUserID()));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();

                            int statusCode = response.getStatusLine().getStatusCode();
                            if (statusCode == 200) {
                                // Server response
                                responseString1 = EntityUtils.toString(entity);
                            } else {
                                responseString1 = EntityUtils.toString(entity);
                            }
                            Log.e("Upload Finger One response: ", responseString1);
                        } catch (Exception e) {
                            Log.e("Exception: ", e.toString());
                            //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                            //  finish();
                        }
                    }
                } else {
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            db.clearFingerFour();
        }
    }

    private class uploadFingerFiveForm extends AsyncTask {

        private String responseString1;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                count = db.getFingerFiveCount();
                if (count > 0) {
                    List<FingerFiveBack> finger = db.getAllFingerFive();

                    for (FingerFiveBack cn : finger) {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            Log.e("", Config.UPLOAD_FINGER_FIVE_FORM);
                            HttpPost httppost = new HttpPost(Config.UPLOAD_FINGER_FIVE_FORM);
                            //send cid
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                            nameValuePairs.add(new BasicNameValuePair("cid", cn.getCID()));
                            nameValuePairs.add(new BasicNameValuePair("pest_level", cn.getPestLevel()));
                            nameValuePairs.add(new BasicNameValuePair("pest_damage", cn.getPestDamage()));
                            nameValuePairs.add(new BasicNameValuePair("last_scout", cn.getLastScout()));
                            nameValuePairs.add(new BasicNameValuePair("empty_cans", cn.getEmptyCans()));
                            nameValuePairs.add(new BasicNameValuePair("peg_board_avail", cn.getPegBoardAvail()));
                            nameValuePairs.add(new BasicNameValuePair("scout_method", cn.getScoutMethod()));
                            nameValuePairs.add(new BasicNameValuePair("spray_time", cn.getSprayTime()));
                            nameValuePairs.add(new BasicNameValuePair("pest_abs_duration", cn.getPestAbsDur()));
                            nameValuePairs.add(new BasicNameValuePair("correct_use_pesticide", cn.getCorrectPestUse()));
                            nameValuePairs.add(new BasicNameValuePair("safe_usage_cans", cn.getSafeUsageCans()));
                            nameValuePairs.add(new BasicNameValuePair("farm_id", cn.getFarmID()));
                            Date date = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            nameValuePairs.add(new BasicNameValuePair("form_date", dateFormat.format(date)));
                            nameValuePairs.add(new BasicNameValuePair("user_id", cn.getUserID()));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();

                            int statusCode = response.getStatusLine().getStatusCode();
                            if (statusCode == 200) {
                                // Server response
                                responseString1 = EntityUtils.toString(entity);
                            } else {
                                responseString1 = EntityUtils.toString(entity);
                            }
                            Log.e("Upload Finger One response: ", responseString1);
                        } catch (Exception e) {
                            Log.e("Exception: ", e.toString());
                            //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                            //  finish();
                        }
                    }
                } else {
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            db.clearFingerFive();
            db.close();
        }
    }

    private class downloadFarms extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    Log.e("", Config.FARM_GET_FARM_LIST);
                    HttpPost httppost = new HttpPost(Config.FARM_GET_FARM_LIST);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", cid));
                    nameValuePairs.add(new BasicNameValuePair("village_id", vid));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    //  finish();
                }

                String result = null;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("Farm list server:", result);
                } catch (Exception e) {
                    Log.e("Ex Farm list: ", e.toString());
                }
                try {
                    if (result.length() > 0) {

                        JSONArray JA = new JSONArray(result);
                        JSONObject json;
                        String[] farm_id = new String[JA.length()];
                        String[] farm_name = new String[JA.length()];
                        String[] farm_area = new String[JA.length()];
                        String[] farm_peri = new String[JA.length()];
                        String[] lat = new String[JA.length()];
                        String[] longt = new String[JA.length()];
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        for (int i = 0; i < JA.length(); i++) {
                            json = JA.getJSONObject(i);

                            farm_id[i] = json.getString("farm_id");
                            farm_name[i] = json.getString("farm_name");
                            if (json.getString("actual_farm_area").length() > 0) {
                                farm_area[i] = json.getString("actual_farm_area");
                            } else {
                                farm_area[i] = json.getString("estimated_farm_area");
                            }
                            farm_peri[i] = json.getString("farm_peri");
                            lat[i] = json.getString("latitude");
                            longt[i] = json.getString("longitude");

                        }
                        db.clearFarms();
                        if (JA.length() > 0) {
                            for (int i = 0; i < JA.length(); i++) {
                                //store in preferences
                                db.addFarm(new Farm(farm_id[i], farm_name[i], farm_area[i], farm_peri[i], lat[i], longt[i], cid));
                            }
                        }

                        List<Farm> farmsList = db.getAllFarms();
                        int i = 0;
                        //for (int i = 0; i < countriesList.size(); i++) {
                        for (Farm r : farmsList) {

                            //dataAdapter2.add(new MyData(r.getRegionName(), r.getRegionID()));
                            String log = "Farm Id: " + r.getFarmID() + " ,Farm name: " + r.getFarmName() + " ,Farm Size: " + r.getFarmSize();
                            Log.e("Farms List: ", log);
                            i++;
                        }
                        db.close();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(getApplicationContext(), "No Farm data on server!");
                            }
                        });
                    }
                } catch (Exception e) {

                    Log.e("ExceptionFarmList: ", e.toString());

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

    }

    class uploadSignedDocs extends AsyncTask {

        private String result;

        @Override
        protected Object doInBackground(Object[] params) {
            ConnectionDetector cd2 = new ConnectionDetector(getApplicationContext());
            if (cd2.isConnectingToInternet()) {
                //download all inputs from server
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                if (db.getSignedDocsCount() > 0) {
                    InputStream is = null;
                    List<SignedDoc> allDocs = new ArrayList<SignedDoc>();
                    allDocs = db.getAllSignedDocs();
                    for (SignedDoc doc : allDocs) {
                        Log.e("CID: " + doc.getCID() + "FID: " + doc.getFID(), "Date: " + doc.getSignDate() + "UID: " + doc.getUserID());
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            Log.e("", Config.UPLOAD_SIGNATURES);
                            HttpPost httppost = new HttpPost(Config.UPLOAD_SIGNATURES);
                            //send cid

                            List<NameValuePair> nameValuePairs = null;
                            nameValuePairs = new ArrayList<NameValuePair>(2);
                            nameValuePairs.add(new BasicNameValuePair("cid", doc.getCID()));
                            nameValuePairs.add(new BasicNameValuePair("fid", doc.getFID()));
                            nameValuePairs.add(new BasicNameValuePair("sign_date", doc.getSignDate()));
                            nameValuePairs.add(new BasicNameValuePair("user_id", doc.getUserID()));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();

                            is = entity.getContent();
                        } catch (Exception e) {
                            Log.e("Exception: ", e.toString());
                            //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                            //  finish();
                        }

                    }
                    result = null;
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();
                        Log.e("Sign Response server:", result);
                    } catch (Exception e) {
                        Log.e("Ex Sign: ", e.toString());
                    }
                }

            } else {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            db.clearSignedDocs();
            db.close();
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(o);
        }
    }

    private class uploadFarmerTimes extends AsyncTask {

        private String responseString;

        @Override
        protected Object doInBackground(Object[] params) {
            Log.e("Uploding farmer times", "");
            //check if database is empty
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            count = db.getFarmersTimeCount();
            if (cd.isConnectingToInternet() == true) {
                if (count > 0) {
                    List<FarmerTime> allFarmerTimes = db.getAllFarmerTimes();
                    for (FarmerTime cn : allFarmerTimes) {
                        responseString = null;

                        HttpClient httpclient = new DefaultHttpClient();
                        Log.e("", Config.FARMER_TIME_UPLOAD_URL);
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
                            entity.addPart("fid", new StringBody(cn.getFID()));
                            entity.addPart("f_date_time", new StringBody(cn.getFTime()));
                            entity.addPart("user_id", new StringBody(cn.getUserID()));
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
                            Log.e("Server response:", responseString);
                            // responseString = e.toString();
                        } catch (Exception e) {
                            // responseString = e.toString();
                        }

                        //   return responseString;
                        try {
                            Log.e("Server response:", responseString);
                        } catch (Exception ex) {
                        }
                    }
                }
                //delete all info from tables

                //clear table farmers
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            db.clearFarmerTimes();
            runOnUiThread(new Runnable() {
                public void run() {
                    // Toast.makeText(getApplicationContext(), "Server response: " + responseString, Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    /**
     * class to upload farm assessment forms major
     *
     */
    public class UploadFarmAssMajor extends AsyncTask {

        private String responseString1;
        private DatabaseHandler db;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                db = new DatabaseHandler(getApplicationContext());
                count = db.getFormAssFormsMajorCount();
                if (count > 0) {
                    List<FarmAssFormsMajor> form = db.getAllActivityLogsMajor();

                    for (FarmAssFormsMajor cn : form) {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            Log.e("", Config.UPLOAD_FARM_ASS_FORM_MAJOR);
                            HttpPost httppost = new HttpPost(Config.UPLOAD_FARM_ASS_FORM_MAJOR);
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                            nameValuePairs.add(new BasicNameValuePair("farm_id", cn.getFarmID()));
                            nameValuePairs.add(new BasicNameValuePair("form_type_id", cn.getFormTypeID()));
                            nameValuePairs.add(new BasicNameValuePair("activity_method", cn.getActivityMethod()));
                            nameValuePairs.add(new BasicNameValuePair("activity_date", cn.getActDate()));
                            nameValuePairs.add(new BasicNameValuePair("family_hours", cn.getFamilyHours()));
                            nameValuePairs.add(new BasicNameValuePair("hired_hours", cn.getHiredHours()));
                            nameValuePairs.add(new BasicNameValuePair("collect_date", cn.getCollectDate()));
                            nameValuePairs.add(new BasicNameValuePair("money_out", cn.getMoneyOut()));
                            nameValuePairs.add(new BasicNameValuePair("remarks", cn.getRemarks()));
                            nameValuePairs.add(new BasicNameValuePair("user_id", cn.getUserID()));
                            nameValuePairs.add(new BasicNameValuePair("lat", cn.getLat()));
                            nameValuePairs.add(new BasicNameValuePair("longt", cn.getLongt()));
                            nameValuePairs.add(new BasicNameValuePair("cid", cn.getCoID()));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();

                            int statusCode = response.getStatusLine().getStatusCode();
                            if (statusCode == 200) {
                                // Server response
                                responseString1 = EntityUtils.toString(entity);
                            } else {
                                responseString1 = EntityUtils.toString(entity);
                            }
                            Log.i("Upload Form Assesment Major Response: ", responseString1);
                        } catch (Exception e) {
                            Log.e("Exception: ", e.toString());
                            //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                            //  finish();
                        }
                    }
                } else {
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            db.clearFarmAssFormsMajor();
            db.close();
        }
    }

    /**
     * class to upload farm assessment forms medium
     *
     */
    public class UploadFarmAssMedium extends AsyncTask {

        private String responseString1;
        private DatabaseHandler db;

        @Override
        protected Object doInBackground(Object[] params) {
            if (cd.isConnectingToInternet()) {
                db = new DatabaseHandler(getApplicationContext());
                count = db.getFormAssFormsMajorCount();
                if (count > 0) {
                    List<FarmAssFormsMedium> form = db.getAllActivityLogsMedium();

                    for (FarmAssFormsMedium cn : form) {
                        try {
                            HttpClient httpclient = new DefaultHttpClient();
                            Log.e("", Config.UPLOAD_FARM_ASS_FORM_MEDIUM);
                            HttpPost httppost = new HttpPost(Config.UPLOAD_FARM_ASS_FORM_MEDIUM);
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(8);
                            nameValuePairs.add(new BasicNameValuePair("farm_id", cn.getFarmID()));
                            nameValuePairs.add(new BasicNameValuePair("form_type_id", cn.getFormTypeID()));
                            nameValuePairs.add(new BasicNameValuePair("activity_method", cn.getActivityMethod()));
                            nameValuePairs.add(new BasicNameValuePair("activity_date", cn.getActDate()));
                            nameValuePairs.add(new BasicNameValuePair("family_hours", cn.getFamilyHours()));
                            nameValuePairs.add(new BasicNameValuePair("hired_hours", cn.getHiredHours()));
                            nameValuePairs.add(new BasicNameValuePair("collect_date", cn.getCollectDate()));
                            nameValuePairs.add(new BasicNameValuePair("money_out", cn.getMoneyOut()));
                            nameValuePairs.add(new BasicNameValuePair("remarks", cn.getRemarks()));
                            nameValuePairs.add(new BasicNameValuePair("user_id", cn.getUserID()));
                            nameValuePairs.add(new BasicNameValuePair("lat", cn.getLat()));
                            nameValuePairs.add(new BasicNameValuePair("longt", cn.getLongt()));
                            nameValuePairs.add(new BasicNameValuePair("cid", cn.getCoID()));
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            HttpEntity entity = response.getEntity();

                            int statusCode = response.getStatusLine().getStatusCode();
                            if (statusCode == 200) {
                                // Server response
                                responseString1 = EntityUtils.toString(entity);
                            } else {
                                responseString1 = EntityUtils.toString(entity);
                            }
                            Log.i("Upload Form Assesment Medium Response: ", responseString1);
                        } catch (Exception e) {
                            Log.e("Exception: ", e.toString());
                            //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                            //  finish();
                        }
                    }
                } else {
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            db.clearFarmAssFormsMajor();
            db.close();
        }
    }

}
