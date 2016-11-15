package k3.daybook.account;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import k3.daybook.R;
import k3.daybook.account.adapter.AccountsAdapter;
import k3.daybook.account.presenter.AccountsPresenter;
import k3.daybook.account.view.AccountsView;

public class AccountsActivity extends Activity implements AccountsView {

    private final String TAG = "Accounts";
    private RecyclerView mAccountList;
    private Button mAdd, mReset;

    private AccountsAdapter mAdapter;
    private AccountsPresenter mPresenter;

    @Override
    public void addAccount() {
        mPresenter.toAddAccount(this);
    }

    @Override
    public void resetAccounts() {
        mPresenter.resetAccounts();
        mAdapter.updateData();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        Log.d(TAG, "onCreate: -----------------");
        initData();
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: -----------------");
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        mPresenter = new AccountsPresenter();
        mAdapter = new AccountsAdapter();
    }

    private void initView() {
        mAccountList = (RecyclerView) findViewById(R.id.list_accounts);
        mAdd = (Button) findViewById(R.id.accounts_add);
        mReset = (Button) findViewById(R.id.accounts_reset);

        mAccountList.setLayoutManager(new LinearLayoutManager(this));
        mAccountList.setAdapter(mAdapter);
    }

    private void initListener() {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAccounts();
            }
        });
    }
}
