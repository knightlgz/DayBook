package k3.daybook.data.manager;

import java.util.ArrayList;
import java.util.List;

import k3.daybook.data.model.Record;
import k3.daybook.util.DBUtil;

/**
 * @author Kyson LEE
 */

public class RecordManager {
    private static final String TAG = "RecordManager";
    private static List<Record> sRecords;
    private static RecordManager sInstance;

    private RecordManager() {
        if (sRecords == null) {
            sRecords = new ArrayList<>();
            initData();
        }
    }

    public static RecordManager getInstance() {
        if (sInstance == null) {
            synchronized (RecordManager.class) {
                if (sInstance == null) {
                    sInstance = new RecordManager();
                }
            }
        }
        return sInstance;
    }

    private static void initData() {
        sRecords = DBUtil.getRecords();
    }

    public List<Record> getRecords() {
        return sRecords;
    }
}
