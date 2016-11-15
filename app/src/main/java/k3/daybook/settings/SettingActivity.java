package k3.daybook.settings;

import android.app.Activity;
import android.os.Bundle;

import k3.daybook.R;
import k3.daybook.settings.view.SettingView;

public class SettingActivity extends Activity implements SettingView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
