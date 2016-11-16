package k3.daybook.home.presenter;

import android.content.Context;
import android.content.Intent;

import k3.daybook.setting.SettingActivity;

/**
 * @author Kyson LEE
 */

public class HomePresenter {

    private Intent mIntent = null;

    public void jumpToSetting(Context context) {
        mIntent = new Intent(context, SettingActivity.class);
        context.startActivity(mIntent);
    }

}
