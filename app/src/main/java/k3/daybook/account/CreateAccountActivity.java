package k3.daybook.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import k3.daybook.R;
import k3.daybook.account.model.Account;
import k3.daybook.account.presenter.CreateAccountPresenter;
import k3.daybook.account.view.CreateAccountView;
import k3.daybook.util.VerifyUtil;

public class CreateAccountActivity extends Activity implements CreateAccountView {

    private EditText mName, mBalance, mExpire, mHolder, mLimit;
    private Spinner mType;
    private Button mBtnSubmit;

    private ArrayAdapter<CharSequence> mTypeAdapter;
    private CreateAccountPresenter mPresenter;
    private Account mAccount;

    @Override
    public void confirm() {
        if (!makeupAmount()) {
            return;
        }
        if (!VerifyUtil.verifyAccount(mAccount)) {
            return;
        }
        mPresenter.AddAccount(mAccount);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        mPresenter = new CreateAccountPresenter();
        mAccount = new Account();
    }

    private void initView() {
        mName = (EditText) findViewById(R.id.create_account_name);
        mBalance = (EditText) findViewById(R.id.create_account_balance);
        mExpire = (EditText) findViewById(R.id.create_account_expire);
        mHolder = (EditText) findViewById(R.id.create_account_holder);
        mLimit = (EditText) findViewById(R.id.create_account_limit);
        mType = (Spinner) findViewById(R.id.create_account_type);
        mBtnSubmit = (Button) findViewById(R.id.btn_create_account_submit);

        mTypeAdapter = ArrayAdapter.createFromResource(this, R.array.account_types,
                android.R.layout.simple_spinner_dropdown_item);
        mType.setAdapter(mTypeAdapter);
    }

    private void initListener() {
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
        mType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAccount.setType(position);
                switch (position) {
                case Account.CASH:
                case Account.VIRTUAL:
                    findViewById(R.id.tv_create_expire).setVisibility(View.GONE);
                    findViewById(R.id.tv_create_holder).setVisibility(View.GONE);
                    findViewById(R.id.tv_create_limit).setVisibility(View.GONE);
                    mExpire.setVisibility(View.GONE);
                    mHolder.setVisibility(View.GONE);
                    mLimit.setVisibility(View.GONE);
                    break;
                case Account.DEBITCARD:
                    findViewById(R.id.tv_create_expire).setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_create_holder).setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_create_limit).setVisibility(View.GONE);
                    mExpire.setVisibility(View.VISIBLE);
                    mHolder.setVisibility(View.VISIBLE);
                    mLimit.setVisibility(View.GONE);
                    break;
                case Account.CREDITCARD:
                    findViewById(R.id.tv_create_expire).setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_create_holder).setVisibility(View.VISIBLE);
                    findViewById(R.id.tv_create_limit).setVisibility(View.VISIBLE);
                    mExpire.setVisibility(View.VISIBLE);
                    mHolder.setVisibility(View.VISIBLE);
                    mLimit.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private boolean makeupAmount() {
//        mAccount.setId(GreenDAOUtil.getInstance().getLargestId(Account.class) + 1);
        mAccount.setName(mName.getText().toString());
        mAccount.setBalance(Float.parseFloat(mBalance.getText().toString()));
        switch (mAccount.getType()) {
        case Account.CASH:
        case Account.VIRTUAL:
            mAccount.setExpire(0);
            mAccount.setHolder("");
            mAccount.setLimit(0);
            return true;
        case Account.DEBITCARD:
            mAccount.setHolder(mHolder.getText().toString());
            mAccount.setLimit(0);
            break;
        case Account.CREDITCARD:
            mAccount.setHolder(mHolder.getText().toString());
            mAccount.setLimit(Float.parseFloat(mLimit.getText().toString()));
            break;
        default:
            break;
        }

        if (mExpire.getText().length() != 6) {
            Toast.makeText(this, R.string.toast_expire_illegal, Toast.LENGTH_SHORT).show();
            return false;
        }
        int tem = Integer.parseInt(mExpire.getText().toString());
        if ((tem % 100) > 12) {
            Toast.makeText(this, R.string.toast_expire_illegal, Toast.LENGTH_SHORT).show();
            return false;
        }
        mAccount.setExpire(Integer.parseInt(mExpire.getText().toString()));
        return true;
    }

}
