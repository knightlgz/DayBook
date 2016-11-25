package k3.daybook.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import k3.daybook.R;
import k3.daybook.data.constant.GlobalConfig;
import k3.daybook.data.manager.AccountManager;
import k3.daybook.data.manager.RecordManager;
import k3.daybook.home.adapter.RecentlyAdapter;
import k3.daybook.home.presenter.HomePresenter;
import k3.daybook.home.view.HomeViews;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */

public class HomeActivity extends Activity implements HomeViews, View.OnClickListener {

    private final String TAG = "Home";
    private ImageView mBtnToAnalyze, mBtnToSetting, mBtnToAdd;
    private HomePresenter mPresenter = null;
    private TextView mTvRestBudget, mTvPastBudget;
    private RecyclerView rvRecentlyPayout;

    private float consumedBudget, wholeBudget;
    private RecentlyAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: -----------------");
        initData();
        initPresenter();
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: -----------------");
        initData();
        refreshView();
    }

    private void initData() {
        consumedBudget = DBUtil.getConsumedBudgetOfLastPeriod();
        wholeBudget = AccountManager.getInstance().getAccount().getBudget();
        mAdapter = new RecentlyAdapter();
    }

    private void initView() {
        mBtnToAnalyze = (ImageView) findViewById(R.id.home_analyze);
        mBtnToSetting = (ImageView) findViewById(R.id.home_setting);
        mBtnToAdd = (ImageView) findViewById(R.id.home_add);
        mTvRestBudget = (TextView) findViewById(R.id.home_rest_budget);
        mTvPastBudget = (TextView) findViewById(R.id.home_past_budget);
        rvRecentlyPayout = (RecyclerView) findViewById(R.id.rv_home_recent_records);

        mTvRestBudget.setText(String.valueOf(wholeBudget - consumedBudget));
        if (consumedBudget >= wholeBudget * GlobalConfig.BUDGET_DANGER) {
            mTvRestBudget.setTextColor(getResources().getColor(R.color.color_rest_budget_alarm));
        } else if (consumedBudget >= wholeBudget * GlobalConfig.BUDGET_FRIENDLY) {
            mTvRestBudget.setTextColor(getResources().getColor(R.color.color_rest_budget_caution));
        } else {
            mTvRestBudget.setTextColor(getResources().getColor(R.color.color_rest_budget_friendly));
        }
        mTvPastBudget.setText(String.valueOf(consumedBudget));

        rvRecentlyPayout.setLayoutManager(new LinearLayoutManager(this));
        rvRecentlyPayout.setAdapter(mAdapter);
    }

    private void initListener() {
        mBtnToAnalyze.setOnClickListener(this);
        mBtnToSetting.setOnClickListener(this);
        mBtnToAdd.setOnClickListener(this);
    }

    private void initPresenter() {
        mPresenter = new HomePresenter();
    }

    private void refreshView() {
        mTvRestBudget.setText(String.valueOf(wholeBudget - consumedBudget));
        if (consumedBudget >= wholeBudget * GlobalConfig.BUDGET_DANGER) {
            mTvRestBudget.setTextColor(getResources().getColor(R.color.color_rest_budget_alarm));
        } else if (consumedBudget >= wholeBudget * GlobalConfig.BUDGET_FRIENDLY) {
            mTvRestBudget.setTextColor(getResources().getColor(R.color.color_rest_budget_caution));
        } else {
            mTvRestBudget.setTextColor(getResources().getColor(R.color.color_rest_budget_friendly));
        }
        mTvPastBudget.setText(String.valueOf(consumedBudget));

        RecordManager.refreshData();
        rvRecentlyPayout.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void jumpAnalyze() {
        mPresenter.jumpToAnalyze(this);
    }

    @Override
    public void jumpSetting() {
        mPresenter.jumpToSetting(this);
    }

    @Override
    public void jumpRecording() {
        mPresenter.jumpToRecording(this);
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
            Log.d(TAG, "onClick: jump to recording page");
            jumpRecording();
            break;
        default:
            break;
        }
    }
}
