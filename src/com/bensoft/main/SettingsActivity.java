package com.bensoft.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by Benson on 3/10/2015.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //String ip = SP.getString("ip", "NULL");
      //  Config.ip = SP.getString("ip", "NULL");
       // Log.e("IP",Config.ip );
        String language = SP.getString("language","NULL");
       // Toast.makeText(getApplicationContext(),Config.ip,Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),language,Toast.LENGTH_SHORT).show();
    }
}
