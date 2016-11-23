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

    public Account() {
        id = 0;
        budget = 0;
        periodDate = 0;
    }

    public void updateAccount(Account account) {
        budget = account.getBudget();
        periodDate = account.getPeriodDate();
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

    @Override
    public String toString() {
        return "Account{" +
                "budget=" + budget +
                ", id=" + id +
                ", periodDate=" + periodDate +
                '}';
    }
}
