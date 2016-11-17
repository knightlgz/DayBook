package k3.daybook.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import k3.daybook.R;
import k3.daybook.data.model.Account;
import k3.daybook.data.model.Payment;
import k3.daybook.data.model.Usage;

/**
 * @author Kyson LEE
 */

public class DBUtil {

    private static final String TAG = "Realm Operations";

    private static final String KEY_ID = "id";

    public static void initRealm() {
        Realm.init(ContextProvider.getApplicationContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(ContextProvider.getApplicationContext().getResources()
                        .getString(R.string.db_name)).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
        Log.d(TAG, "initRealm: -----------------");
    }

    public static long getLatestId(Class cls) {
        Number target = Realm.getDefaultInstance().where(cls).findAll().max(KEY_ID);
        if (target == null) {
            Log.d(TAG, "getLatestId of Table " + cls.getSimpleName() + " is: " + -1);
            return -1;
        }
        Log.d(TAG, "getLatestId of Table " + cls.getSimpleName() + " is: " + target.longValue());
        return target.longValue();
    }

    public static Account getAccount() {
        Account account = Realm.getDefaultInstance().where(Account.class).findFirst();

        if (true) {
            account = demoAccount();
            Log.d(TAG, "getAccount: " + account);
            updateAccount(account);
            return account;
        } else {
            Log.d(TAG, "getAccount: " + account);
            return Realm.getDefaultInstance().copyFromRealm(account);
        }
    }

    private static Account demoAccount() {
        Account demo = new Account();
        demo.setBudget(1000);
        demo.setPeriodDate(9);

        Payment method = new Payment();
        method.setId(0);
        method.setName("cash");
        demo.addPayment(method);

        method = new Payment();
        method.setId(1);
        method.setName("alipay");
        demo.addPayment(method);

        method = new Payment();
        method.setId(2);
        method.setName("wechat");
        demo.addPayment(method);

        return demo;
    }

    public static List<Usage> getUsages() {
        List<Usage> usages = Realm.getDefaultInstance().where(Usage.class).findAllSorted(KEY_ID);
        if (true) {
            usages = demoUsage();
            addUsages(usages);
            Log.d(TAG, "getUsages: " + usages);
            return usages;
        } else {
            Log.d(TAG, "getUsages: " + usages);
            return Realm.getDefaultInstance().copyFromRealm(usages);
        }
    }

    private static List<Usage> demoUsage() {
        List<Usage> demo = new ArrayList<>();

        Usage tem = new Usage();
        tem.setId(0);
        tem.setTitle("Leisure");
        demo.add(tem);

        tem = new Usage();
        tem.setId(1);
        tem.setTitle("Feeding");
        demo.add(tem);

        tem = new Usage();
        tem.setId(2);
        tem.setTitle("Toys");
        demo.add(tem);

        tem = new Usage();
        tem.setId(3);
        tem.setTitle("EXs");
        demo.add(tem);

        return demo;
    }

    public static void addAnUsage(final Usage usage) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(usage);
            }
        });
    }

    private static void addUsages(final List<Usage> usages) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(usages);
            }
        });
    }

    public static void updateUsage(final Usage usage) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(usage);
            }
        });
    }

    public static void updateAllUsages(final List<Usage> usages) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(usages);
            }
        });
    }

    public static void deleteUsage(final Usage usage) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                usage.deleteFromRealm();
            }
        });
    }

    public static void updateAccount(final Account account) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(account);
            }
        });
    }
}
