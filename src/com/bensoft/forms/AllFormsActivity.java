package com.bensoft.forms;


import com.bensoft.farm.assesment.AllFarmAssesmentActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bensoft.main.DemoPlotEvalutationActivity;
import com.bensoft.main.R;


public class AllFormsActivity extends Activity {
    private Button PlotEvaluation;
    private Button JointDemoPlotEvaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_forms);

        initView();
        initListeners();
    }


    private void initView() {
        PlotEvaluation = (Button)findViewById(R.id.bPlotEval);
        JointDemoPlotEvaluation = (Button)findViewById(R.id.bJDemoPlotEval);
         SaveFarmID(getApplicationContext(), "pid", "159");
    }

    
    public static void SaveFarmID(Context ctx, String Key, String value) {
        ctx.getSharedPreferences("mypref", ctx.MODE_PRIVATE)
                .edit().putString(Key, value).commit();
    }
    
    private void initListeners() {
        PlotEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent formIntent = new Intent(getApplicationContext(),AllFarmAssesmentActivity.class);
                startActivity(formIntent);
            }
        });


        JointDemoPlotEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fingerFiveIntent = new Intent(getApplicationContext(),JDemoPlotEval.class);
                startActivity(fingerFiveIntent);
            }
        });
    }

}
