package com.bensoft.main;

import com.bensoft.utils.ConnectionDetector;
import com.bensoft.utils.DatabaseHandler;
import com.bensoft.utils.GPSTracker;
import com.bensoft.entities.Countries;
import com.bensoft.entities.Wards;
import com.bensoft.entities.Villages;
import com.bensoft.entities.SubVillages;
import com.bensoft.entities.Regions;
import com.bensoft.entities.Districts;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 12/19/2014.
 */
public class FarmerRegistrationActivity extends Activity {

    EditText fname, lname, id_number, phone, email, post;
    Spinner country, region, district, ward, gender, village, subvillage;
    Button done;

    String sfname, slname, sgender, sdistrict, sregion, scountry, sward, ssubvillage, semail, svillage, spost;
    String sid_number, sphone,sestfarmarea;
    List<String> list;
    ArrayAdapter<String> dataAdapter;
    Boolean isInternetPresent;
    ConnectionDetector cd;
    DatabaseHandler db;
    private String fingerPath;
    private String vid,svid;
    private String imagePath;
    private List<String> list_country, list_region, list_district, list_ward, list_village, list_subvillage;
    private ArrayAdapter<MyData> dataAdapter1;
    private ArrayAdapter<MyData> dataAdapter2;
    private ArrayAdapter<MyData> dataAdapter3;
    private ArrayAdapter<MyData> dataAdapter4;
    private ArrayAdapter<MyData> dataAdapter5;
    private ArrayAdapter<MyData> dataAdapter6;
    private MyData[] items;
    private MyData[] items2;
    private MyData[] items3;
    private MyData[] items4;
    private MyData[] items5;
    private MyData[] items6;
    private double longt, lat;
    private GPSTracker gpsTracker;
    private String cid;
    private Date d1;
    private SimpleDateFormat sdf;
    private EditText estfarmarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        initiate();
        loadVillages();

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sgender = gender.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                svillage = village.getItemAtPosition(position).toString();
                MyData d = items5[position];
                vid = d.getValue();
                //ToastUtil.showToast(getApplicationContext(), d.getValue());

