package k3.daybook.data.manager;

import java.util.List;

import k3.daybook.data.model.Account;
import k3.daybook.data.model.Payment;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */

public class AccountManager {

    private static final String TAG = "AccountManager";

    private static AccountManager sInstance;

    private static Account sAccount;

    public void initData() {
        if (sAccount == null) {
            sAccount = new Account();
        }
        sAccount.updateAccount(DBUtil.getAccount());
    }

    private AccountManager() {
        initData();
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

    public Payment getAPayment(int index) {
        return sAccount.getPayments().get(index);
    }

    public int getPaymentSize() {
        return sAccount.getPayments().size();
    }

    public void renamePayment(String name, int index) {
        sAccount.renamePayment(name, index);
    }

    public void addAPayment(Payment payment) {
        sAccount.addPayment(payment);
        DBUtil.updateAccount(sAccount);
    }

    public void deleteAPayment(int index) {
        sAccount.deletePayment(index);
        DBUtil.updateAccount(sAccount);
    }

    public void storeData() {
        DBUtil.updateAccount(sAccount);
    }
}
