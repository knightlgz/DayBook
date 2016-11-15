package k3.daybook.record;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import k3.daybook.R;
import k3.daybook.record.callbacks.RecordingCompleteListener;
import k3.daybook.record.model.Record;
import k3.daybook.record.presenter.RecordingPresenter;
import k3.daybook.record.view.RecordingView;

/**
 * @author Kyson LEE
 */

public class RecordingActivity extends Activity implements RecordingView, RecordingCompleteListener {

    private Switch mSwitch;
    private EditText mAmount, mComment;
    private Spinner mConsume, mPay;
    private Button mConfirm;

    private ArrayAdapter<CharSequence> mConsumeAdapter;
    private ArrayAdapter<String> mPayAdapter;

    private RecordingPresenter mPresenter;
    private Record mRecord;
    private boolean isGain;

    @Override
    public void onSuccess() {
        Toast.makeText(this, R.string.toast_recording_succeed, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFail() {
        Toast.makeText(this, R.string.toast_recording_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        mPresenter = new RecordingPresenter();
        mRecord = new Record();
        isGain = false;
    }

    private void initView() {
        mSwitch = (Switch) findViewById(R.id.inOrOut);
        mAmount = (EditText) findViewById(R.id.amount);
        mConsume = (Spinner) findViewById(R.id.consume);
        mPay = (Spinner) findViewById(R.id.pay_method);
        mComment = (EditText) findViewById(R.id.comment);
        mConfirm = (Button) findViewById(R.id.confirm);

        mConsumeAdapter = ArrayAdapter.createFromResource(this, R.array.consumes,
                android.R.layout.simple_spinner_dropdown_item);
        mConsume.setAdapter(mConsumeAdapter);

//        mPayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GreenDAOUtil
//                .getInstance().getAccountNames());
        mPay.setAdapter(mPayAdapter);
    }

    private void initListener() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGain = isChecked;
            }
        });

        mConsume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRecord.setConsume(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mPay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRecord.setAccountId(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mRecord.setAccountId(0);
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGain) {
                    mRecord.setAmount(Float.parseFloat(mAmount.getText().toString()));
                } else {
                    mRecord.setAmount(0 - Float.parseFloat(mAmount.getText().toString()));
                }
                mRecord.setComment(mComment.getText().toString());
                confirm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        GreenDAOUtil.getInstance().releaseAsyncTask();
    }

    @Override
    public void confirm() {
        mPresenter.verifyInfo(this, mRecord);
    }
}
