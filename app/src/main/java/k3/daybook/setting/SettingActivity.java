package k3.daybook.setting;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import k3.daybook.R;
import k3.daybook.data.manager.AccountManager;
import k3.daybook.setting.adapter.DividerItemDecoration;
import k3.daybook.setting.adapter.PaymentAdapter;
import k3.daybook.setting.adapter.UsageAdapter;

public class SettingActivity extends Activity {

    private final String TAG = "SettingActivity";

    private EditText etBudget, etPeriodDate;
    private RecyclerView lvUsage, lvPayment;

    private PaymentAdapter mPaymentAdapter;
    private UsageAdapter mUsageAdapter;
    private View mPaymentFooter, mUsageFooter;

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
        mPaymentAdapter = new PaymentAdapter();
        mUsageAdapter = new UsageAdapter();
    }

    private void initView() {
        etBudget = (EditText) findViewById(R.id.et_setting_budget);
        etPeriodDate = (EditText) findViewById(R.id.et_setting_period_date);
        lvUsage = (RecyclerView) findViewById(R.id.rv_setting_usage);
        lvPayment = (RecyclerView) findViewById(R.id.rv_setting_payment);

        mPaymentAdapter = new PaymentAdapter();
        lvPayment.setLayoutManager(new LinearLayoutManager(this));
        lvPayment.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        lvPayment.setAdapter(mPaymentAdapter);
        mPaymentFooter = LayoutInflater.from(this)
                .inflate(R.layout.footer_append, lvPayment, false);
        mPaymentAdapter.setFooterView(mPaymentFooter);

        mUsageAdapter = new UsageAdapter();
        lvUsage.setLayoutManager(new LinearLayoutManager(this));
        lvUsage.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        lvUsage.setAdapter(mUsageAdapter);
        mUsageFooter = LayoutInflater.from(this).inflate(R.layout.footer_append, lvUsage, false);
        mUsageAdapter.setFooterView(mUsageFooter);

        etBudget.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etBudget.setCursorVisible(true);
                } else {
                    etBudget.setCursorVisible(false);
                }
            }
        });
        etBudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                AccountManager.getInstance().resetBudget(
                        Float.parseFloat(etBudget.getText().toString()));
            }
        });

        etPeriodDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etPeriodDate.setCursorVisible(true);
                } else {
                    etPeriodDate.setCursorVisible(false);
                }
            }
        });
        etPeriodDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                AccountManager.getInstance().resetDate(
                        Integer.parseInt(etPeriodDate.getText().toString()));
            }
        });
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
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: -----------------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: -----------------");
        AccountManager.getInstance().storeDataToDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: -----------------");
    }
}
