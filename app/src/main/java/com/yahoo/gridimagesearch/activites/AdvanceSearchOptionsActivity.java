package com.yahoo.gridimagesearch.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.gridimagesearch.R;
import com.yahoo.gridimagesearch.models.AdvanceFilterParam;

public class AdvanceSearchOptionsActivity extends AppCompatActivity {

    private Spinner spSize;
    private Spinner spColor;
    private Spinner spType;
    private EditText edSiteFilter;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search_options);
        setupViews();
        settingCustomerSpanner(R.array.imageSizeArray, spSize);
        settingCustomerSpanner(R.array.imageColorArray, spColor);
        settingCustomerSpanner(R.array.imageTypeArray, spType);

        AdvanceFilterParam result = (AdvanceFilterParam)getIntent().getSerializableExtra("queryParam");
        if(null != result){
            spSize.setSelection(result.getSizePosit());
            spColor.setSelection(result.getColorPosit());
            spType.setSelection(result.getTypePosit());
            edSiteFilter.setText(result.getSiteFilter());
        }



    }

    public void onSaveAction(View v){
        AdvanceFilterParam param = new AdvanceFilterParam();

        param.setSizePosit(spSize.getSelectedItemPosition());
        param.setColorPosit(spColor.getSelectedItemPosition());
        param.setTypePosit(spType.getSelectedItemPosition());

        param.setSize(spSize.getSelectedItem().toString());
        param.setColor(spColor.getSelectedItem().toString());
        param.setType(spType.getSelectedItem().toString());
        param.setSiteFilter(edSiteFilter.getText().toString());
        Intent data = new Intent();
        data.putExtra("queryParam", param);
        setResult(RESULT_OK, data);
        finish();
    }

    private void settingCustomerSpanner(int textArrayResId, Spinner spnner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, textArrayResId, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spnner.setAdapter(adapter);
    }

    private void setupViews(){
        spSize = (Spinner) findViewById(R.id.spSize);
        spColor = (Spinner) findViewById(R.id.spColor);
        spType = (Spinner) findViewById(R.id.spType);
        edSiteFilter = (EditText) findViewById(R.id.edSiteFilter);
        btnSave = (Button) findViewById(R.id.btnSave);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advance_search_options, menu);
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
