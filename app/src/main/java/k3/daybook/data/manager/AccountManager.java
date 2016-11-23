package k3.daybook.data.manager;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import k3.daybook.data.model.Account;
import k3.daybook.data.model.Payment;
import k3.daybook.data.model.Usage;
import k3.daybook.util.DBUtil;
import k3.daybook.util.SortingUtil;

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

        makeupAccount();

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

    private void makeupAccount() {
        SortingUtil.RecommendSortingUsage(sUsages);
        SortingUtil.RecommendSortingPayment(sPayments);
    }

    public Account getAccount() {
        return sAccount;
    }

    public String getUsageNameByIndex(int index) {
        return sUsages.get(index).getName();
    }

    public int getUsageSize() {
        return sUsages.size();
    }

    public String getPaymentNameByIndex(int index) {
        return sPayments.get(index).getName();
    }

    public int getPaymentSize() {
        return sPayments.size();
    }

    public void renameUsageByIndex(String newName, int index) {
        sUsages.get(index).renameUsage(newName);
    }

    public void renamePaymentByIndex(String newName, int index) {
        sPayments.get(index).rename(newName);
    }

    public void deleteUsageByIndex(int index) {
        DBUtil.deleteUsageById(sUsages.get(index).getId());
        sUsages.remove(index);
    }

    public void deletePaymentByIndex(int index) {
        DBUtil.deletePaymentById(sPayments.get(index).getId());
        sPayments.remove(index);
    }

    public void addUsage(String name) {
        Usage usage = new Usage();
        usage.setName(name);
        sUsages.add(usage);
        DBUtil.addUsage(usage);
    }

    public void addPayment(String name) {
        Payment payment = new Payment();
        payment.setName(name);
        sPayments.add(payment);
        DBUtil.addPayment(payment);
    }

    public void resetBudget(float budget) {
        sAccount.setBudget(budget);
    }

    public void resetDate(int date) {
        sAccount.setPeriodDate(date);
    }

    public void storeDataToDB() {
        DBUtil.updateAccount(sAccount);
        DBUtil.updateUsages(sUsages);
        DBUtil.updatePayments(sPayments);
    }
}
