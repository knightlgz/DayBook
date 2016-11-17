package k3.daybook.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import k3.daybook.R;
import k3.daybook.home.presenter.HomePresenter;
import k3.daybook.home.view.HomeViews;

/**
 * @author Kyson LEE
 */

public class HomeActivity extends Activity implements HomeViews, View.OnClickListener {

    private final String TAG = "Home";
    private HomePresenter mPresenter = null;
    private ImageView mBtnToAnalyze, mBtnToSetting, mBtnToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: -----------------");
        initPresenter();
        initView();
    }

    private void initView() {
        mBtnToAnalyze = (ImageView) findViewById(R.id.home_analyze);
        mBtnToSetting = (ImageView) findViewById(R.id.home_setting);
        mBtnToAdd = (ImageView) findViewById(R.id.home_add);

        mBtnToAnalyze.setOnClickListener(this);
        mBtnToSetting.setOnClickListener(this);
        mBtnToAdd.setOnClickListener(this);
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
    public void confirmAdd() {

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
        case R.id.home_add:
            break;
        default:
            break;
        }
    }
}
