package k3.daybook.data;

import java.util.ArrayList;
import java.util.List;

import k3.daybook.account.model.Account;
import k3.daybook.record.model.Record;
import k3.daybook.util.RealmUtil;

/**
 * @author Kyson LEE
 */

public class DataFactory {

    private static DataFactory sInstance;
    private static List<Account> sAccounts;
    private static List<Record> sRecords;

    private DataFactory() {
        initAccounts();
        initRecords();
    }

    public static DataFactory getInstance() {
        if (sInstance == null) {
            synchronized (DataFactory.class) {
                if (sInstance == null) {
                    sInstance = new DataFactory();
                }
            }
        }
        return sInstance;
    }

    private void initAccounts() {
        if (sAccounts == null) {
            sAccounts = new ArrayList<>();
        }
    }

    private void initRecords() {
        if (sRecords == null) {
            sRecords = new ArrayList<>();
        }
        sRecords.clear();
    }

    public void updateAccountsFromDB() {
        initAccounts();
        sAccounts.clear();
        sAccounts = RealmUtil.getAllAccounts();
        if (sAccounts.size() == 0) {
            RealmUtil.resetAccounts();
            sAccounts = RealmUtil.getAllAccounts();
        }
    }

    public void updateRecordsByAccountIdFromDB(long accountId) {
        initRecords();
        sRecords = RealmUtil.getRecordsByAccountId(accountId);
    }

    public void updateRecordsByConsumeFromDB(int consume) {
        initRecords();
        sRecords = RealmUtil.getRecordsByConsume(consume);
    }

    public List<Account> getAccounts() {
        return sAccounts;
    }

    public List<Record> getRecords() {
        return sRecords;
    }

    public long getLatestAccountId() {
        return RealmUtil.getLatestAccountId();
    }

    public long getLatestRecordId() {
        return RealmUtil.getLatestRecordId();
    }

    public void updateAccountToDB() {

    }

    public void updateRecordToDB(Record record) {

    }

    public void addAnAccount(Account account) {
        initAccounts();
        if (sAccounts.size() == 0) {
            RealmUtil.resetAccounts();
        }
        sAccounts = getAccounts();
        sAccounts.add(account);
        RealmUtil.addAccount(account);
    }
}
