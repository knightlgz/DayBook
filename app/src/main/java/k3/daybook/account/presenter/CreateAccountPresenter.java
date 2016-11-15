package k3.daybook.account.presenter;

import k3.daybook.account.model.Account;
import k3.daybook.data.DataFactory;

/**
 * @author Kyson LEE
 */

public class CreateAccountPresenter {

    public void AddAccount(Account account) {
        DataFactory.getInstance().addAnAccount(account);
    }

}
