package com.bensoft.main;

import com.bensoft.utils.DatabaseHandler;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.bensoft.farmersearch.ManualFarmerSearch;

/**
 * Created by user on 12/31/2014.
 */
public class FarmInputsActivity extends Activity {

    private DatabaseHandler db;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_inputs_main);
        db = new DatabaseHandler(this);
        displayListView();
    }

    private void displayListView() {

        Cursor cursor = db.getAllAssignedInputs();
        Toast.makeText(getApplicationContext(), "Count: " + cursor.getCount(), Toast.LENGTH_SHORT).show();

        // The desired columns to be bound
        String[] columns = new String[]{
            db.KEY_GEN_ID,
            db.KEY_INPUT_TYPE,
            db.KEY_INPUT_PRICE,
            db.KEY_INPUT_UNIT,
            db.KEY_INPUT_TOTAL,};

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
            R.id.tvGenID,
            R.id.tvInputType,
            R.id.tvInputPrice,
            R.id.tvInputUnit,
            R.id.tvInputTotal,};

        // create the adapter using the cursor pointing to the desired data 
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.activity_farm_inputs_details,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.lvFarmInputs);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                    int position, long id
            ) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String assInputCode
                        = cursor.getString(cursor.getColumnIndexOrThrow("assfinputs_id"));
                Toast.makeText(getApplicationContext(),
                        assInputCode, Toast.LENGTH_SHORT).show();

                Intent inputs = new Intent(FarmInputsActivity.this, ManualFarmerSearch.class);
                inputs.putExtra("class", "upload_inputs");
                inputs.putExtra("assfid", assInputCode);

                startActivity(inputs);

            }
        }
        );

        EditText myFilter = (EditText) findViewById(R.id.myFilter);

        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                    int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                    int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        }
        );

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return db.fetchInputsByGenID(constraint.toString());
            }
        });

    }
}
