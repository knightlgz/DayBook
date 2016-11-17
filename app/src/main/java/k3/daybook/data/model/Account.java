package k3.daybook.data.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Kyson LEE
 */

public class Account extends RealmObject{

    @PrimaryKey
    private long id;
    private float budget;
    private int periodDate;
    private RealmList<Payment> payments;

    public Account() {
        id = 0;
        budget = 0;
        periodDate = -1;
        payments = new RealmList<>();
    }

    public void updateAccount(Account account) {
        id = 0;
        budget = account.getBudget();
        periodDate = account.getPeriodDate();
        payments = account.getPayments();
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void deletePayment(int index) {
        payments.remove(index);
    }

    public void renamePayment(String title, int index) {
        payments.get(index).rename(title);
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

    public RealmList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(RealmList<Payment> payments) {
        this.payments = payments;
    }

    public int getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(int periodDate) {
        this.periodDate = periodDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "budget=" + budget +
                ", id=" + id +
                ", periodDate=" + periodDate +
                ", payments=" + payments +
                '}';
    }
}
