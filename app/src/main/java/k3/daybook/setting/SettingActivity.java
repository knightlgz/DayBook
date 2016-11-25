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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import k3.daybook.R;
import k3.daybook.data.constant.GlobalConfig;
import k3.daybook.data.manager.AccountManager;
import k3.daybook.data.model.Account;
import k3.daybook.setting.adapter.DividerItemDecoration;
import k3.daybook.setting.adapter.PaymentAdapter;
import k3.daybook.setting.adapter.UsageAdapter;

public class SettingActivity extends Activity {

    private final String TAG = "SettingActivity";

    private Account mAccount;

    private EditText etBudget, etPeriodDate;
    private RecyclerView lvUsage, lvPayment;
    private Switch budgetMerge;

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
        initListener();
        decorateView();
    }

    private void initData() {
        mPaymentAdapter = new PaymentAdapter();
        mUsageAdapter = new UsageAdapter();
        mAccount = new Account();
    }

    private void initView() {
        etBudget = (EditText) findViewById(R.id.et_setting_budget);
        etPeriodDate = (EditText) findViewById(R.id.et_setting_period_date);
        lvUsage = (RecyclerView) findViewById(R.id.rv_setting_usage);
        lvPayment = (RecyclerView) findViewById(R.id.rv_setting_payment);
        budgetMerge = (Switch) findViewById(R.id.setting_budget_merge);
        budgetMerge.setChecked(GlobalConfig.MERGE_BUDGET);

        lvPayment.setLayoutManager(new LinearLayoutManager(this));
        lvPayment.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        lvPayment.setAdapter(mPaymentAdapter);
        mPaymentFooter = LayoutInflater.from(this)
                .inflate(R.layout.footer_append, lvPayment, false);
        mPaymentAdapter.setFooterView(mPaymentFooter);

        lvUsage.setLayoutManager(new LinearLayoutManager(this));
        lvUsage.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        lvUsage.setAdapter(mUsageAdapter);
        mUsageFooter = LayoutInflater.from(this).inflate(R.layout.footer_append, lvUsage, false);
        mUsageAdapter.setFooterView(mUsageFooter);
    }

    private void initListener() {
        budgetMerge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GlobalConfig.updateMergeBudget(isChecked);
            }
        });
        etBudget.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etBudget.setCursorVisible(true);
                } else {
                    etBudget.setCursorVisible(false);
                    if (etBudget.getText().length() == 0) {
                        mAccount.setBudget(0);
                        return;
                    }
                    mAccount.setBudget(Float.parseFloat(etBudget.getText().toString()));
                }
            }
        });

        etPeriodDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etPeriodDate.setCursorVisible(true);
                } else {
                    etPeriodDate.setCursorVisible(false);
                    if (etPeriodDate.getText().length() == 0) {
                        mAccount.setPeriodDate(1);
                        return;
                    }
                    mAccount.setPeriodDate(Integer.parseInt(etPeriodDate.getText().toString()));
                }
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
        etBudget.clearFocus();
        etPeriodDate.clearFocus();
        AccountManager.getInstance().resetBudget(mAccount.getBudget());
        AccountManager.getInstance().resetDate(mAccount.getPeriodDate());
        AccountManager.getInstance().storeDataToDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: -----------------");
    }
}
