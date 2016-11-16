package k3.daybook.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import k3.daybook.R;
import k3.daybook.setting.adapter.PaymentAdapter;
import k3.daybook.setting.adapter.UsageAdapter;

public class SettingActivity extends Activity {

    private final String TAG = "SettingActivity";

    private EditText etBudget, etPeriodDate;
    private RecyclerView lvUsage, lvPayment;
    private TextView tvUsage, tvPayment, tvAddMore;

    private PaymentAdapter mPaymentAdapter;
    private UsageAdapter mUsageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Log.d(TAG, "onCreate: -----------------");
        initView();
    }

    private void initView() {
        etBudget = (EditText) findViewById(R.id.et_setting_budget);
        etPeriodDate = (EditText) findViewById(R.id.et_setting_period_date);
        lvUsage = (RecyclerView) findViewById(R.id.rv_setting_usage);
        lvPayment = (RecyclerView) findViewById(R.id.rv_setting_payment);
        tvAddMore = (TextView) findViewById(R.id.tv_setting_list_footer);
        tvPayment = (TextView) findViewById(R.id.tv_setting_payment_header);
        tvUsage = (TextView) findViewById(R.id.tv_setting_usage_header);

        mPaymentAdapter = new PaymentAdapter();
        lvPayment.setAdapter(mPaymentAdapter);

        mUsageAdapter = new UsageAdapter();
        lvUsage.setAdapter(mUsageAdapter);
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
