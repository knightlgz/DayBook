package k3.daybook.util;

import android.util.Log;

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
                        .getString(R.string.db_name))
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        Log.d(TAG, "initRealm: -----------------");
    }

    public static long getLatestId(Class cls) {
        long id = Realm.getDefaultInstance()
                .where(cls)
                .findAll()
                .max(KEY_ID)
                .longValue();
        Log.d(TAG, "getLatestId of Table " + cls.getSimpleName() + " is: " + id);
        return id;
    }

    public static Account getAccount() {
        Account account = Realm.getDefaultInstance()
                .copyFromRealm(Realm.getDefaultInstance()
                        .where(Account.class)
                        .findFirst());
        Log.d(TAG, "getAccount: " + account);
        return account;
    }

    public static List<Usage> getUsages() {
        List<Usage> usages = Realm.getDefaultInstance()
                .copyFromRealm(Realm.getDefaultInstance()
                        .where(Usage.class)
                        .findAllSorted(KEY_ID));
        Log.d(TAG, "getUsages: " + usages);
        return usages;
    }

    public static void addAnUsage(final Usage usage) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(usage);
            }
        });
    }

    public static void editUsage(final Usage usage) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(usage);
            }
        });
    }

    public static void addAPayment(final Payment payment) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(payment);
            }
        });
    }

    public static void editPayment(final Payment payment) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(payment);
            }
        });
    }
}
