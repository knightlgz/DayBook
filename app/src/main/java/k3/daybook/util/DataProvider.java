package k3.daybook.util;

import java.util.ArrayList;
import java.util.List;

import k3.daybook.data.model.Account;
import k3.daybook.data.model.Payment;
import k3.daybook.data.model.Record;
import k3.daybook.data.model.Usage;

/**
 * @author Kyson LEE
 */

public class DataProvider {

    private static final String TAG = "DataProvider";

    private static Account sAccount;
    private static List<Record> sRecords;
    private static List<Usage> sUsages;

    private static DataProvider sInstance;

    private DataProvider() {
        initAccount();
        initUsage();
        initRecords();
    }

    public static DataProvider getInstance() {
        if (sInstance == null) {
            synchronized (DataProvider.class) {
                if (sInstance == null) {
                    sInstance = new DataProvider();
                }
            }
        }
        return sInstance;
    }

    private void initAccount() {
        if (sAccount == null) {
            sAccount = new Account();
        }
        sAccount.updateAccount(DBUtil.getAccount());
    }

    private void initUsage() {
        if (sUsages == null) {
            sUsages = new ArrayList<>();
        }
        sUsages.addAll(DBUtil.getUsages());
    }

    private void initRecords() {
        if (sRecords == null) {
            sRecords = new ArrayList<>();
        }
    }

    public float getBudget() {
        return sAccount.getBudget();
    }

    public int getPeriodDate() {
        return sAccount.getPeriodDate();
    }

    public List<Payment> getPayments() {
        return sAccount.getPayments();
    }

    public Payment getPaymentById(long id) {
        for (Payment payment :
                sAccount.getPayments()) {
            if (payment.getId() == id) {
                return payment;
            }
        }
        return null;
    }

    public List<Usage> getUsages() {
        return sUsages;
    }

    // TODO: 2016/11/15 Add methods on getting records
}
