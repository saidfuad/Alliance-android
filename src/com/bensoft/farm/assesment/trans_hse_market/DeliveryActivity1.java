/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.trans_hse_market;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.bensoft.main.R;

/**
 *
 * @author Benson
 */
public class DeliveryActivity1 extends Activity implements View.OnClickListener {
	private EditText etDeliveryDate1;
	private EditText etMoneyOut;
	private Spinner spTransMode;
	private Button bBack;
	private Button bNext;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_delivery1);
        initView();
        initListeners();
    }

    private void initView() {
        etDeliveryDate1 = (EditText)findViewById( R.id.etDeliveryDate1 );
		etMoneyOut = (EditText)findViewById( R.id.etMoneyOut );
		spTransMode = (Spinner)findViewById( R.id.spTransMode );
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
