package k3.daybook.home.presenter;

import android.content.Context;
import android.content.Intent;

import k3.daybook.record.RecordingActivity;
import k3.daybook.management.ManagementActivity;
import k3.daybook.settings.SettingActivity;

/**
 * @author Kyson LEE
 */

public class HomePresenter {

    private Intent mIntent = null;

    public void jumpToRecording(Context context) {
        mIntent = new Intent(context, RecordingActivity.class);
        context.startActivity(mIntent);
    }

    public void jumpToManagement(Context context) {
        mIntent = new Intent(context, ManagementActivity.class);
        context.startActivity(mIntent);
    }

    public void jumpToSetting(Context context) {
        mIntent = new Intent(context, SettingActivity.class);
        context.startActivity(mIntent);
    }

}
