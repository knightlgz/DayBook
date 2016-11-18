package k3.daybook.data.manager;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import k3.daybook.data.model.Account;
import k3.daybook.data.model.Payment;
import k3.daybook.data.model.Usage;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */

public class AccountManager {

    private static final String TAG = "AccountManager";

    private static AccountManager sInstance;

    private static Account sAccount;
    private static List<Usage> sUsages;
    private static List<Payment> sPayments;

    private AccountManager() {
        if (sAccount == null) {
            sAccount = new Account();
        }
        sAccount.updateAccount(DBUtil.getAccount());

        if (sUsages == null) {
            sUsages = new ArrayList<>();
        }
        sUsages.clear();
        sUsages.addAll(DBUtil.getUsages());

        if (sPayments == null) {
            sPayments = new ArrayList<>();
        }
        sPayments.clear();
        sPayments.addAll(DBUtil.getPayments());

        Log.d(TAG, "AccountManager: Data Initialized: " + sAccount + "\n" + sUsages + "\n"
                + sPayments);
    }

    public static AccountManager getInstance() {
        if (sInstance == null) {
            synchronized (AccountManager.class) {
                if (sInstance == null) {
                    sInstance = new AccountManager();
                }
            }
        }
        return sInstance;
    }

    public Account getAccount() {
        return sAccount;
    }

    public List<String> getUsageNameList() {
        return sAccount.getUsageNames();
    }

    public List<String> getPaymentNameList() {
        return sAccount.getPaymentNames();
    }

    public void renameUsageByIndex(String newName, int index) {
        sAccount.renameUsageNameByIndex(newName, index);
        sUsages.get(index).renameUsage(newName);
    }

    public void renamePaymentByIndex(String newName, int index) {
        sAccount.renamePaymentNameByIndex(newName, index);
        sPayments.get(index).rename(newName);
    }

    public void deleteUsageByIndex(int index) {
        DBUtil.deleteUsageByName(sAccount.getUsageNames().get(index));
        sUsages.remove(index);
        sAccount.deleteUsageNameByIndex(index);
    }

    public void deletePaymentByIndex(int index) {
        DBUtil.deletePaymentByName(sAccount.getPaymentNames().get(index));
        sPayments.remove(index);
        sAccount.deletePaymentNameByIndex(index);
    }

    public void storeDataToDB() {
        DBUtil.updateAmmount(sAccount);
        DBUtil.updateUsages(sUsages);
        DBUtil.updatePayments(sPayments);
    }
}
