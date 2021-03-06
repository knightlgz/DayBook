package k3.daybook.util;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.Sort;
import k3.daybook.R;
import k3.daybook.data.model.Account;
import k3.daybook.data.model.Payment;
import k3.daybook.data.model.Record;
import k3.daybook.data.model.Usage;

/**
 * @author Kyson LEE
 */

public class DBUtil {

    private static final String TAG = "Realm Operations";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_USAGE_NAME = "usageName";
    private static final String KEY_PAYMENT_NAME = "paymentName";
    private static final String KEY_AMOUNT = "amount";

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
            Account result = Realm.getDefaultInstance().copyFromRealm(account);
            Log.d(TAG, "getAccount: " + result);
            return result;
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

    public static List<Record> getRecords() {
        List<Record> records = Realm.getDefaultInstance().where(Record.class)
                .findAllSorted(KEY_DATE, Sort.DESCENDING);
        if (records.size() == 0) {
            Log.d(TAG, "getRecords: empty db");
        } else {
            Log.d(TAG, "getRecords: " + records);
        }
        return Realm.getDefaultInstance().copyFromRealm(records);
    }

    /**
     * Common Updater
     */
    public static void updateAccount(final Account newAccount) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(newAccount);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: update Account failed");
            }
        });
    }

    public static void updateUsages(final List<Usage> newUsages) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Usage tem : newUsages) {
                    realm.insertOrUpdate(tem);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: update Usages failed");
            }
        });
    }

    public static void updatePayments(final List<Payment> newPayments) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Payment tem : newPayments) {
                    realm.insertOrUpdate(tem);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: update Payments failed");
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

        updateAccount(account);

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

    public static void deleteUsageById(final long id) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Usage usage = Realm.getDefaultInstance().where(Usage.class).equalTo(KEY_ID, id)
                        .findFirst();
                usage.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: delete usage failed");
            }
        });
    }

    public static void deletePaymentById(final long id) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Payment payment = Realm.getDefaultInstance().where(Payment.class)
                        .equalTo(KEY_ID, id).findFirst();
                payment.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: delete payment succeed");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                try {
                    throw error;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Log.d(TAG, "onError: delete payment failed");
            }
        });
    }

    /**
     * CLEAR
     */
    public static void clearRecords() {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Record.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: clear records failed");
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

    public static void addRecord(final Record record) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().insertOrUpdate(record);
                Usage usage = realm.where(Usage.class).equalTo(KEY_NAME, record.getUsageName())
                        .findFirst();
                if (usage != null) {
                    usage.utilized();
                }
                Payment payment = realm.where(Payment.class)
                        .equalTo(KEY_NAME, record.getPaymentName()).findFirst();
                if (payment != null) {
                    payment.utilized();
                }
            }
        });
    }

    /**
     * Conditional Query
     */
    @Nullable
    public static Usage getLastSelectedUsage() {
        List<Record> recordList = Realm.getDefaultInstance().where(Record.class)
                .findAllSorted(KEY_ID, Sort.DESCENDING);

        if (recordList.size() == 0) {
            Log.d(TAG, "getLastSelectedUsage: no records");
            return null;
        }

        String name = recordList.get(0).getUsageName();
        Log.d(TAG, "getLastSelectedUsage: the last selected usage is: " + name);
        Usage result = Realm.getDefaultInstance().where(Usage.class).equalTo(KEY_NAME, name)
                .findFirst();
        if (result == null) {
            Log.d(TAG, "getLastSelectedUsage: could not find usage with name [" + name + "]");
            return null;
        } else {
            Log.d(TAG, "getLastSelectedUsage: " + result);
            return Realm.getDefaultInstance().copyFromRealm(result);
        }
    }

    @Nullable
    public static Payment getLastSelectedPayment() {
        List<Record> recordList = Realm.getDefaultInstance().where(Record.class)
                .findAllSorted(KEY_ID, Sort.DESCENDING);

        if (recordList.size() == 0) {
            Log.d(TAG, "getLastSelectedPayment: norecords");
            return null;
        }

        String name = recordList.get(0).getPaymentName();
        Log.d(TAG, "getLastSelectedPayment: the last selected usage is: " + name);
        Payment result = Realm.getDefaultInstance().where(Payment.class).equalTo(KEY_NAME, name)
                .findFirst();
        if (result == null) {
            Log.d(TAG, "getLastSelectedPayment: could not find usage with name [" + name + "]");
            return null;
        } else {
            Log.d(TAG, "getLastSelectedPayment: " + result);
            return Realm.getDefaultInstance().copyFromRealm(result);
        }
    }

    public static Usage getLastWeekMostSelectedUsage() {
        List<Record> records = Realm.getDefaultInstance().where(Record.class)
                .greaterThan(KEY_DATE, TimeUtil.oneWeekAgo()).findAll().sort(KEY_USAGE_NAME);
        if (records.size() == 0) {
            Log.d(TAG, "getLastWeekMostSelectedUsage: no records in the recent 7 days");
        } else {
            Log.d(TAG, "getLastWeekMostSelectedUsage: " + records);
        }
        String maxUsageName = "";
        int max = 0;
        int counter = 0;
        String name = "";
        for (int index = 0; index < records.size(); index++) {
            if (!name.equals(records.get(index).getUsageName())) {
                name = records.get(index).getUsageName();
                counter = 0;
            }
            counter++;
            if (max < counter) {
                max = counter;
                maxUsageName = records.get(index).getUsageName();
            }
        }
        Log.d(TAG, "getLastWeekMostSelectedUsage: " + maxUsageName + " being selected " + max
                + "times");
        Usage result = Realm.getDefaultInstance().where(Usage.class)
                .equalTo(KEY_NAME, maxUsageName).findFirst();
        if (result == null) {
            return result;
        } else {
            return Realm.getDefaultInstance().copyFromRealm(result);
        }
    }

    public static Payment getLastWeekMostSelectedPayment() {
        List<Record> records = Realm.getDefaultInstance().where(Record.class)
                .greaterThan(KEY_DATE, TimeUtil.oneWeekAgo()).findAll().sort(KEY_PAYMENT_NAME);
        if (records.size() == 0) {
            Log.d(TAG, "getLastWeekMostSelectedPayment: no records in the recent 7 days");
        } else {
            Log.d(TAG, "getLastWeekMostSelectedPayment: " + records);
        }
        String maxPaymentName = "";
        int max = 0;
        int counter = 0;
        String name = "";
        for (int index = 0; index < records.size(); index++) {
            if (!name.equals(records.get(index).getPaymentName())) {
                name = records.get(index).getPaymentName();
                counter = 0;
            }
            counter++;
            if (max < counter) {
                max = counter;
                maxPaymentName = records.get(index).getPaymentName();
            }
        }
        Log.d(TAG, "getLastWeekMostSelectedPayment: " + maxPaymentName + " being selected " + max
                + " times");
        Payment result = Realm.getDefaultInstance().where(Payment.class)
                .equalTo(KEY_NAME, maxPaymentName).findFirst();
        if (result == null) {
            return result;
        } else {
            return Realm.getDefaultInstance().copyFromRealm(result);
        }
    }

    public static float getConsumedBudgetOfLastPeriod() {
        int accountingDay = getAccount().getPeriodDate();
        Number result = Realm.getDefaultInstance().where(Record.class)
                .greaterThan(KEY_DATE, TimeUtil.lastAccountingDay(accountingDay)).findAll()
                .sum(KEY_AMOUNT);
        if (result == null) {
            Log.d(TAG, "getConsumedBudgetOfLastPeriod: no data for last period");
            return 0;
        }
        Log.d(TAG, "getConsumedBudgetOfLastPeriod: " + result.floatValue());
        return result.floatValue();
    }

    /**
     * Conditional update
     */
    public static void updateRecordsWithUsageChanging(final String oldName, final String newName) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<Record> recordList = realm.where(Record.class)
                        .equalTo(KEY_USAGE_NAME, oldName).findAll();
                for (Record record : recordList) {
                    record.setUsageName(newName);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: updateRecordsWithUsageChanging failed");
            }
        });
    }

    public static void updateRecordsWithPaymentChanging(final String oldName, final String newName) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<Record> recordList = realm.where(Record.class)
                        .equalTo(KEY_PAYMENT_NAME, oldName).findAll();
                for (Record record : recordList) {
                    record.setPaymentName(newName);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "onError: updateRecordsWithPaymentChanging failed");
            }
        });
    }
}
