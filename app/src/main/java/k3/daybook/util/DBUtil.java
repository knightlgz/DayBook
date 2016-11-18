package k3.daybook.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
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
    private static final String KEY_NAME = "name";

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

    /**
     * Common Getters
     */
    public static Account getAccount() {
        Account account = Realm.getDefaultInstance().where(Account.class).findFirst();
        if (account == null) {
            account = demoAccount();
            Log.d(TAG, "getAccount: empty db, demoed data: " + account);
            return account;
        } else {
            Log.d(TAG, "getAccount: " + account);
            return Realm.getDefaultInstance().copyFromRealm(account);
        }
    }

    public static List<Usage> getUsages() {
        List<Usage> usages = Realm.getDefaultInstance().where(Usage.class).findAll();
        if (usages.size() == 0) {
            usages = demoUsages();
            Log.d(TAG, "getUsages: empty db, demoed data: " + usages);
            return usages;
        } else {
            Log.d(TAG, "getUsages: " + usages);
            return Realm.getDefaultInstance().copyFromRealm(usages);
        }
    }

    public static List<Payment> getPayments() {
        List<Payment> payments = Realm.getDefaultInstance().where(Payment.class).findAll();
        if (payments.size() == 0) {
            payments = demoPayments();
            Log.d(TAG, "getPayments: empty db, demoed data: " + payments);
            return payments;
        } else {
            Log.d(TAG, "getPayments: " + payments);
            return Realm.getDefaultInstance().copyFromRealm(payments);
        }
    }

    /**
     * Common Updater
     */
    public static void updateAmmount(final Account newAccount) {
        final Account account = Realm.getDefaultInstance().where(Account.class).findFirst();
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                account.updateAccount(newAccount);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public static void updateUsages(final List<Usage> newUsages) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Usage tem : newUsages) {
                    Usage usage = Realm.getDefaultInstance().where(Usage.class)
                            .equalTo(KEY_ID, tem.getId()).findFirst();
                    usage.updateUsage(tem);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public static void updatePayments(final List<Payment> newPayments) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Payment tem : newPayments) {
                    Payment payment = Realm.getDefaultInstance().where(Payment.class)
                            .equalTo(KEY_ID, tem.getId()).findFirst();
                    payment.updatePayment(tem);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    /**
     * Common Demo Init
     */
    private static Account demoAccount() {
        Account account = new Account();
        account.setPeriodDate(10);
        account.setBudget(3000);
        account.refreshUsageNames(demoUsages());
        account.refreshPaymentNames(demoPayments());

        updateAmmount(account);

        return account;
    }

    private static List<Usage> demoUsages() {
        List<Usage> list = new ArrayList<>();

        Usage usage = new Usage();
        usage.setId(0);
        usage.setName("Rent");
        list.add(usage);

        usage = new Usage();
        usage.setId(1);
        usage.setName("Food");
        list.add(usage);

        usage = new Usage();
        usage.setId(2);
        usage.setName("Toys");
        list.add(usage);

        usage = new Usage();
        usage.setId(3);
        usage.setName("4EXs");
        list.add(usage);

        updateUsages(list);

        return list;
    }

    private static List<Payment> demoPayments() {
        List<Payment> list = new ArrayList<>();

        Payment payment = new Payment();
        payment.setId(0);
        payment.setName("Cash");
        list.add(payment);

        payment = new Payment();
        payment.setId(1);
        payment.setName("alipay");
        list.add(payment);

        payment = new Payment();
        payment.setId(2);
        payment.setName("wechat");
        list.add(payment);

        payment = new Payment();
        payment.setId(3);
        payment.setName("Credit Card");
        list.add(payment);

        payment = new Payment();
        payment.setId(4);
        payment.setName("Debit Card");
        list.add(payment);

        updatePayments(list);

        return list;
    }

    /**
     * Common Detector
     */
    public static boolean isNameExist(String name, Class cls) {
        RealmModel result = Realm.getDefaultInstance().where(cls).equalTo(KEY_NAME, name)
                .findFirst();
        return result != null;
    }

    /**
     * Conditional Delete
     */
    public static void deleteUsageByName(String name) {
        final Usage usage = Realm.getDefaultInstance().where(Usage.class).equalTo(KEY_NAME, name)
                .findFirst();
        if (usage == null) {
            return;
        }
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                usage.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    public static void deletePaymentByName(String name) {
        final Payment payment = Realm.getDefaultInstance().where(Payment.class)
                .equalTo(KEY_NAME, name).findFirst();
        if (payment == null) {
            return;
        }
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                payment.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    /**
     * Appender
     */
    public static void addPayment(final Payment payment) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().insertOrUpdate(payment);
            }
        });
    }

    public static void addUsage(final Usage usage) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().insertOrUpdate(usage);
            }
        });
    }

}
