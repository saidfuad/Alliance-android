/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.yield_estimate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.bensoft.main.R;

/**
 *
 * @author Benson
 */
public class YieldEstimateActivity3 extends Activity implements View.OnClickListener {
	private EditText ertNumofPlants;
	private EditText etNumofBalls;
	private EditText etLeftRow;
	private EditText etRightRow;
	private Button bBack;
	private Button bNext;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_yield_estimate3);
        initView();
        initListeners();
    }

    private void initView() {
    ertNumofPlants = (EditText)findViewById( R.id.ertNumofPlants );
		etNumofBalls = (EditText)findViewById( R.id.etNumofBalls );
		etLeftRow = (EditText)findViewById( R.id.etLeftRow );
		etRightRow = (EditText)findViewById( R.id.etRightRow );
		bBack = (Button)findViewById( R.id.bBack );
		bNext = (Button)findViewById( R.id.bNext );
    }

    private void initListeners() {
        bBack.setOnClickListener(this);
        bNext.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == bBack) {
            // Handle clicks for bBack
        } else if (v == bNext) {
            // Handle clicks for bNext
        }
    }

}