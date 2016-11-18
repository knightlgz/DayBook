package k3.daybook.data.model;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author Kyson LEE
 */

public class Account extends RealmObject {

    @PrimaryKey
    private long id;
    private float budget;
    private int periodDate;
    @Ignore
    private List<String> paymentNames, usageNames;

    public Account() {
        id = 0;
        budget = 0;
        periodDate = 0;
        paymentNames = new ArrayList<>();
        usageNames = new ArrayList<>();
    }

    public void updateAccount(Account account) {
        budget = account.getBudget();
        periodDate = account.getPeriodDate();
        paymentNames.clear();
        paymentNames.addAll(account.getPaymentNames());
        usageNames.clear();
        usageNames.addAll(account.getUsageNames());
    }

    public void refreshUsageNames(List<Usage> usages) {
        if (usageNames == null) {
            usageNames = new ArrayList<>();
        } else {
            usageNames.clear();
        }
        for (Usage usage : usages) {
            usageNames.add(usage.getName());
        }
    }

    public void refreshPaymentNames(List<Payment> payments) {
        if (paymentNames == null) {
            paymentNames = new ArrayList<>();
        } else {
            paymentNames.clear();
        }
        for (Payment payment :
                payments) {
            paymentNames.add(payment.getName());
        }
    }

    public void renameUsageNameByIndex(String newName, int index) {
        usageNames.set(index, newName);
    }

    public void renamePaymentNameByIndex(String newName, int index) {
        paymentNames.set(index, newName);
    }

    public void deleteUsageNameByIndex(int index) {
        usageNames.remove(index);
    }

    public void deletePaymentNameByIndex(int index) {
        paymentNames.remove(index);
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(int periodDate) {
        this.periodDate = periodDate;
    }

    public List<String> getPaymentNames() {
        return paymentNames;
    }

    public void setPaymentNames(List<String> paymentNames) {
        this.paymentNames = paymentNames;
    }

    public List<String> getUsageNames() {
        return usageNames;
    }

    public void setUsageNames(List<String> usageNames) {
        this.usageNames = usageNames;
    }

    @Override
    public String toString() {
        return "Account{" +
                "budget=" + budget +
                ", id=" + id +
                ", periodDate=" + periodDate +
                ", paymentNames=" + paymentNames +
                ", usageNames=" + usageNames +
                '}';
    }
}
