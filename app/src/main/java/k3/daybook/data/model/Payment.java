package k3.daybook.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */
public class Payment extends RealmObject {

    @PrimaryKey
    private long id;

    private String name;
    private int times;

    public Payment() {
        id = DBUtil.getLatestId(Payment.class) + 1;
        name = "";
        times = 0;
    }

    public void updatePayment(Payment payment) {
        name = payment.getName();
        times = payment.getTimes();
    }

    public void rename(String title) {
        name = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", times=" + times +
                '}';
    }
}
