package k3.daybook.home.presenter;

import android.content.Context;
import android.content.Intent;

import k3.daybook.analyze.AnalyzeActivity;
import k3.daybook.recording.RecordingActivity;
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

    public void jumpToAnalyze(Context context) {
        mIntent = new Intent(context, AnalyzeActivity.class);
        context.startActivity(mIntent);
    }

    public void jumpToRecording(Context context) {
        mIntent = new Intent(context, RecordingActivity.class);
        context.startActivity(mIntent);
    }

}
