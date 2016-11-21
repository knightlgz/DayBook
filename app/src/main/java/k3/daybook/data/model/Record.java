package k3.daybook.data.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */
public class Record extends RealmObject {

    @PrimaryKey
    private long id;
    private long date;

    private float amount;

    private String usageName;
    private String paymentName;

    public Record() {
        id = DBUtil.getLatestId(Record.class) + 1;
        date = System.currentTimeMillis();
        amount = 0;
        usageName = "";
        paymentName = "";
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getUsageName() {
        return usageName;
    }

    public void setUsageName(String usageName) {
        this.usageName = usageName;
    }
}
