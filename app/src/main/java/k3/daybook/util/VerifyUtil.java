package k3.daybook.util;

import android.widget.Toast;

import java.util.Date;

import k3.daybook.R;
import k3.daybook.account.model.Account;
import k3.daybook.record.model.Record;

/**
 * @author Kyson LEE
 */

public class VerifyUtil {

    public static boolean verifyAccount(Account account) {
        if (RealmUtil.checkNameExist(account.getName())) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_name_exist,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (account.getType() <= 0 || account.getType() >= 5) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_no_type,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (account.getBalance() < 0.0) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_balance_illegal,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (account.getType() == Account.CASH || account.getType() == Account.VIRTUAL) {
            return true;
        }
        if (account.getHolder().isEmpty()) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_no_holder,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        Date today = new Date(System.currentTimeMillis());
        if (account.getExpire() < (today.getYear() * 100 + today.getMonth())) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_expire_illegal,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (account.getHolder().isEmpty()) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_no_holder,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (account.getType() == Account.DEBITCARD) {
            return true;
        }
        if (account.getLimit() <= 0.0) {
            Toast.makeText(ContextProvider.getApplicationContext(), R.string.toast_limit_illegal,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean verifyRecord(Record record) {
        if (record.getAmount() == 0.0) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_no_amount,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (record.getConsume() == 0) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_no_consume,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (record.getAccountId() < 0) {
            Toast.makeText(ContextProvider.getApplicationContext(),
                    R.string.toast_no_payment,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
