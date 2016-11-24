package k3.daybook.data.constant;

import android.content.Context;
import android.content.SharedPreferences;

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

    private static final String KEY_MERGE_BUDGET = "budget_merge";
    private static final String KEY_USAGE_LIMIT = "max_usage_size";
    private static final String KEY_PAYMENT_LIMIT = "max_payment_size";
    private static final String KEY_BUDGET_DANGER = "budget_danger";
    private static final String KEY_BUDGET_FRIENDLY = "budget_friendly";
    private static final String KEY_RECENTLY_SHOW = "recently_show";

    public GlobalConfig() {
    }

    public static void init(Context context) {
        mSharedPreference = context.getSharedPreferences(userData,
                Context.MODE_PRIVATE);
        LIMIT_USAGE_SIZE = mSharedPreference.getInt(KEY_USAGE_LIMIT, 10);
        LIMIT_PAYMENT_SIZE = mSharedPreference.getInt(KEY_PAYMENT_LIMIT, 10);
        MERGE_BUDGET = mSharedPreference.getBoolean(KEY_MERGE_BUDGET, false);
        BUDGET_DANGER = mSharedPreference.getFloat(KEY_BUDGET_DANGER, 0.8f);
        BUDGET_FRIENDLY = mSharedPreference.getFloat(KEY_BUDGET_FRIENDLY, 0.5f);
        LIMIT_HOME_RECENTLY = mSharedPreference.getInt(KEY_RECENTLY_SHOW, 5);
    }

    public static void updateMergeBudget(boolean isMergeBudget) {
        MERGE_BUDGET = isMergeBudget;
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putBoolean(KEY_MERGE_BUDGET, isMergeBudget);
        editor.commit();
    }
}
