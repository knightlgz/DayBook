package k3.daybook.data.model;

import io.realm.RealmObject;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */
public class Payment extends RealmObject {

    // the primary key of the table account
    /**
     * the unique id for an account
     */
    private long id;

    // the non-null content for the table
    /**
     * the unique name to be shown as the account
     */
    private String name;

    public static final String ID = "id";
    public static final String NAME = "name";

    public Payment() {
        id = DBUtil.getLatestId(Payment.class) + 1;
        name = "";
    }

    public void updateAccount(Payment payment) {
        name = payment.getName();
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
}
