package com.bensoft.farmersearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.acs.audiojack.AudioJackReader;
import static com.acs.audiojack.AudioJackReader.PICC_CARD_TYPE_ISO14443_TYPE_A;
import com.acs.audiojack.Result;

import com.bensoft.utils.ToastUtil;
import com.bensoft.main.R;
import com.bensoft.utils.Config;
import com.bensoft.utils.ConnectionDetector;
import com.bensoft.main.DashBoardActivity;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.main.FarmInputsActivity;
import com.bensoft.entities.KmlMetaData;
import com.bensoft.main.PlotSelectionActitivty;
import com.bensoft.entities.SignedDoc;
import com.bensoft.main.TrainingActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ApprovedFarmerSearch extends Activity implements OnClickListener {

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().toString();
    public static final int progress_bar_type = 0;
    public ImageView fingerprintImage;
    public String fingerPrintImageFromServer;
    ImageView my_image;
    private String[] m;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String filePath;
    private String filePath2;
    private TextView tveriId;
    private EditText veriId;
    private Button register;
    private Button getFingerServer;
    // Progress Dialog
    private ProgressDialog pDialog;
    private Button validate;
    private Button calibration;
    private Button back;
    private ProgressDialog progressDialog;
    private byte[] model;

    private String farmer_id;
    private long lastBackTime = 0;
    private String path = Environment.getExternalStorageDirectory()
            + File.separator + "fingerprint_image";
    private String rootPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    private String farmerImageFromServer;
    private String next2;
    private String next1;
    private String cid;
    private boolean running = false;
    private String toWhichClass;
    private AlertDialog.Builder search;
    private boolean match;
    private AudioManager mAudioManager;
    private AudioJackReader mReader;
    int timeout = 5;    /* 1 second. */

    private byte[] fidCommandApdu = new byte[]{(byte) 0xFF, (byte) 0xB0, (byte) 0x00, (byte) 0x04,
            (byte) 0x0F};;
    String uidApduString = "FF CA 00 00 00h";
    String fidApduString = "FF B0 00 04 0Fh";
    String piccRfConfigString = "07 85 85 85 85 85 85 85 85 69 69 69 69 69 69 69 69 3F 3F";
    int cardType = PICC_CARD_TYPE_ISO14443_TYPE_A;
    private ProgressDialog mProgress;
    private Timer mTimer;

    private void showValidateResult() {
        try {
            Intent intent = getIntent();
            cid = intent.getStringExtra("cid");
            String next1 = "";
            String next = "";
            String next2 = "";
            if (intent.getExtras() != null) {
                if (intent.getStringExtra("inputs") != null) {
                    try {
                        next1 = intent.getStringExtra("inputs");
                        Log.e("NEXT1: ", next1);
                    } catch (Exception e) {
                        Log.e("Exception next1: ", e.getMessage());
                    }
                }

                if (intent.getStringExtra("planimeter") != null) {
                    next = intent.getStringExtra("planimeter");
                    Log.e("NEXT: ", next);
                }

                if (intent.getStringExtra("training") != null) {
                    next2 = intent.getStringExtra("training");
                    Log.e("NEXT2: ", next2);
                }
            }
            if ((next1.trim()).equals("inputs") && next.matches("") && next2.matches("")) {
                showAlertInputs("Farmer verified sucessfully");

            } else if ((next.trim()).equals("planimeter") && next1.matches("") && next2.matches("")) {
                showAlertPlani("Farmer verified sucessfully");

            } else if ((next2.trim()).equals("training") && next.matches("") && next1.matches("")) {
                showAlertTrain("Farmer verified sucessfully");

            } else if ((next2.trim()).equals("demoPlotEvalutation") && next.matches("") && next1.matches("")) {
                showAlertTrain("Farmer verified sucessfully");

            } else {
                Log.e("else: ", "...");
            }
        } catch (Exception e) {
            Log.e("Exception: ", e.getMessage());
        }
    }

    private void showAlertNotFarmer(final String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ApprovedFarmerSearch.this);
        builder.setMessage(message).setTitle("Fingerprint Validation")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void SavePrefrence(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("mypref", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }

    private void showAlertPlani(final String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ApprovedFarmerSearch.this);
        builder.setMessage(message).setTitle("Fingerprint Validation")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        Intent planimeter = getPackageManager().getLaunchIntentForPackage("com.vistechprojects.planimeter");
                        startActivity(planimeter);
                        //  }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showAlertInputs(final String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ApprovedFarmerSearch.this);
        builder.setMessage(message).setTitle("Fingerprint Validation")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //show green sound
                        Intent farmer_input = new Intent(getApplicationContext(), FarmInputsActivity.class);
                        farmer_input.putExtra("cid", cid);
                        farmer_input.putExtra("ifarmer_id", farmer_id);
                        startActivity(farmer_input);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showAlertTrain(final String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ApprovedFarmerSearch.this);
        builder.setMessage(message).setTitle("Fingerprint Validation")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent train = new Intent(getApplicationContext(), TrainingActivity.class);
                        train.putExtra("cid", cid);
                        train.putExtra("farmer_id", farmer_id);
                        startActivity(train);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showFingerImage(int fingerType, byte[] data) {
        Bitmap image = BitmapFactory.decodeByteArray(data, 0, data.length);
        // saveImage(data);
        if (image.getHeight() != 360) {
        }
        fingerprintImage.setBackgroundDrawable(new BitmapDrawable(image));
        //writeToFile(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mAudioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);
        mReader = new AudioJackReader(mAudioManager);
        mReader.reset();

        initView();
        initViewListener();
        Intent fidd = getIntent();

        farmer_id = fidd.getStringExtra("farmer_id");
        toWhichClass = fidd.getStringExtra("class");
    }

    private void initView() {
        spinner = (Spinner) findViewById(R.id.spinner);
        tveriId = (TextView) findViewById(R.id.tVeriId);
        veriId = (EditText) findViewById(R.id.etVeriId);
        register = (Button) findViewById(R.id.register);
        getFingerServer = (Button) findViewById(R.id.bgetFingerServer);
        getFingerServer.setText("Verify Farmer");
        validate = (Button) findViewById(R.id.validate);
        calibration = (Button) findViewById(R.id.calibration);
        back = (Button) findViewById(R.id.backRegister);
        fingerprintImage = (ImageView) findViewById(R.id.fingerprintImage);
        spinner.setVisibility(View.GONE);
        register.setVisibility(View.GONE);
        //getFingerServer.setVisibility(View.GONE);
        //validate.setText("Verify Card");
        back.setVisibility(View.GONE);
        tveriId.setVisibility(View.GONE);
        validate.setVisibility(View.GONE);
        getFingerServer.setVisibility(View.VISIBLE);
        //fingerprintImage.setVisibility(View.GONE);
        // Receiving the data from previous activity
        /* Initialize the progress dialog */
        mProgress = new ProgressDialog(ApprovedFarmerSearch.this);
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        //init reader
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mAudioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);
        mReader = new AudioJackReader(mAudioManager);
        /* Set the sleep timeout to 10 seconds. */
        mReader.setSleepTimeout(6);
    }

    private void writeToFile(byte[] data) {
        String dir = rootPath + File.separator + "fingerprint_image";
        File dirPath = new File(dir);
        if (!dirPath.exists()) {
            dirPath.mkdir();
        }

        filePath = dir + "/" + System.currentTimeMillis() + ".bmp";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initViewListener() {
        getFingerServer.setOnClickListener(this);
        register.setOnClickListener(this);
        validate.setOnClickListener(this);
        calibration.setOnClickListener(this);
        back.setOnClickListener(this);
        //nfc stuff

        /* Set the PICC ATR callback. */
        mReader.setOnPiccAtrAvailableListener(
                new AudioJackReader.OnPiccAtrAvailableListener() {

                    @Override
                    public void onPiccAtrAvailable(AudioJackReader reader, byte[] atr) {
                        Log.e("atr", "atr");
                        /* Transmit the APDU. */
                        if (mReader.piccTransmit(timeout, fidCommandApdu)) {
                            Log.e("FIDAPDU", "sent");
                        }

                    }
                });
        /* Set the result callback. */
        mReader.setOnResultAvailableListener(
                new AudioJackReader.OnResultAvailableListener() {
                    private Result rfresult;

                    @Override
                    public void onResultAvailable(AudioJackReader reader, final Result result) {
                        rfresult = result;

                        if (rfresult.getErrorCode() == Result.ERROR_SUCCESS) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //  Toast.makeText(getApplicationContext(), "Result: " + toErrorCodeString(result.getErrorCode()), Toast.LENGTH_SHORT).show();
                                    Log.i("Result: ", "" + toErrorCodeString(result.getErrorCode()));
                                }
                            });
                        }
                    }
                });

        /* Set the PICC response APDU callback. */
        mReader.setOnPiccResponseApduAvailableListener(
                new AudioJackReader.OnPiccResponseApduAvailableListener() {
                    private String found;
                    private String fidVal;

                    @Override
                    public void onPiccResponseApduAvailable(AudioJackReader reader,
                            final byte[] responseApdu) {
                        if (responseApdu.length > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mProgress.isShowing()) {
                                        mProgress.dismiss();

                                    }

                                    // Toast.makeText(getApplicationContext(), "APDU RESPONSE: " + toHexString(responseApdu), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.i("APDU RESPONSE: ", "" + toHexString(responseApdu));
                            String cardfid0 = toHexString(responseApdu);
                            String cardfid = cardfid0.replaceAll(" ", "");
                            String cleanfid = cardfid.substring(0, cardfid.length() - 4);
                            Log.i("CLEAN FID: ", hexToASCII(cleanfid));
                            //do stuff with response apdu
                            //check if farmer exists in list
                            found = hexToASCII(cleanfid);
                            if (found.length() == 15) {

                                Map<String, ?> allEntries = getApprovedFarmer(getApplicationContext());

                                for (final Map.Entry<String, ?> entry : allEntries.entrySet()) {

                                    if (found.trim().equals(entry.getKey().trim())) {
                                        match = true;
                                        fidVal = entry.getValue().toString();
                                        Log.e("Match: ", entry.getKey() + ": " + fidVal);
                                    }
                                }

                                if (match) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {

                                            showAlert2("Farmer Found \n" + found, found);

                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            //Toast.makeText(getApplicationContext(), "Farmer Not Found", Toast.LENGTH_LONG).show();
                                            //alert

                                            showAlert3("Farmer ID Not Found!");
                                        }
                                    });
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        //Toast.makeText(getApplicationContext(), "Farmer Not Found", Toast.LENGTH_LONG).show();
                                        //alert

                                        showAlert3("Please Verify Farmer ID");
                                    }
                                });
                            }
                        }
                    }
                });

    }

    private void setButtonEnabled(boolean enabled) {
        getFingerServer.setEnabled(enabled);
        register.setEnabled(enabled);
        validate.setEnabled(enabled);
        back.setEnabled(enabled);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register:
                //asyncFingerprintVeri.register();
                break;
            case R.id.bgetFingerServer:
                /* Reset the reader. */
                mReader.reset(new AudioJackReader.OnResetCompleteListener() {

                    public void onResetComplete(AudioJackReader reader) {
                        Log.e("reset", "reset");

                        if (mReader.piccPowerOn(timeout, cardType)) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgress.setMessage("Tap Card...");
                                    mProgress.show();
                                }
                            });
                            /* Dismiss the progress after 5 seconds. */
                            mTimer = new Timer();
                            mTimer.schedule(new TimerTask() {

                                @Override
                                public void run() {

                                    mProgress.dismiss();
                                    mTimer.cancel();
                                    mReader.piccPowerOff();

                                }
                            }, 5000);
                        }
                    }
                });

                break;
            case R.id.validate:
                fidCommandApdu = toByteArray(fidApduString);
                /* Transmit the APDU. */
                mReader.piccTransmit(timeout, fidCommandApdu);
                // mReader.reset();
                break;
            case R.id.backRegister:
                finish();
                break;
            default:
                break;
        }
    }

    private void cancleProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
            progressDialog = null;
        }
    }

    /**
     * Showing Dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Fetching fingerprint. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    public String getFingerprintFromServerPath() {
        return this.fingerPrintImageFromServer;
    }

    public void setFingerprintFromServerPath(String path) {
        this.fingerPrintImageFromServer = path;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentBackTime = System.currentTimeMillis();
            if (currentBackTime - lastBackTime > 2000) {
                ToastUtil.showToast(this, R.string.exit_toast);
                lastBackTime = currentBackTime;
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean checkFingerprintAvailability(Context ctx, String key) {
//check in preferences
        boolean isFarmer = false;
        SharedPreferences pref = ctx.getSharedPreferences(
                "farmers", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        if (result == null) {
            isFarmer = false;
        } else {
            isFarmer = true;
        }
        return isFarmer;
    }

    class AsyncDownloadFarmerPic extends AsyncTask<String, String, String> {

        private int lenghtOfFile;

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream("/sdcard/farmer_details/" + farmer_id + ".jpg");

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
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog *
         */
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
            //ToastUtil.showToast(getApplicationContext(), "File length: "+Integer.valueOf(lenghtOfFile));
            farmerImageFromServer = Environment.getExternalStorageDirectory().toString() + "/farmer_details/" + farmer_id + ".jpg";
            if (lenghtOfFile >= 2323 && farmer_id.length() > 5) {
                // ToastUtil.showToast(getApplicationContext(), "Fingerprint successfully downloaded");
                //validate.setVisibility(View.VISIBLE);
            } else {
                // ToastUtil.showToast(getApplicationContext(), "No such fingerprint on database");
                //validate.setVisibility(View.VISIBLE);
            }
            setFingerprintFromServerPath(fingerPrintImageFromServer);
        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public static Map<String, ?> getApprovedFarmer(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "approvedfarmers", ctx.MODE_PRIVATE);
        Map<String, ?> result = pref.getAll();
        return result;
    }

    private void showAlert2(String message, final String fid2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ApprovedFarmerSearch.this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (toWhichClass.equals("inputs")) {
                            Intent farmer_input = new Intent(getApplicationContext(), FarmInputsActivity.class);
                            //farmer_input.putExtra("farmer_id", fid2);
                            SaveValFarmerID(getApplicationContext(), "val_gen_id", fid2);
                            startActivity(farmer_input);
                        } else if (toWhichClass.equals("planimeter")) {
                            DatabaseHandler db2 = new DatabaseHandler(getApplicationContext());
                           // db2.addKmlSupportData(new KmlMetaData(fid2, getPrefrence(getApplicationContext(), "user_id"), getPrefrence(getApplicationContext(), "cid")));
                            List<KmlMetaData> kmlmetadata = db2.getAllKmlMeta(fid2);
                            for (KmlMetaData cn : kmlmetadata) {
                                //sample farmers
                                String log = "FID: " + cn.getFID() + " ,USERID: " + cn.getUserID() + " ,CID: " + cn.getCID();

                            }
                            Intent planimeter = getPackageManager().getLaunchIntentForPackage("com.vistechprojects.planimeter");
                            startActivity(planimeter);
                        } else if (toWhichClass.equals("forms")) {
                            Intent allForms = new Intent(getApplicationContext(), PlotSelectionActitivty.class);
                            //allForms.putExtra("farmer_id",fid2);
                            SaveValFarmerID(getApplicationContext(), "val_gen_id", fid2);
                            startActivity(allForms);
                        } else if (toWhichClass.equals("farm_update")) {
                            Intent allForms = new Intent(getApplicationContext(), PlotSelectionActitivty.class);
                            allForms.putExtra("to_which","farm_update");
                            SaveValFarmerID(getApplicationContext(), "val_gen_id", fid2);
                            startActivity(allForms);
                        }

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showAlert3(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ApprovedFarmerSearch.this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ApprovedFarmerSearch.this);
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

    public String getFarmerID(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "farmers", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    public static void SaveValFarmerID(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("mypref", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }

    class uploadSignedDocs extends AsyncTask {

        String fid2;
        private String result;

        public uploadSignedDocs(String fid2) {
            this.fid2 = fid2;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            ConnectionDetector cd2 = new ConnectionDetector(getApplicationContext());
            if (cd2.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(Config.UPLOAD_SIGNATURES);
                    //send cid
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("cid", getPrefrence(getApplicationContext(), "cid")));
                    nameValuePairs.add(new BasicNameValuePair("fid", getFarmerID(getApplication(), fid2)));
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    nameValuePairs.add(new BasicNameValuePair("sign_date", dateFormat.format(date)));
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
                    Log.e("Sign Response server:", result + " fid android: " + getFarmerID(getApplication(), fid2));
                } catch (Exception e) {
                    Log.e("Ex Sign: ", e.toString());
                }

            } else {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            showAlert(result);
            super.onPostExecute(o);
        }
    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_farmer_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_search:
                //launch dialog to search for farmer manually

                search = new AlertDialog.Builder(this);
                search.setTitle("Title");
                search.setMessage("Message");

// Set an EditText view to get user input
                final EditText search_value = new EditText(this);
                search.setView(search_value);

                search.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = search_value.getText().toString();
                        // Do something with value!

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

    public static Map<String, ?> getAllUids(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "card_uid", ctx.MODE_PRIVATE);
        Map<String, ?> result = pref.getAll();
        return result;
    }

    public static Map<String, ?> getUnApprovedFarmer(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "farmers", ctx.MODE_PRIVATE);
        Map<String, ?> result = pref.getAll();
        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReader.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReader.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReader.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mReader.stop();
    }

    /**
     * Converts the HEX string to byte array.
     *
     * @param hexString the HEX string.
     * @return the number of bytes.
     */
    private int toByteArray(String hexString, byte[] byteArray) {

        char c = 0;
        boolean first = true;
        int length = 0;
        int value = 0;
        int i = 0;

        for (i = 0; i < hexString.length(); i++) {

            c = hexString.charAt(i);
            if ((c >= '0') && (c <= '9')) {
                value = c - '0';
            } else if ((c >= 'A') && (c <= 'F')) {
                value = c - 'A' + 10;
            } else if ((c >= 'a') && (c <= 'f')) {
                value = c - 'a' + 10;
            } else {
                value = -1;
            }

            if (value >= 0) {

                if (first) {

                    byteArray[length] = (byte) (value << 4);

                } else {

                    byteArray[length] |= value;
                    length++;
                }

                first = !first;
            }

            if (length >= byteArray.length) {
                break;
            }
        }

        return length;
    }

    /**
     * Converts the HEX string to byte array.
     *
     * @param hexString the HEX string.
     * @return the byte array.
     */
    private byte[] toByteArray(String hexString) {

        byte[] byteArray = null;
        int count = 0;
        char c = 0;
        int i = 0;

        boolean first = true;
        int length = 0;
        int value = 0;

        // Count number of hex characters
        for (i = 0; i < hexString.length(); i++) {

            c = hexString.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a'
                    && c <= 'f') {
                count++;
            }
        }

        byteArray = new byte[(count + 1) / 2];
        for (i = 0; i < hexString.length(); i++) {

            c = hexString.charAt(i);
            if (c >= '0' && c <= '9') {
                value = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                value = c - 'A' + 10;
            } else if (c >= 'a' && c <= 'f') {
                value = c - 'a' + 10;
            } else {
                value = -1;
            }

            if (value >= 0) {

                if (first) {

                    byteArray[length] = (byte) (value << 4);

                } else {

                    byteArray[length] |= value;
                    length++;
                }

                first = !first;
            }
        }

        return byteArray;
    }

    /**
     * Converts the byte array to HEX string.
     *
     * @param buffer the buffer.
     * @return the HEX string.
     */
    private String toHexString(byte[] buffer) {

        String bufferString = "";

        if (buffer != null) {

            for (int i = 0; i < buffer.length; i++) {

                String hexChar = Integer.toHexString(buffer[i] & 0xFF);
                if (hexChar.length() == 1) {
                    hexChar = "0" + hexChar;
                }

                bufferString += hexChar.toUpperCase(Locale.US) + " ";
            }
        }

        return bufferString;
    }

    /**
     * Converts the error code to string.
     *
     * @param errorCode the error code.
     * @return the error code string.
     */
    private String toErrorCodeString(int errorCode) {

        String errorCodeString = null;

        switch (errorCode) {
            case Result.ERROR_SUCCESS:
                errorCodeString = "The operation completed successfully.";
                break;
            case Result.ERROR_INVALID_COMMAND:
                errorCodeString = "The command is invalid.";
                break;
            case Result.ERROR_INVALID_PARAMETER:
                errorCodeString = "The parameter is invalid.";
                break;
            case Result.ERROR_INVALID_CHECKSUM:
                errorCodeString = "The checksum is invalid.";
                break;
            case Result.ERROR_INVALID_START_BYTE:
                errorCodeString = "The start byte is invalid.";
                break;
            case Result.ERROR_UNKNOWN:
                errorCodeString = "The error is unknown.";
                break;
            case Result.ERROR_DUKPT_OPERATION_CEASED:
                errorCodeString = "The DUKPT operation is ceased.";
                break;
            case Result.ERROR_DUKPT_DATA_CORRUPTED:
                errorCodeString = "The DUKPT data is corrupted.";
                break;
            case Result.ERROR_FLASH_DATA_CORRUPTED:
                errorCodeString = "The flash data is corrupted.";
                break;
            case Result.ERROR_VERIFICATION_FAILED:
                errorCodeString = "The verification is failed.";
                break;
            default:
                errorCodeString = "Error communicating with reader.";
                break;
        }

        return errorCodeString;
    }

    /**
     * COnverts Hex to ASCII
     *
     */
    private String hexToASCII(String hexValue) {
        StringBuilder output = new StringBuilder("");
        if (hexValue.length() % 2 != 0) {
        }
        for (int i = 0; i < hexValue.length(); i += 2) {
            String str = hexValue.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    class PowerOn extends AsyncTask<String, String, String> {

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {

            try {
                Log.e("Power On: ", "" + mReader.piccPowerOn(timeout, cardType));

            } catch (Exception e) {
                Log.e("PICCError: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result); //To change body of generated methods, choose Tools | Templates.
            fidCommandApdu = toByteArray(fidApduString);

            Log.i("FIDAPDU Status: ", "" + mReader.piccTransmit(timeout, fidCommandApdu));
        }

    }
}
