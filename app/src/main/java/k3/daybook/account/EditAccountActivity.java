package k3.daybook.account;

import android.app.Activity;
import android.os.Bundle;

import k3.daybook.R;
import k3.daybook.account.view.EditAccountView;

public class EditAccountActivity extends Activity implements EditAccountView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
    }
}
