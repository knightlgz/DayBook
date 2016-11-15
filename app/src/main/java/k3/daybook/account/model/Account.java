package k3.daybook.account.model;

import java.util.Date;

import io.realm.RealmObject;
import k3.daybook.util.RealmUtil;

/**
 * @author Kyson LEE
 */
public class Account extends RealmObject {

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
    /**
     * the type of the account:
     * 1: cash
     * 2: debit card
     * 3: credit card
     * 4: virtual accounts like paypal, alipay, etc.
     */
    private int type;
    public static final int UNDEFINED = 0;
    public static final int CASH = 1;
    public static final int DEBITCARD = 2;
    public static final int CREDITCARD = 3;
    public static final int VIRTUAL = 4;

    /**
     * the balance of the account
     */
    private float balance;

    // optional information for different kind of account
    /**
     * the date when the card is expire
     */
    private int expire;
    /**
     * the holder of the card
     */
    private String holder;
    /**
     * the limit of the credit card only
     */
    private float limit;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String BALANCE = "balance";
    public static final String EXPIRE = "expire";
    public static final String HOLDER = "holder";
    public static final String LIMIT = "limit";

    public Account() {
        id = RealmUtil.getLatestAccountId() + 1;
        name = "";
        type = UNDEFINED;
        balance = 0;
        expire = 0;
        holder = "";
        limit = 0;
    }

    public void updateAccount(Account account) {
        name = account.getName();
        type = account.getType();
        balance = account.getBalance();
        expire = account.getExpire();
        holder = account.getHolder();
        limit = account.getLimit();
    }

    @SuppressWarnings("deprecation")
    public boolean isAccountValid() {
        Date today = new Date(System.currentTimeMillis());
        if (type == DEBITCARD || type == CREDITCARD) {
            return expire > ((today.getYear() * 100) + today.getMonth());
        }
        return true;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getLimit() {
        return limit;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" + "balance=" + balance + ", id=" + id + ", name='" + name + '\''
                + ", type=" + type + ", expire=" + expire + ", holder='" + holder + '\''
                + ", limit=" + limit + '}';
    }
}
