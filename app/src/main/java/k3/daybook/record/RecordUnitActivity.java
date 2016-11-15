package k3.daybook.record;

import android.app.Activity;
import android.os.Bundle;

import k3.daybook.R;
import k3.daybook.record.view.RecordUnitView;

public class RecordUnitActivity extends Activity implements RecordUnitView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_unit);
    }
}