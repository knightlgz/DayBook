package k3.daybook.data.constant;

import android.content.Context;
import android.content.SharedPreferences;

import k3.daybook.R;
import k3.daybook.util.ContextProvider;

/**
 * @author Kyson LEE
 */

public class GlobalConfig {

    private static final String userData = "k3studio_daybook";
    private static SharedPreferences mSharedPreference;

    public static int LIMIT_USAGE_SIZE = 10;
    public static int LIMIT_PAYMENT_SIZE = 10;
    public static boolean MERGE_BUDGET = false;
    public static float BUDGET_DANGER = 0.8f;
    public static float BUDGET_FRIENDLY = 0.5f;
    public static int LIMIT_HOME_RECENTLY = 5;
    public static String DEFAULT_USAGE = ContextProvider.getApplicationContext().getResources()
            .getString(R.string.default_usage);
    public static String DEFAULT_PAYMENT = ContextProvider.getApplicationContext().getResources()
            .getString(R.string.default_payment);

    private static final String KEY_MERGE_BUDGET = "budget_merge";
    private static final String KEY_USAGE_LIMIT = "max_usage_size";
    private static final String KEY_PAYMENT_LIMIT = "max_payment_size";
    private static final String KEY_BUDGET_DANGER = "budget_danger";
    private static final String KEY_BUDGET_FRIENDLY = "budget_friendly";
    private static final String KEY_RECENTLY_SHOW = "recently_show";
    private static final String KEY_DEFAULT_USAGE = "default_usage";
    private static final String KEY_DEFAULT_PAYMENT = "default_payment";

    public GlobalConfig() {
    }

    public static void init(Context context) {
        mSharedPreference = context.getSharedPreferences(userData, Context.MODE_PRIVATE);
        LIMIT_USAGE_SIZE = mSharedPreference.getInt(KEY_USAGE_LIMIT, 10);
        LIMIT_PAYMENT_SIZE = mSharedPreference.getInt(KEY_PAYMENT_LIMIT, 10);
        MERGE_BUDGET = mSharedPreference.getBoolean(KEY_MERGE_BUDGET, false);
        BUDGET_DANGER = mSharedPreference.getFloat(KEY_BUDGET_DANGER, 0.8f);
        BUDGET_FRIENDLY = mSharedPreference.getFloat(KEY_BUDGET_FRIENDLY, 0.5f);
        LIMIT_HOME_RECENTLY = mSharedPreference.getInt(KEY_RECENTLY_SHOW, 5);
        DEFAULT_USAGE = mSharedPreference.getString(KEY_DEFAULT_USAGE, ContextProvider
                .getApplicationContext().getResources().getString(R.string.default_usage));
        DEFAULT_PAYMENT = mSharedPreference.getString(KEY_DEFAULT_PAYMENT, ContextProvider
                .getApplicationContext().getResources().getString(R.string.default_payment));
    }

    public static void updateMergeBudget(boolean isMergeBudget) {
        MERGE_BUDGET = isMergeBudget;
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putBoolean(KEY_MERGE_BUDGET, isMergeBudget);
        editor.commit();
    }
}
