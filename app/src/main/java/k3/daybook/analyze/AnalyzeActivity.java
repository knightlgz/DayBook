package k3.daybook.analyze;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import k3.daybook.R;
import k3.daybook.analyze.fragment.RecordListFragment;

public class AnalyzeActivity extends Activity {

    private static final String TAG = "AnalyzeActivity";

    private RecordListFragment mRecordListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mRecordListFragment = new RecordListFragment();
        transaction.replace(R.id.analyze_fragment_field, mRecordListFragment);
        transaction.commit();
    }
}
