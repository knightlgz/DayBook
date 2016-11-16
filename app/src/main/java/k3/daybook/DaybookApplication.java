package k3.daybook;

import android.app.Application;
import k3.daybook.util.ContextProvider;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */

public class DaybookApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ContextProvider.initIfNotYet(this);
        DBUtil.initRealm();
    }

}
