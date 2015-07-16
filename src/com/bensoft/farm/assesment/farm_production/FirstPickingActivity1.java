/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment.farm_production;

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
public class FirstPickingActivity1 extends Activity  implements View.OnClickListener {

    private EditText etPickingDate;
    private EditText etGradeA;
    private EditText etGradeB;
    private EditText etGradeC;
    private Button bBack;
    private Button bNext;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_picking1);
        initView();
        initListeners();
    }

    private void initView() {
        etPickingDate = (EditText) findViewById(R.id.etPickingDate1);
        etGradeA = (EditText) findViewById(R.id.etGradeA);
        etGradeB = (EditText) findViewById(R.id.etGradeB);
        etGradeC = (EditText) findViewById(R.id.etGradeC);
        bBack = (Button) findViewById(R.id.bBack);
        bNext = (Button) findViewById(R.id.bNext);
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
