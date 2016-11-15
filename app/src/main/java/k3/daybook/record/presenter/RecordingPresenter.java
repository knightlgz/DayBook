package k3.daybook.record.presenter;

import android.content.Context;

import k3.daybook.record.model.Record;
import k3.daybook.util.VerifyUtil;

/**
 * @author Kyson LEE
 */

public class RecordingPresenter {

    public void verifyInfo(Context context, Record record) {
        if (VerifyUtil.verifyRecord(record)) {
            recordIt(record);
        }
    }

    private void recordIt(Record record) {
//        GreenDAOUtil.getInstance().addARecord(record);
    }
}
