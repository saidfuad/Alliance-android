package com.bensoft.forms;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.bensoft.main.R;


public class JDemoPlotEval extends Activity {
    private Button fingerOne,fingerTwo,fingerThree,fingerFour,fingerFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jdemo_plot);

        initView();
        initListeners();
    }


    private void initView() {
        fingerOne = (Button)findViewById(R.id.bFingerOne);
        fingerTwo = (Button)findViewById(R.id.bFingerTwo);
        fingerThree = (Button)findViewById(R.id.bFingerThree);
        fingerFour = (Button)findViewById(R.id.bFingerFour);
        fingerFive = (Button)findViewById(R.id.bFingerFive);
    }

    private void initListeners() {

        fingerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fingerOneIntent = new Intent(getApplicationContext(),FingerOne.class);
                startActivity(fingerOneIntent);
            }
        });

        fingerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fingerTwoIntent = new Intent(getApplicationContext(),FingerTwo.class);
                startActivity(fingerTwoIntent);
            }
        });

        fingerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fingerThreeIntent = new Intent(getApplicationContext(),FingerThree.class);
                startActivity(fingerThreeIntent);
            }
        });

        fingerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fingerFourIntent = new Intent(getApplicationContext(),FingerFour.class);
                startActivity(fingerFourIntent);
            }
        });

        fingerFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fingerFiveIntent = new Intent(getApplicationContext(),FingerFive.class);
                startActivity(fingerFiveIntent);
            }
        });

    }

}
