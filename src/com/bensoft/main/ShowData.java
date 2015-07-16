package com.bensoft.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowData extends Activity {
    ImageView image;
    TextView tName, tId, tPhone, tCounty, tDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        initiate();
    }

    private void initiate() {
        image = (ImageView) findViewById(R.id.imgPreview);
        tName = (TextView) findViewById(R.id.tName);
        tId = (TextView) findViewById(R.id.tId);
        tPhone = (TextView) findViewById(R.id.tPhone);
        tCounty = (TextView) findViewById(R.id.tCounty);
        tDistrict = (TextView) findViewById(R.id.tDistrict);

        Intent intent = getIntent();
        Bitmap photo= intent.getParcelableExtra("photo");
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        String phone = intent.getStringExtra("phone");
        String county = intent.getStringExtra("county");
        String district = intent.getStringExtra("district");

        image.setImageBitmap(photo);
        tName.setText(name);
        tId.setText(id);
        tPhone.setText(phone);
        tCounty.setText(county);
        tDistrict.setText(district);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_data, menu);
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
}