                //   loadWards(s);
                loadSubVillages(vid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subvillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ssubvillage = subvillage.getItemAtPosition(position).toString();
                MyData d = items6[position];
                svid = d.getValue();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (cd.isConnectingToInternet() == true) {
                sfname = fname.getText().toString();
                slname = lname.getText().toString();
                sgender = gender.getSelectedItem().toString();
                sid_number = id_number.getText().toString();
                sphone = phone.getText().toString();
                semail = email.getText().toString();
                spost = post.getText().toString();
                sestfarmarea = estfarmarea.getText().toString();

                int bobo = 0;
                int bobo2 = 0;
                svillage = village.getSelectedItem().toString();
                ssubvillage = subvillage.getSelectedItem().toString();

                // Toast.makeText(getApplicationContext(), "Length: "+sfname.length(), Toast.LENGTH_SHORT).show();
                if (sfname.length() == 0 || slname.length() == 0 || sid_number.length() == 0 || sphone.length() == 0 || semail.length() == 0 || spost.length() == 0|| sestfarmarea.length() == 0) {

                    svillage = village.getSelectedItem().toString();
                    ssubvillage = subvillage.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                } else {
                    launchUploadActivity(true);
                }

                /*   } else {

                 if (gpsTracker.canGetLocation) {
                 // do {
                 lat = gpsTracker.getLatitude();
                 longt = gpsTracker.getLongitude();
                 // }while(lat==0.0 || longt==0.0);
                 sfname = fname.getText().toString();
                 slname = lname.getText().toString();
                 sid_number = id_number.getText().toString();
                 sphone = phone.getText().toString();

                 svillage = village.getSelectedItem().toString();
                 ssubvillage = subvillage.getSelectedItem().toString();
                 semail = email.getText().toString();
                 spost = post.getText().toString();

                 if (sfname.length()==0  || slname.length()==0 || sid_number.length()==0 || sphone.length()==0 || semail.length()==0 || spost.length()==0) {
                 Toast.makeText(getApplicationContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                 } else {
                 try {
                 //save to offline database
                 Intent i = new Intent(getApplicationContext(), UploadActivity.class);
                 fingerPath = getPrefrence(getApplicationContext(), "fingerPath");
                 imagePath = getPrefrence(getApplicationContext(), "imagePath");
                 DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                 // Inserting Farmers
                 Log.e("Insert: ", "Inserting ..");
                 int id_no = Integer.parseInt(sid_number);
                 //add farmers

                 db.addFarmer(new Farmers(sfname, slname, sgender, id_no, sphone, semail, spost, svillage, ssubvillage, imagePath, String.valueOf(lat), String.valueOf(longt),sdf.format(d1), cid));
                 Toast.makeText(getApplicationContext(), "Data saved offline", Toast.LENGTH_SHORT).show();

                 // Reading all Farmers
                 Log.e("Reading: ", "Reading all farmers..");
                 List<Farmers> farmers = db.getAllFarmers();
                 int count = db.getFarmersCount();
                 Log.e("Count: ", String.valueOf(count));
                 for (Farmers cn : farmers) {
                 String log = "Id: " + cn.getID() + " ,FName: " + cn.getFName() + " ,LName: " + cn.getLName() + " ,Gender: " + cn.getGender() + " ,ID number: " + cn.getIDNO() + " ,Phone: " + cn.getPhoneNumber() + " ,Email: " + cn.getEmail() + " ,Post: " + cn.getPost() +
                 " ,ImagePath: " + cn.getPicPath() + " Date: " + cn.getRegDate() + " Lat: " + cn.getLat() + "Long: " + cn.getLong();
                 // Writing Farmers to log
                 Log.d(" ", log);
                 }
                 db.close();
                 sfname = "";
                 slname = "";
                 sgender = "";
                 sid_number = "";
                 sphone = "";
                 semail = "";
                 spost = "";
                 svillage = "";
                 //fingerPath = null;
                 ToastUtil.showToast(getApplicationContext(), "Data Saved Offline");
                 Intent newf = new Intent(getApplicationContext(), DashBoardActivity.class);
                 newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(newf);
                 } catch (Exception e) {
                 Log.e("Exception:", e.getMessage());
                 }
                 }

                 //
                 Log.d("Name:", sfname);
                 Log.e("lat: ", String.valueOf(lat));
                 Log.e("long: ", String.valueOf(longt));
                 Intent r = new Intent("android.location.GPS_ENABLED_CHANGE");
                 r.putExtra("enabled", false);
                 sendBroadcast(r);
                 gpsTracker.stopUsingGPS();
                 } else {
                 //turn on gps
                 gpsTracker.showSettingsAlert(FormActivity.this);
                 lat = gpsTracker.getLatitude();
                 longt = gpsTracker.getLongitude();

                 sfname = fname.getText().toString();
                 slname = lname.getText().toString();
                 sid_number = id_number.getText().toString();
                 sphone = phone.getText().toString();
                 semail = email.getText().toString();
                 spost = post.getText().toString();

                 if (sfname.length()==0  || slname.length()==0 || sid_number.length()==0 || sphone.length()==0 || semail.length()==0 || spost.length()==0) {
                 Toast.makeText(getApplicationContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                 } else {
                 try {
                 //save to offline database
                 Intent i = new Intent(getApplicationContext(), UploadActivity.class);
                 // Receiving the data from previous activity

                 // image or video path that is captured in previous activity
                 fingerPath = getPrefrence(getApplicationContext(), "fingerPath");
                 imagePath = getPrefrence(getApplicationContext(), "imagePath");
                 DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                 // Inserting Farmers
                 Log.e("Insert: ", "Inserting ..");
                 int id_no = Integer.parseInt(sid_number);
                 //add farmers

                 db.addFarmer(new Farmers(sfname, slname, sgender, id_no, sphone, semail, spost, svillage, ssubvillage, imagePath, String.valueOf(lat), String.valueOf(longt),sdf.format(d1), cid));
                 Toast.makeText(getApplicationContext(), "Data saved offline", Toast.LENGTH_SHORT).show();

                 // Reading all Farmers
                 Log.e("Reading: ", "Reading all farmers..");
                 List<Farmers> farmers = db.getAllFarmers();
                 int count = db.getFarmersCount();
                 Log.e("Count: ", String.valueOf(count));
                 for (Farmers cn : farmers) {
                 String log = "Id: " + cn.getID() + " ,FName: " + cn.getFName() + " ,LName: " + cn.getLName() + " ,Gender: " + cn.getGender() + " ,ID number: " + cn.getIDNO() + " ,Phone: " + cn.getPhoneNumber() + " ,Email: " + cn.getEmail() + " ,Post: " + cn.getPost() +" ,Village: " + cn.getVillage() + " ,ImagePath: " + cn.getPicPath() + " Date: " + cn.getRegDate() + " Lat: " + cn.getLat() + "Long: " + cn.getLong();
                 // Writing Farmers to log
                 Log.d(" ", log);
                 }
                 db.close();
                 sfname = "";
                 slname = "";
                 sgender = "";
                 sid_number = "";
                 sphone = "";
                 semail = "";
                 spost = "";
                 svillage = "";
                 //fingerPath = null;
                 ToastUtil.showToast(getApplicationContext(), "Data Saved Offline");
                 Intent newf = new Intent(getApplicationContext(), DashBoardActivity.class);
                 newf.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(newf);
                 } catch (Exception e) {
                 Log.e("Exception:", e.getMessage());
                 }
                 }

                 //
                 Log.d("Name:", sfname);
                 Log.e("lat: ", String.valueOf(lat));
                 Log.e("long: ", String.valueOf(longt));
                 Intent r = new Intent("android.location.GPS_ENABLED_CHANGE");
                 r.putExtra("enabled", false);
                 sendBroadcast(r);
                 gpsTracker.stopUsingGPS();

                 }
                 }*/
            }
        }
        );

    }

    public static String getPrefrence(Context ctx, String key) {
        SharedPreferences pref = ctx.getSharedPreferences(
                "mypref", ctx.MODE_PRIVATE);
        String result = pref.getString(key, null);
        return result;
    }

    private void loadCountries(String s) {
        Log.e("Loading ", "Countries...");
        db = new DatabaseHandler(getApplicationContext());
        List<Countries> countriesList = db.getAllDynamicCountries(s);

        dataAdapter1 = new ArrayAdapter<MyData>(getBaseContext(),
                android.R.layout.simple_spinner_item);
        items = new MyData[countriesList.size()];
        int i = 0;
        //for (int i = 0; i < countriesList.size(); i++) {
        for (Countries c : countriesList) {
            if (i < countriesList.size()) {
                items[i] = new MyData(c.getCountryName(), c.getCountryID());
                Log.e("Mydata:", items[i].getValue());
            }
            //dataAdapter1.add(new MyData(c.getCountryName(), c.getCountryID()));
            String log = "Country Id: " + c.getCountryID() + " ,Country name: " + c.getCountryName();
            Log.e(" ", log);
            i++;
        }
        //}
        //  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
        //          android.R.layout.simple_spinner_item);
        dataAdapter1.addAll(items);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter1.notifyDataSetChanged();
        country.setAdapter(dataAdapter1);
        db.close();
    }

    private void loadRegions(String d) {
        db = new DatabaseHandler(getApplicationContext());
        List<Regions> regionsList = db.getDynamicRegions(d);
        dataAdapter2 = new ArrayAdapter<MyData>(getBaseContext(),
                android.R.layout.simple_spinner_item);
        items2 = new MyData[regionsList.size()];
        int i = 0;
        //for (int i = 0; i < countriesList.size(); i++) {
        for (Regions r : regionsList) {
            if (i < regionsList.size()) {
                items2[i] = new MyData(r.getRegionName(), r.getRegionID());
                Log.e("Mydata:", items2[i].getValue());
            }
            //dataAdapter2.add(new MyData(r.getRegionName(), r.getRegionID()));
            String log = "Region Id: " + r.getRegionID() + " ,Region name: " + r.getRegionName();
            Log.e(" ", log);
            i++;
        }
        //}
        //  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
        //          android.R.layout.simple_spinner_item);
        dataAdapter2.addAll(items2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.notifyDataSetChanged();
        region.setAdapter(dataAdapter2);
        db.close();
    }

    private void loadDistricts(String d) {
        db = new DatabaseHandler(getApplicationContext());
        List<Districts> districtsList = db.getDynamicDistricts(d);
        dataAdapter3 = new ArrayAdapter<MyData>(getBaseContext(),
                android.R.layout.simple_spinner_item);
        items3 = new MyData[districtsList.size()];
        int i = 0;
        //for (int i = 0; i < countriesList.size(); i++) {
        for (Districts r : districtsList) {
            if (i < districtsList.size()) {
                items3[i] = new MyData(r.getDistrictName(), r.getDistrictID());
                Log.e("Mydata:", items3[i].getValue());
            }
            //dataAdapter2.add(new MyData(r.getRegionName(), r.getRegionID()));
            String log = "District Id: " + r.getDistrictID() + " ,District name: " + r.getDistrictName();
            Log.e(" ", log);
            i++;
        }
        //}
        //  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
        //          android.R.layout.simple_spinner_item);
        dataAdapter3.addAll(items3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.notifyDataSetChanged();
        district.setAdapter(dataAdapter3);
        db.close();
    }

    private void loadWards(String d) {
        try {
            db = new DatabaseHandler(getApplicationContext());
            List<Wards> wardsList = db.getDynamicWards(d);
            dataAdapter4 = new ArrayAdapter<MyData>(getBaseContext(),
                    android.R.layout.simple_spinner_item);
            items4 = new MyData[wardsList.size()];
            int i = 0;
            //for (int i = 0; i < countriesList.size(); i++) {
            for (Wards r : wardsList) {
                if (i < wardsList.size()) {
                    items4[i] = new MyData(r.getWardName(), r.getWardID());
                    Log.e("Mydata:", items4[i].getValue());
                }
                //dataAdapter2.add(new MyData(r.getRegionName(), r.getRegionID()));
                String log = "Ward Id: " + r.getWardID() + " ,Ward name: " + r.getWardName();
                Log.e(" ", log);
                i++;
            }
            //}
            //  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
            //          android.R.layout.simple_spinner_item);
            dataAdapter4.addAll(items4);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter4.notifyDataSetChanged();
            ward.setAdapter(dataAdapter4);
            db.close();
        } catch (Exception e) {
        }
    }

    private void loadVillages() {
        db = new DatabaseHandler(getApplicationContext());
        List<Villages> villagesList = db.getVillages();
        dataAdapter5 = new ArrayAdapter<MyData>(getBaseContext(),
                android.R.layout.simple_spinner_item);
        items5 = new MyData[villagesList.size()];
        int i = 0;
        for (Villages r : villagesList) {
            if (i < villagesList.size()) {
                items5[i] = new MyData(r.getVillageName(), r.getVillageID());
                Log.e("Mydata:", items5[i].getValue());
            }
            String log = "Village Id: " + r.getVillageID() + " ,Village name: " + r.getVillageName();
            Log.e(" ", log);
            i++;
        }
        //}
        //  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
        //          android.R.layout.simple_spinner_item);
        dataAdapter5.addAll(items5);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter5.notifyDataSetChanged();
        village.setAdapter(dataAdapter5);
        db.close();
    }

    private void loadSubVillages(String d) {
        db = new DatabaseHandler(getApplicationContext());
        List<SubVillages> subvillagesList = db.getDynamicSubVillages(d);
        dataAdapter6 = new ArrayAdapter<MyData>(getBaseContext(),
                android.R.layout.simple_spinner_item);
        items6 = new MyData[subvillagesList.size()];
        int i = 0;
        //for (int i = 0; i < countriesList.size(); i++) {
        for (SubVillages r : subvillagesList) {
            if (i < subvillagesList.size()) {
                items6[i] = new MyData(r.getSubVillageName(), r.getSubVillageID());
                Log.e("Mydata:", items6[i].getValue());
            }
            //dataAdapter2.add(new MyData(r.getRegionName(), r.getRegionID()));
            String log = "SubVillage Id: " + r.getSubVillageID() + " ,SubVillage name: " + r.getSubVillageName();
            Log.e(" ", log);
            i++;
        }
        //}
        //  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getBaseContext(),
        //          android.R.layout.simple_spinner_item);
        dataAdapter6.addAll(items6);
        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter6.notifyDataSetChanged();
        subvillage.setAdapter(dataAdapter6);
        db.close();
    }

    public void initiate() {

        fname = (EditText) findViewById(R.id.etFName);
        lname = (EditText) findViewById(R.id.etLName);
        gender = (Spinner) findViewById(R.id.spGender);
        id_number = (EditText) findViewById(R.id.etNum);
        phone = (EditText) findViewById(R.id.etPhone);
        email = (EditText) findViewById(R.id.etEmail);
        post = (EditText) findViewById(R.id.etPost);
        village = (Spinner) findViewById(R.id.spVillage);
        subvillage = (Spinner) findViewById(R.id.spSubVillage);
        estfarmarea = (EditText) findViewById(R.id.etEstFarmArea);
        done = (Button) findViewById(R.id.bDone);
       // country.setVisibility(View.INVISIBLE);
        // region.setVisibility(View.INVISIBLE);
        // district.setVisibility(View.INVISIBLE);
        //ward.setVisibility(View.INVISIBLE);
        cid = getPrefrence(getApplicationContext(), "cid");

        cd = new ConnectionDetector(getApplicationContext());
        gpsTracker = new GPSTracker(getApplicationContext());
        // isInternetPresent = cd.isConnectingToInternet();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        d1 = new Date();

    }

    private void launchUploadActivity(boolean isImage) {
        //get location

        if (gpsTracker.canGetLocation) {
            lat = gpsTracker.getLatitude();
            longt = gpsTracker.getLongitude();

            Intent i = new Intent(getApplicationContext(), UploadActivity.class);
            fingerPath = getPrefrence(getApplicationContext(), "fingerPath");
            imagePath = getPrefrence(getApplicationContext(), "imagePath");
            //  Log.d("filePathForm: ", fingerPath.toString());

            //imagePath = intent.getStringExtra("imagePath");
            // i.putExtra("fingerPath", fingerPath);
            i.putExtra("imagePath", imagePath);
            i.putExtra("isImage", isImage);
            i.putExtra("fname", sfname);
            i.putExtra("lname", slname);
            i.putExtra("gender", sgender);
            i.putExtra("id_no", sid_number);
            i.putExtra("phone", sphone);
            i.putExtra("email", semail);
            i.putExtra("post", spost);
            i.putExtra("village", svillage);
            i.putExtra("vid",vid);
            i.putExtra("subvillage", ssubvillage);
            i.putExtra("svid", svid);
            i.putExtra("estfarmarea", sestfarmarea);
            i.putExtra("lat", lat);
            i.putExtra("longt", longt);
            i.putExtra("reg_date", sdf.format(d1));
            i.putExtra("cid", cid);

            Log.e("lat: ", String.valueOf(lat));
            Log.e("long: ", String.valueOf(longt));
            gpsTracker.stopUsingGPS();
            startActivity(i);
        } else {
            //turn on gps
            gpsTracker.showSettingsAlert(FarmerRegistrationActivity.this);
            lat = gpsTracker.getLatitude();
            longt = gpsTracker.getLongitude();

            Intent i = new Intent(getApplicationContext(), UploadActivity.class);
            // image or video path that is captured in fingerprint activity
            fingerPath = getPrefrence(getApplicationContext(), "fingerPath");
            imagePath = getPrefrence(getApplicationContext(), "imagePath");

            //imagePath = intent.getStringExtra("imagePath");
            i.putExtra("fingerPath", fingerPath);
            i.putExtra("imagePath", imagePath);
            i.putExtra("isImage", isImage);
            i.putExtra("fname", sfname);
            i.putExtra("lname", slname);
            i.putExtra("gender", sgender);
            i.putExtra("id_no", sid_number);
            i.putExtra("phone", sphone);
            i.putExtra("email", semail);
            i.putExtra("post", spost);
            i.putExtra("village", svillage);
            i.putExtra("subvillage", ssubvillage);
            i.putExtra("estfamrarea", sestfarmarea);
            i.putExtra("lat", lat);
            i.putExtra("longt", longt);
            i.putExtra("reg_date", sdf.format(d1));
            Log.e("lat: ", String.valueOf(lat));
            Log.e("long: ", String.valueOf(longt));
            gpsTracker.stopUsingGPS();
            Intent r = new Intent("android.location.GPS_ENABLED_CHANGE");
            r.putExtra("enabled", false);
            sendBroadcast(r);
            startActivity(i);
        }
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
}
