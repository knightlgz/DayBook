package k3.daybook.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import k3.daybook.R;
import k3.daybook.data.manager.AccountManager;
import k3.daybook.data.manager.UsageManager;
import k3.daybook.setting.adapter.PaymentAdapter;
import k3.daybook.setting.adapter.UsageAdapter;

public class SettingActivity extends Activity {

    private final String TAG = "SettingActivity";

    private EditText etBudget, etPeriodDate;
    private RecyclerView lvUsage, lvPayment;

    private PaymentAdapter mPaymentAdapter;
    private UsageAdapter mUsageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Log.d(TAG, "onCreate: -----------------");
        initData();
        initView();
        decorateView();
    }

    private void initData() {
        AccountManager.getInstance().initData();
        UsageManager.getInstance().initData();
    }

    private void initView() {
        etBudget = (EditText) findViewById(R.id.et_setting_budget);
        etPeriodDate = (EditText) findViewById(R.id.et_setting_period_date);
        lvUsage = (RecyclerView) findViewById(R.id.rv_setting_usage);
        lvPayment = (RecyclerView) findViewById(R.id.rv_setting_payment);

        mPaymentAdapter = new PaymentAdapter();
        lvPayment.setLayoutManager(new LinearLayoutManager(this));
        lvPayment.setAdapter(mPaymentAdapter = new PaymentAdapter());

        mUsageAdapter = new UsageAdapter();
        lvUsage.setLayoutManager(new LinearLayoutManager(this));
        lvUsage.setAdapter(mUsageAdapter = new UsageAdapter());

    }

    private void decorateView() {
        float budget = AccountManager.getInstance().getAccount().getBudget();
        if (budget > 0) {
            etBudget.setText(String.valueOf(budget));
            etBudget.setBackground(null);
            etBudget.setCursorVisible(false);
        }
        int date = AccountManager.getInstance().getAccount().getPeriodDate();
        if (date > 0 && date < 29) {
            etPeriodDate.setText(String.valueOf(date));
            etPeriodDate.setBackground(null);
            etPeriodDate.setCursorVisible(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: -----------------");
        // TODO: 2016/11/15 save the local setting to data provider
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: -----------------");
        // TODO: 2016/11/15 save the data provider to db
    }
}
