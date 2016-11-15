package k3.daybook.management;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import k3.daybook.R;
import k3.daybook.management.presenter.ManagementPresenter;
import k3.daybook.management.view.ManagementView;

public class ManagementActivity extends Activity implements ManagementView, View.OnClickListener {

    private final String TAG = "Management";
    private ManagementPresenter mPresenter = null;
    private Button mBtnToAccounts, mBtnToRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        Log.d(TAG, "onCreate: -----------------");
        initPresenter();
        initView();
    }

    private void initPresenter() {
        mPresenter = new ManagementPresenter();
    }

    private void initView() {
        mBtnToAccounts = (Button) findViewById(R.id.management_accounts);
        mBtnToRecords = (Button) findViewById(R.id.management_records);
        mBtnToAccounts.setOnClickListener(this);
        mBtnToRecords.setOnClickListener(this);
    }

    @Override
    public void toAccounts() {
        mPresenter.jumpToAccounts(this);
    }

    @Override
    public void toRecords() {
        mPresenter.jumpToRecords(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.management_accounts:
            Log.d(TAG, "onClick: jump to accounts page");
            toAccounts();
            break;
        case R.id.management_records:
            Log.d(TAG, "onClick: jump to records page");
            toRecords();
            break;
        default:
            break;
        }
    }
}
