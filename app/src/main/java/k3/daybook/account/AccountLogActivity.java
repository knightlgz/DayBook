package k3.daybook.account;

import android.app.Activity;
import android.os.Bundle;

import k3.daybook.R;
import k3.daybook.account.view.AccountLogView;

public class AccountLogActivity extends Activity implements AccountLogView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_log);
    }
}
