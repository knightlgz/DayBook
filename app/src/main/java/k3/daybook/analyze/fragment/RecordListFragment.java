package k3.daybook.analyze.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import k3.daybook.R;
import k3.daybook.analyze.adapter.RecordsAdapter;
import k3.daybook.data.manager.RecordManager;
import k3.daybook.setting.adapter.DividerItemDecoration;
import k3.daybook.util.DBUtil;

public class RecordListFragment extends android.app.Fragment {

    private RecyclerView rvRecords;
    private Button btnClear;

    private RecordsAdapter mAdapter;

    public RecordListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RecordListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordListFragment newInstance() {
        RecordListFragment fragment = new RecordListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);
        initView(view);
        return view;
    }

    private void initData() {
        mAdapter = new RecordsAdapter();
    }

    private void initView(View view) {
        rvRecords = (RecyclerView) view.findViewById(R.id.analyze_record_list);
        rvRecords.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvRecords.addItemDecoration(new DividerItemDecoration(view.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        rvRecords.setAdapter(mAdapter);

        btnClear = (Button) view.findViewById(R.id.analyze_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtil.clearRecords();
                RecordManager.refreshData();
                rvRecords.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
