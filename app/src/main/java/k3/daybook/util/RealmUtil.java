package k3.daybook.util;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;
import k3.daybook.R;
import k3.daybook.account.model.Account;
import k3.daybook.record.model.Record;

/**
 * @author Kyson LEE
 */

public class RealmUtil {

    private static final String TAG = "Realm Operations";

    public static void initRealm() {
        Realm.init(ContextProvider.getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(ContextProvider.getApplicationContext().getResources()
                        .getString(R.string.db_name)).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
        Log.d(TAG, "initRealm: -----------------");
    }

    /**
     * 获取全部的账户信息
     *
     * @return
     */
    public static List<Account> getAllAccounts() {
        List<Account> accounts = Realm.getDefaultInstance()
                .copyFromRealm(Realm.getDefaultInstance()
                        .where(Account.class)
                        .findAllSorted(Account.ID, Sort.ASCENDING));
        Log.d(TAG, "getAllAccounts: " + accounts);
        return accounts;
    }

    /**
     * 根据账户的ID获取该账户下的记录
     *
     * @param accountId
     * @return
     */
    public static List<Record> getRecordsByAccountId(long accountId) {
        List<Record> records = Realm.getDefaultInstance()
                .copyFromRealm(Realm.getDefaultInstance()
                        .where(Record.class)
                        .equalTo(Record.ACCOUNTID, accountId)
                        .findAllSorted(Record.ID, Sort.ASCENDING));
        Log.d(TAG, "getRecordsByAccountId: " + records);
        return records;
    }

    /**
     * 根据消费类型获取该消费类型下的记录
     *
     * @param consume
     * @return
     */
    public static List<Record> getRecordsByConsume(int consume) {
        List<Record> records = Realm.getDefaultInstance()
                .copyFromRealm(Realm.getDefaultInstance()
                        .where(Record.class)
                        .equalTo(Record.CONSUME, consume)
                        .findAllSorted(Record.ID, Sort.ASCENDING));
        Log.d(TAG, "getRecordsByAccountId: " + records);
        return records;
    }

    /**
     * 获取全部支出总和
     *
     * @return
     */
    public static float getAllAmount() {
        float amount = Realm.getDefaultInstance()
                .where(Record.class)
                .notEqualTo(Record.CONSUME, Record.CONSUMING_SALARY)
                .findAll()
                .sum(Record.AMOUNT)
                .floatValue();
        Log.d(TAG, "getAllAmount: " + amount);
        return amount;
    }

    /**
     * 获取特定消费支出总和
     *
     * @param consume
     * @return
     */
    public static float getAmountByConsume(int consume) {
        float amount = Realm.getDefaultInstance()
                .where(Record.class)
                .equalTo(Record.CONSUME, consume)
                .findAll()
                .sum(Record.AMOUNT)
                .floatValue();
        Log.d(TAG, "getAmountByConsume: " + amount);
        return amount;
    }

    /**
     * 获取最大账户ID
     *
     * @return
     */
    public static long getLatestAccountId() {
        Number id = Realm.getDefaultInstance()
                .where(Account.class)
                .max(Account.ID);
        Log.d(TAG, "getLatestAccountId: " + id);
        if (id == null) {
            return -1;
        }
        return id.longValue();
    }

    /**
     * 获取最大记录ID
     *
     * @return
     */
    public static long getLatestRecordId() {
        long id = Realm.getDefaultInstance()
                .where(Record.class)
                .max(Record.ID)
                .longValue();
        Log.d(TAG, "getLatestRecordId: " + id);
        return id;
    }

    /**
     * 获取每种消费计量的百分比
     *
     * @return
     */
    public static float[] getPercentageOfConsume() {
        float[] percentages = new float[6];
        float total = getAllAmount();
        for (int i = 0; i < 6; i++) {
            percentages[i] = getAmountByConsume(i + 1) / total;
        }
        Log.d(TAG, "getPercentageOfConsume: " + percentages);
        return percentages;
    }

    /**
     * 添加一个账户到数据库中
     * @param account
     */
    public static void addAccount(Account account) {
        Realm.getDefaultInstance().copyToRealm(account);
    }

    /**
     * 重置账户信息
     */
    public static void resetAccounts() {
        final Account cashAccount = new Account();
        cashAccount.setName("现金Cash");
        cashAccount.setType(Account.CASH);
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance()
                        .where(Account.class)
                        .findAll()
                        .deleteAllFromRealm();
                Realm.getDefaultInstance()
                        .copyToRealm(cashAccount);
            }
        });
    }

    /**
     * 重名检查
     */
    public static boolean checkNameExist(String name) {
        Account account = Realm.getDefaultInstance()
                        .where(Account.class)
                        .equalTo(Account.NAME, name)
                        .findFirst();
        return account != null;
    }

    public static void addARecord (final Record record) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().copyToRealm(record);
            }
        });
    }
}
