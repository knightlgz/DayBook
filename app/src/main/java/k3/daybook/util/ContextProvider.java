package k3.daybook.util;

import android.content.Context;

/**
 * @author Kyson LEE
 */

public final class ContextProvider {
    private static Context sContext = null;

    public static void initIfNotYet(Context context) {
        if (sContext == null) {
            init(context);
        }
    }

    /**
     * This function should be invoked in Application while the application is being created.
     * @param context
     */
    public static void init(Context context) {
        if (context == null) {
            throw new NullPointerException("Can not use null initlialized application context");
        }
        sContext = context;
    }

    /**
     * Get application context.
     * @return
     */
    public static Context getApplicationContext() {
        if (sContext == null) {
            throw new NullPointerException("Global application uninitialized");
        }
        return sContext;
    }

    private ContextProvider() {
    }
}
