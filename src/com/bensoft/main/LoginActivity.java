package com.bensoft.main;

import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.Config;
import com.bensoft.entities.Companies;
import com.bensoft.entities.Users;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bensoft.utils.ToastUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.client.methods.HttpGet;

public class LoginActivity extends Activity {

    Button btnLogin;
    Spinner company;
    EditText inputPassword;
    TextView loginErrorMsg;

    EditText inputUser;
    private DatabaseHandler db;
    private ArrayAdapter<MyData> dataAdapter1;
    private MyData[] items;
    private ConnectionDetector cd;
    private String co;
    ImageView image;
    CheckBox saveLoginCheckBox;
    private TextWatcher watcher;
    private String user;
    private String password;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Importing all assets like buttons, text fields
        dialog = new ProgressDialog(LoginActivity.this);
        //company = (Spinner) findViewById(R.id.sCompany);
        /// company.setVisibility(View.GONE);
        inputUser = (EditText) findViewById(R.id.etLoginUser);
        inputPassword = (EditText) findViewById(R.id.etLoginPassword);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.cbSaveLoginCheckBox);
        btnLogin = (Button) findViewById(R.id.bLogin);
        loginErrorMsg = (TextView) findViewById(R.id.tvLoginError);
        image = (ImageView) findViewById(R.id.imageView);
        //image.setImageResource(R.drawable.login_image);
        //load companies
        //loadCompanies();
        SavePrefrence(getApplicationContext(), "language", "swa");
        /* company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         MyData d = items[position];
         // co = d.getValue().toString();
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
         });*/
        co = String.valueOf(1);
        /*  inputUser.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
         //  ToastUtil.showToast(getApplicationContext(),"beforeTextChanged"+s.toString());
         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
         // ToastUtil.showToast(getApplicationContext(),"onTextChanged");

         }

         @Override
         public void afterTextChanged(Editable s) {
         //ToastUtil.showToast(getApplicationContext(),"afterTextChanged");*/
        try {/*
             user = inputUser.getText().toString().trim();
             Log.e("BOx1:",user);
             Log.e("Saved Usr: ",getPreference(getApplicationContext(),"username").toString());
             Log.e("Saved Pwd: ",getPreference(getApplicationContext(),"pwd").toString());
             if(co != null && user != "") {


             if (getPreference(getApplicationContext(), "username").toString().trim().equals(user)) {
             Log.e("Match true", "");*/

            if (getPreference(getApplicationContext(), "username").toString().length() > 0 && getPreference(getApplicationContext(), "pwd").toString().length() > 0) {
                inputUser.setText(getPreference(getApplicationContext(), "username").toString());
            }
            inputPassword.setText(getPreference(getApplicationContext(), "pwd").toString());
            //}
            //  }
        } catch (Exception e) {
        }
        /*
         }
         });*/

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try {
                    user = inputUser.getText().toString();
                    password = inputPassword.getText().toString();
                    
                    // check if offline database is empty
                    Log.e("User: ", user);
                    Log.e("Pwd: ", password);

                    if (co != null && user != "" && password != "") {
                        db = new DatabaseHandler(getApplicationContext());
                        db.getUserCount();
                        loginErrorMsg.setText("");
                        if (db.getUserCount() > 0) {

                            String user_temp;
                            String user_pwd_temp;

                            if (db.validateUser(user, password, co) == true) {
                                String user_id = db.getUserID(user);
                                String vid = db.getUserVillageID(user_id);
                                SavePrefrence(getApplicationContext(), "user_id", user_id);
                                SavePrefrence(getApplicationContext(), "vid", vid);
                                //store in preferences
                                SavePrefrence(getApplicationContext(), "cid", co);
                                if (saveLoginCheckBox.isChecked()) {
                                    SavePrefrence(getApplicationContext(), "username", user.trim());
                                    SavePrefrence(getApplicationContext(), "pwd", password.trim());
                                } else {
                                    removePrefrence(getApplicationContext(), "username");
                                    removePrefrence(getApplicationContext(), "pwd");
                                }

                                // Close all views before launching Dashboard
                                db.close();
                                Intent dashboard = new Intent(getApplicationContext(), DashBoardActivity.class);
                                dashboard.putExtra("cid", co);
                                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //  if (dialog.isShowing()) {
                                //    dialog.dismiss();
                                //}
                                startActivity(dashboard);

                                // Close Login Screen
                                finish();
                            } else {
                                // Error in login
                                loginErrorMsg.setText("Incorrect username/password");
                            }
                        } else {
                            // Error in login
                            loginErrorMsg.setText("Offline database is empty.Sync to Server");
                        }
                    }

                } catch (Exception ex) {
                    Log.e("LOGIN Ex: ", ex.getMessage());
                }
            }
        });

    }

    public static void SavePrefrence(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("mypref", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }

    public static void removePrefrence(Context ctx, String Key) {
        ctx.getSharedPreferences("mypref", ctx.MODE_PRIVATE)
                .edit().remove(Key);
    }

    public static String getPreference(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    public void loadCompanies() {
        db = new DatabaseHandler(getApplicationContext());
        List<Companies> companiesList = db.getAllCompanies();
        //add to spinner
        dataAdapter1 = new ArrayAdapter<MyData>(getBaseContext(),
                android.R.layout.simple_spinner_item);

        items = new MyData[companiesList.size()];
        int i = 0;
        //for (int i = 0; i < countriesList.size(); i++) {
        for (Companies c : companiesList) {
            if (i < companiesList.size()) {
                items[i] = new MyData(c.getCoName(), c.getCoID());
                Log.e("Mydata:", items[i].getValue());
            }
            //dataAdapter1.add(new MyData(c.getCountryName(), c.getCountryID()));
            String log = "Company Id: " + c.getCoID() + " ,Company name: " + c.getCoName();
            Log.e(" ", log);
            i++;
        }
        //}
        //  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
        //          android.R.layout.simple_spinner_item);
        dataAdapter1.addAll(items);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter1.notifyDataSetChanged();
        company.setAdapter(dataAdapter1);
        db.close();
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

    private class dowloadAllCompanies extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    Log.e("COS:", Config.FARM_GET_ALL_COMPANIES_URL);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet httppost = new HttpGet(Config.FARM_GET_ALL_COMPANIES_URL);

                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    finish();
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

                    Log.e("Company Objects from server:", result);
                } catch (Exception e) {
                    Log.e("Ex Download Companies: ", e.toString());
                }

                try {
                    JSONArray JA = new JSONArray(result);
                    JSONObject json;
                    String[] company_id = new String[JA.length()];
                    String[] company_name = new String[JA.length()];
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    for (int i = 0; i < JA.length(); i++) {
                        json = JA.getJSONObject(i);
                        company_id[i] = json.getString("company_id");
                        company_name[i] = json.getString("company_name");

                    }
                    //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                    db.clearCompanies();
                    // List<InputsFromServer> inputs = db.getAllInputs();
                    for (int i = 0; i < company_id.length; i++) {
                        //add to database

                        db.addCompany(new Companies(company_id[i], company_name[i]));
                        //list1.add(input_cat[i]);
                        // list2.add(input_type[i]);
                    }
                    List<Companies> companies = db.getAllCompanies();
                    int count = db.getCompanyCount();
                    Log.d("Count: ", String.valueOf(count));
                    for (Companies cn : companies) {
                        String log = "Company Id: " + cn.getCoID() + " ,Cat: " + cn.getCoName();
                        // Writing Farmers to log
                        Log.e("Offline database:", log);
                    }
                    db.close();

                } catch (Exception e) {

                    Log.e("ExceptionCompany: ", e.toString());

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
            //load companies
            // loadCompanies();
            //download users
            try {
                new dowloadAllUsers().execute();
            } catch (Exception ex) {
                Log.e("ExceptionUsers", ex.getMessage());
            }
        }
    }

    private class dowloadAllUsers extends AsyncTask<Void, Void, Void> {

        DatabaseHandler db;

        @Override
        protected void onPreExecute() {
            if (!dialog.isShowing()) {
                dialog.setMessage("Downloading data from server, please wait.");
                dialog.setCancelable(true);
                dialog.show();
            }
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (cd.isConnectingToInternet()) {
                //download all inputs from server
                InputStream is = null;
                try {
                    Log.e("Users: ", Config.FARM_GET_ALL_USERS_URL);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet httppost = new HttpGet(Config.FARM_GET_ALL_USERS_URL);

                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();
                } catch (Exception e) {
                    Log.e("Exception: ", e.toString());
                    //Toast.makeText(getApplicationContext(), "Invalid IP Address",Toast.LENGTH_LONG).show();
                    finish();
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
                    Log.e("Objects from server:", result);
                } catch (Exception e) {
                    Log.e("Ex Download Users: ", e.toString());
                }

                try {
                    JSONArray JA = new JSONArray(result);
                    JSONObject json;
                    String[] user_id = new String[JA.length()];
                    String[] user_name = new String[JA.length()];
                    String[] user_pwd = new String[JA.length()];
                    String[] village_id = new String[JA.length()];
                    String[] company_id = new String[JA.length()];

                    db = new DatabaseHandler(getApplicationContext());
                    for (int i = 0; i < JA.length(); i++) {
                        json = JA.getJSONObject(i);
                        user_id[i] = json.getString("user_id");
                        user_name[i] = json.getString("username");
                        user_pwd[i] = json.getString("password");
                        village_id[i] = json.getString("village_id");
                        company_id[i] = json.getString("company_id");

                    }
                    //Toast.makeText(getApplicationContext(), "sss",Toast.LENGTH_LONG).show();
                    db.clearUsers();
                    // List<InputsFromServer> inputs = db.getAllInputs();
                    for (int i = 0; i < user_id.length; i++) {
                        //add to database

                        db.addUser(new Users(user_id[i], user_name[i], user_pwd[i], village_id[i], company_id[i]));
                        //list1.add(input_cat[i]);
                        // list2.add(input_type[i]);
                    }

                } catch (Exception e) {

                    Log.e("ExceptionUsers: ", e.toString());

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Connection to Host Computer", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                int count = db.getUserCount();
                if (count > 0) {
                    Log.e("Users Count: ", String.valueOf(count));
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //Toast.makeText(getApplicationContext(),"Data Successfully Downloaded",Toast.LENGTH_LONG).show();
                                //show alert
                                showAlert("Download Complete");
                            }
                        });
                    }

                }

                List<Users> users1 = db.getAllUsers();

                for (Users cn : users1) {
                    String log = "User Id: " + cn.getUserID() + " ,UserName: " + cn.getUserName() + " ,Cid: " + cn.getCoID();
                    // Writing Farmers to log
                    Log.e("Offline database:", log);
                }
                db.close();

            } catch (Exception ex) {

            }
        }
    }

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected Identify single menu
     * item by it's id
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sync:
                //internet connection checker
                cd = new ConnectionDetector(getApplicationContext());
                if (cd.isConnectingToInternet()) {
                    //new dowloadAllCompanies().execute();
                    try {
                        new dowloadAllCompanies().execute();
                    } catch (Exception ex) {
                        Log.e("ExceptionCompanies: ", ex.getMessage());
                    }
                } else {
                    ToastUtil.showToast(getApplicationContext(), "No Connection to Host Computer!");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("NOTICE")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
