package com.bensoft.utils;

import com.bensoft.utils.Config;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by user on 12/27/2014.
 */
public class ConnectionDetector {

    private Context _context;
    private boolean isReachable = false;
    private InputStream is = null;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        int responseCode = 0;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final ConnectivityManager connMgr = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = connMgr.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            try {
                Log.e("", Config.CONNECTION_CHECK);
                URL myUrl = new URL(Config.CONNECTION_CHECK);
                URLConnection connection = myUrl.openConnection();
                connection.setConnectTimeout(3000);
                connection.connect();
                isReachable = true;
            } catch (Exception e) {
                isReachable = false;
                Log.e("Connection Detector", String.valueOf(isReachable));
            }
        }

        Log.e("HOst: ", String.valueOf(isReachable));
        return isReachable;

    }
}
