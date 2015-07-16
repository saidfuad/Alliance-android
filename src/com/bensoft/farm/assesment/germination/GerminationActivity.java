/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.germination;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.bensoft.main.R;

/**
 *
 * @author Benson
 */
public class GerminationActivity extends Activity implements OnClickListener {

    private EditText etGermDate;
    private EditText etRemarks;
    private Button bBack;
    private Button bSave;
    private final static String FORM_TYPE_ID = "11";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_good_rains);
        initView();
        initListeners();
    }

    private void initView() {
        etGermDate = (EditText) findViewById(R.id.etGermDate);
        etRemarks = (EditText) findViewById(R.id.etRemarks);
        bBack = (Button) findViewById(R.id.bBack);
        bSave = (Button) findViewById(R.id.bSave);
    }

    private void initListeners() {
        bBack.setOnClickListener(this);
        bSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == bBack) {
            // Handle clicks for bBack
        } else if (v == bSave) {
            // Handle clicks for bNext
        }
    }

}
