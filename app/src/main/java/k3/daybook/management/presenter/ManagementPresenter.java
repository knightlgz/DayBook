package k3.daybook.management.presenter;

import android.content.Context;
import android.content.Intent;

import k3.daybook.account.AccountsActivity;
import k3.daybook.record.RecordsActivity;

/**
 * @author Kyson LEE
 */

public class ManagementPresenter {

    public void jumpToAccounts(Context context) {
        Intent intent = new Intent(context, AccountsActivity.class);
        context.startActivity(intent);
    }

    public void jumpToRecords(Context context) {
        Intent intent = new Intent(context, RecordsActivity.class);
        context.startActivity(intent);
    }
}
