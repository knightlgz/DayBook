package k3.daybook.analyze;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import k3.daybook.R;
import k3.daybook.analyze.fragment.RecordListFragment;

public class AnalyzeActivity extends FragmentActivity {

    private static final String TAG = "AnalyzeActivity";

    private RecordListFragment mRecordListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mRecordListFragment = new RecordListFragment();
        transaction.replace(R.id.analyze_fragment_field, mRecordListFragment);
        transaction.commit();
    }
}
