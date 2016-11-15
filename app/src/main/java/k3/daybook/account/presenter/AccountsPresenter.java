package k3.daybook.account.presenter;

import android.content.Context;
import android.content.Intent;

import k3.daybook.account.CreateAccountActivity;
import k3.daybook.util.RealmUtil;

/**
 * @author Kyson LEE
 */

public class AccountsPresenter {

    public void toAddAccount(Context context) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        context.startActivity(intent);
    }

    public void resetAccounts() {
        RealmUtil.resetAccounts();
    }

}
