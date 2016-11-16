package k3.daybook.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import k3.daybook.R;
import k3.daybook.home.presenter.HomePresenter;
import k3.daybook.home.view.HomeViews;

/**
 * @author Kyson LEE
 */

public class HomeActivity extends Activity implements HomeViews, View.OnClickListener {

    private final String TAG = "Home";
    private HomePresenter mPresenter = null;
    private Button mBtnToRecording, mBtnToManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: -----------------");
        initPresenter();
        initView();
    }

    private void initView() {
        mBtnToRecording = (Button) findViewById(R.id.home_analyze);
        mBtnToManagement = (Button) findViewById(R.id.home_setting);

        mBtnToRecording.setOnClickListener(this);
        mBtnToManagement.setOnClickListener(this);
    }

    private void initPresenter() {
        mPresenter = new HomePresenter();
    }

    @Override
    public void jumpAnalyze() {
    }

    @Override
    public void jumpSetting() {
        mPresenter.jumpToSetting(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_analyze:
                Log.d(TAG, "onClick: jump to analyze page");
                jumpAnalyze();
                break;
            case R.id.home_setting:
                Log.d(TAG, "onClick: jump to setting page");
                jumpSetting();
                break;
            default:
                break;
        }
    }

}
