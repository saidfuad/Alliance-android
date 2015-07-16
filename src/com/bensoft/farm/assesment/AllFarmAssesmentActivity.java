/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.farm.assesment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bensoft.farm.assesment.germination.GerminationActivity;
import com.bensoft.farm.assesment.herb_application.HerbApplicationActivity1;
import com.bensoft.farm.assesment.land_prep.LandClearActivity;
import com.bensoft.farm.assesment.planting.RowPlantingActivity;
import com.bensoft.farm.assesment.soil_fertility.MulchingActivity;
import com.bensoft.farm.assesment.weeding.WeedingActivity1;
import com.bensoft.main.R;

/**
 *
 * @author Benson
 */
public class AllFarmAssesmentActivity extends Activity implements OnClickListener {

    private Button bGoodRains;
    private Button bOtherCrops;
    private Button bLandPrep;
    private Button bPlanting;
    private Button bHerbApp;
    private Button bGermDate;
    private Button bRGT;
    private Button bSoilFert;
    private Button bWeed;
    private Button bTrap;
    private Button bScout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_plot_forms);

        initView();
        initListeners();
    }

    private void initView() {
        bGoodRains = (Button) findViewById(R.id.bGoodRains);
        bOtherCrops = (Button) findViewById(R.id.bOtherCrops);
        bLandPrep = (Button) findViewById(R.id.bLandPrep);
        bPlanting = (Button) findViewById(R.id.bPlanting);
        bHerbApp = (Button) findViewById(R.id.bHerbApp);
        bGermDate = (Button) findViewById(R.id.bGermDate);
        bRGT = (Button) findViewById(R.id.bRGT);
        bSoilFert = (Button) findViewById(R.id.bSoilFert);
        bWeed = (Button) findViewById(R.id.bWeed);
        bTrap = (Button) findViewById(R.id.bTrap);
        bScout = (Button) findViewById(R.id.bScout);
    }

    private void initListeners() {
        bGoodRains.setOnClickListener(this);
        bOtherCrops.setOnClickListener(this);
        bLandPrep.setOnClickListener(this);
        bPlanting.setOnClickListener(this);
        bHerbApp.setOnClickListener(this);
        bGermDate.setOnClickListener(this);
        bRGT.setOnClickListener(this);
        bSoilFert.setOnClickListener(this);
        bWeed.setOnClickListener(this);
        bTrap.setOnClickListener(this);
        bScout.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v == bGoodRains) {
            // Handle clicks for bGoodRains
            Intent good_rains = new Intent(getApplicationContext(), GoodRainsActivity.class);
            startActivity(good_rains);
        } else if (v == bOtherCrops) {
            // Handle clicks for bOtherCrops
            Intent other_crops = new Intent(getApplicationContext(), OtherCropsActivity.class);
            startActivity(other_crops);
        } else if (v == bLandPrep) {
            // Handle clicks for bLandPrep
            Intent land_clear = new Intent(getApplicationContext(), LandClearActivity.class);
            startActivity(land_clear);
        } else if (v == bPlanting) {
            // Handle clicks for bPlanting
            Intent planting = new Intent(getApplicationContext(), RowPlantingActivity.class);
            startActivity(planting);
        } else if (v == bHerbApp) {
            // Handle clicks for bHerbApp
            Intent planting = new Intent(getApplicationContext(), HerbApplicationActivity1.class);
            startActivity(planting);
        } else if (v == bGermDate) {
            // Handle clicks for bGermDate
        	Intent GermDate = new Intent(getApplicationContext(), GerminationActivity.class);
            startActivity(GermDate);
        } else if (v == bRGT) {
            // Handle clicks for bRGT
        	Intent Replanting = new Intent(getApplicationContext(), ReplantingActivity.class);
            startActivity(Replanting);
        } else if (v == bSoilFert) {
            // Handle clicks for bSoilFert
        	Intent SoilFertility = new Intent(getApplicationContext(), MulchingActivity.class);
            startActivity(SoilFertility);
        } else if (v == bWeed) {
            // Handle clicks for bWeed
        	Intent Weeding = new Intent(getApplicationContext(), WeedingActivity1.class);
            startActivity(Weeding);
        } else if (v == bTrap) {
            // Handle clicks for bTrap
        } else if (v == bScout) {
            // Handle clicks for bScout
        }
    }
}
