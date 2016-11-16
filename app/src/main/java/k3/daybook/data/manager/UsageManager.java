package k3.daybook.data.manager;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import k3.daybook.data.model.Usage;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */

public class UsageManager {

    private static final String TAG = "UsageManager";

    private static UsageManager sInstance;

    private static List<Usage> sUsages;

    private void initData() {
        if (sUsages == null) {
            sUsages = new ArrayList<>();
        } else {
            sUsages.clear();
        }
        sUsages.addAll(DBUtil.getUsages());
        Log.d(TAG, "initData: " + sUsages);
    }

    private UsageManager() {
        initData();
    }

    public static UsageManager getInstance() {
        if (sInstance == null) {
            synchronized (UsageManager.class) {
                if (sInstance == null) {
                    sInstance = new UsageManager();
                }
            }
        }
        return sInstance;
    }

    public List<Usage> getUsages() {
        return sUsages;
    }

    public void addAnUsage(Usage usage) {
        sUsages.add(usage);
        DBUtil.addAnUsage(usage);
    }

    public void editUsage(int position, Usage newUsage) {
        sUsages.get(position).updateUsage(newUsage);
        DBUtil.editUsage(newUsage);
    }
}
