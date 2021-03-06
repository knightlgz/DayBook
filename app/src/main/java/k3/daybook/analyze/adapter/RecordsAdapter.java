package k3.daybook.analyze.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import k3.daybook.R;
import k3.daybook.data.manager.RecordManager;
import k3.daybook.data.model.Record;
import k3.daybook.util.ContextProvider;
import k3.daybook.util.TimeUtil;

/**
 * @author Kyson LEE
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsHolder> {
    @Override
    public RecordsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecordsHolder viewHolder = new RecordsHolder(LayoutInflater.from(
                ContextProvider.getApplicationContext()).inflate(R.layout.item_record, parent,
                false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecordsHolder holder, int position) {
        Record data = RecordManager.getInstance().getRecords().get(position);
        holder.date.setText(TimeUtil.convertYMD(data.getDate()));
        holder.amount.setText(String.valueOf(data.getAmount()));
        holder.usage.setText(data.getUsageName());
        holder.payment.setText(data.getPaymentName());
    }

    @Override
    public int getItemCount() {
        return RecordManager.getInstance().getRecords().size();
    }

    public class RecordsHolder extends RecyclerView.ViewHolder {
        TextView date, amount, usage, payment;

        public RecordsHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.record_date);
            amount = (TextView) itemView.findViewById(R.id.record_amount);
            usage = (TextView) itemView.findViewById(R.id.record_usage);
            payment = (TextView) itemView.findViewById(R.id.record_payment);
        }
    }
}
