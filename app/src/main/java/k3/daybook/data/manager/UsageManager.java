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

    public void initData() {
        if (sUsages == null) {
            sUsages = new ArrayList<>();
        } else {
            sUsages.clear();
        }
        sUsages=DBUtil.getUsages();
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

    public Usage getAnUsage(int index) {
        return sUsages.get(index);
    }

    public int getUsageSize() {
        return sUsages.size();
    }

    public void addAnUsage(Usage usage) {
        sUsages.add(usage);
        DBUtil.addAnUsage(usage);
    }

    public void renameUsage(String name, int index) {
        sUsages.get(index).renameUsage(name);
        DBUtil.updateUsage(sUsages.get(index));
    }

    public void deleteUsage(int index) {
        DBUtil.deleteUsage(sUsages.get(index));
        sUsages.remove(index);
    }

    public void storeData() {
        DBUtil.updateAllUsages(sUsages);
    }
}
