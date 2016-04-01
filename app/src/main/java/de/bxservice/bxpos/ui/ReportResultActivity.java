package de.bxservice.bxpos.ui;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import java.util.ArrayList;

import de.bxservice.bxpos.R;
import de.bxservice.bxpos.ui.adapter.ReportResultAdapter;
import de.bxservice.bxpos.ui.decorator.DividerItemDecoration;

public class ReportResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> reportResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.report_result);

        getExtras();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL_LIST));


        ReportResultAdapter mGridAdapter = new ReportResultAdapter(reportResults);

        recyclerView.setAdapter(mGridAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Get extras from the previous activity
     */
    private void getExtras() {

        Intent intent = getIntent();

        if(intent != null) {
            reportResults = (ArrayList<String>) intent.getSerializableExtra("results");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }

}