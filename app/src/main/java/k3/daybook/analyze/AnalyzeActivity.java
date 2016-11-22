package k3.daybook.analyze;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import k3.daybook.R;
import k3.daybook.analyze.adapter.RecordsAdapter;
import k3.daybook.setting.adapter.DividerItemDecoration;

public class AnalyzeActivity extends Activity {

    private static final String TAG = "AnalyzeActivity";

    private RecyclerView rvRecords;

    private RecordsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        initData();
        initView();
    }

    private void initData() {
        mAdapter = new RecordsAdapter();
    }

    private void initView() {
        rvRecords = (RecyclerView) findViewById(R.id.analyze_record_list);
        rvRecords.setLayoutManager(new LinearLayoutManager(this));
        rvRecords.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        rvRecords.setAdapter(mAdapter);
    }
}