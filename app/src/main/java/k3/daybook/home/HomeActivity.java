package k3.daybook.home;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;

import k3.daybook.R;
import k3.daybook.data.manager.AccountManager;
import k3.daybook.data.manager.RecordManager;
import k3.daybook.data.model.Record;
import k3.daybook.home.presenter.HomePresenter;
import k3.daybook.home.view.HomeViews;

/**
 * @author Kyson LEE
 */

public class HomeActivity extends Activity implements HomeViews, View.OnClickListener {

    private final String TAG = "Home";
    private HomePresenter mPresenter = null;
    private ImageView mBtnToAnalyze, mBtnToSetting, mBtnToAdd;
    private Switch mInOut;
    private EditText mAmount;
    private RadioButton mUsage[], mPayment[];
    private Button xUsageSet, xPaymentSet;

    private Record mRecord;
    private boolean isGain, isUsageSetChanged, isPaymentSetChanged;
    private int usageIndex, paymentIndex;

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
        makeupPayments();
        makeupUsages();
    }

    private void initData() {
        mRecord = new Record();
        mUsage = new RadioButton[5];
        mPayment = new RadioButton[5];
        isGain = false;
        isPaymentSetChanged = false;
        isUsageSetChanged = false;
        usageIndex = 0;
        paymentIndex = 0;
    }

    private void initView() {
        mBtnToAnalyze = (ImageView) findViewById(R.id.home_analyze);
        mBtnToSetting = (ImageView) findViewById(R.id.home_setting);
        mBtnToAdd = (ImageView) findViewById(R.id.home_add);
        mInOut = (Switch) findViewById(R.id.home_in_out);
        mAmount = (EditText) findViewById(R.id.et_home_amount);
        mUsage[0] = (RadioButton) findViewById(R.id.home_usage_1);
        mUsage[1] = (RadioButton) findViewById(R.id.home_usage_2);
        mUsage[2] = (RadioButton) findViewById(R.id.home_usage_3);
        mUsage[3] = (RadioButton) findViewById(R.id.home_usage_4);
        mUsage[4] = (RadioButton) findViewById(R.id.home_usage_5);
        mPayment[0] = (RadioButton) findViewById(R.id.home_payment_1);
        mPayment[1] = (RadioButton) findViewById(R.id.home_payment_2);
        mPayment[2] = (RadioButton) findViewById(R.id.home_payment_3);
        mPayment[3] = (RadioButton) findViewById(R.id.home_payment_4);
        mPayment[4] = (RadioButton) findViewById(R.id.home_payment_5);
        xUsageSet = (Button) findViewById(R.id.another_usage_page);
        xPaymentSet = (Button) findViewById(R.id.another_payment_page);

        makeupUsages();
        makeupPayments();

        mBtnToAdd.setEnabled(false);
    }

    private void initListener() {
        mBtnToAnalyze.setOnClickListener(this);
        mBtnToSetting.setOnClickListener(this);
        mBtnToAdd.setOnClickListener(this);
        xUsageSet.setOnClickListener(this);
        xPaymentSet.setOnClickListener(this);

        mInOut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGain = isChecked;
            }
        });

        for (int i = 0; i < 5; i++) {
            final int index = i;
            mUsage[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (!isUsageSetChanged) {
                            usageIndex = index;
                        } else {
                            usageIndex = index + 5;
                        }
                    }
                }
            });
        }

        for (int i = 0; i < 5; i++) {
            final int index = i;
            mPayment[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (!isPaymentSetChanged) {
                            paymentIndex = index;
                        } else {
                            paymentIndex = index + 5;
                        }
                    }
                }
            });
        }

        mAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mAmount.getText().length() > 0) {
                    mBtnToAdd.setEnabled(true);
                } else {
                    mBtnToAdd.setEnabled(false);
                }
            }
        });
    }

    private void initPresenter() {
        mPresenter = new HomePresenter();
    }

    private void makeupUsages() {
        if (!isUsageSetChanged) {
            for (int i = 0; i < (AccountManager.getInstance().getUsageSize() < 5 ? AccountManager
                    .getInstance().getUsageSize() : 5); i++) {
                mUsage[i].setText(AccountManager.getInstance().getUsageNameByIndex(i));
                mUsage[i].setVisibility(View.VISIBLE);
            }
            for (int i = (AccountManager.getInstance().getUsageSize() < 5 ? AccountManager
                    .getInstance().getUsageSize() : 5); i < 5; i++) {
                mUsage[i].setVisibility(View.INVISIBLE);
            }
            if (AccountManager.getInstance().getUsageSize() <= 5) {
                xUsageSet.setVisibility(View.GONE);
            } else {
                xUsageSet.setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 5; i < AccountManager.getInstance().getUsageSize(); i++) {
                mUsage[i - 5].setText(AccountManager.getInstance().getUsageNameByIndex(i));
                mUsage[i - 5].setVisibility(View.VISIBLE);
            }
            for (int i = AccountManager.getInstance().getUsageSize(); i < 10; i++) {
                mUsage[i - 5].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void makeupPayments() {
        if (!isPaymentSetChanged) {
            for (int i = 0; i < (AccountManager.getInstance().getPaymentSize() < 5 ? AccountManager
                    .getInstance().getPaymentSize() : 5); i++) {
                mPayment[i].setText(AccountManager.getInstance().getPaymentNameByIndex(i));
                mPayment[i].setVisibility(View.VISIBLE);
            }
            for (int i = (AccountManager.getInstance().getPaymentSize() < 5 ? AccountManager
                    .getInstance().getPaymentSize() : 5); i < 5; i++) {
                mPayment[i].setVisibility(View.INVISIBLE);
            }
            if (AccountManager.getInstance().getPaymentSize() <= 5) {
                xPaymentSet.setVisibility(View.GONE);
            } else {
                xPaymentSet.setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 5; i < AccountManager.getInstance().getPaymentSize(); i++) {
                mPayment[i - 5].setText(AccountManager.getInstance().getPaymentNameByIndex(i));
                mPayment[i - 5].setVisibility(View.VISIBLE);
            }
            for (int i = AccountManager.getInstance().getPaymentSize(); i < 10; i++) {
                mPayment[i - 5].setVisibility(View.INVISIBLE);
            }
        }
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
    public void confirmAdd() {
        mBtnToAdd.setEnabled(false);
        mRecord = new Record();
        mRecord.setAmount(isGain ? 0 - Float.parseFloat(mAmount.getText().toString()) : Float
                .parseFloat(mAmount.getText().toString()));
        mRecord.setUsageName(AccountManager.getInstance().getUsageNameByIndex(usageIndex));
        mRecord.setPaymentName(AccountManager.getInstance().getPaymentNameByIndex(paymentIndex));
        RecordManager.getInstance().addRecord(mRecord);

        mRecord = null;
        mInOut.setChecked(false);
        mAmount.setText("");
        for (RadioButton usage : mUsage) {
            usage.setChecked(false);
        }
        for (RadioButton payment : mPayment) {
            payment.setChecked(false);
        }

        AccountManager.getInstance().refreshUsageNPayment();
        makeupUsages();
        makeupPayments();
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
            confirmAdd();
            break;
        case R.id.another_usage_page:
            isUsageSetChanged = !isUsageSetChanged;
            makeupUsages();
            break;
        case R.id.another_payment_page:
            isPaymentSetChanged = !isPaymentSetChanged;
            makeupPayments();
            break;
        default:
            break;
        }
    }
}
