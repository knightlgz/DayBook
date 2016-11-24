package k3.daybook.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import k3.daybook.R;
import k3.daybook.data.constant.GlobalConfig;
import k3.daybook.data.manager.RecordManager;
import k3.daybook.data.model.Record;
import k3.daybook.util.TimeUtil;

/**
 * @author Kyson LEE
 */

public class RecentlyAdapter extends RecyclerView.Adapter<RecentlyAdapter.RecentlyHolder> {
    @Override
    public RecentlyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecentlyHolder holder = new RecentlyHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecentlyHolder holder, int position) {
        Record data = RecordManager.getInstance().getRecords().get(position);
        holder.date.setText(TimeUtil.convertYMD(data.getDate()));
        holder.amount.setText(String.valueOf(data.getAmount()));
        holder.usage.setText(data.getUsageName());
        holder.payment.setText(data.getPaymentName());
    }

    @Override
    public int getItemCount() {
        return RecordManager.getInstance().getRecords().size() > GlobalConfig.LIMIT_HOME_RECENTLY ? GlobalConfig.LIMIT_HOME_RECENTLY
                : RecordManager.getInstance().getRecords().size();
    }

    public class RecentlyHolder extends RecyclerView.ViewHolder {

        TextView date, amount, usage, payment;

        public RecentlyHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.record_date);
            amount = (TextView) itemView.findViewById(R.id.record_amount);
            usage = (TextView) itemView.findViewById(R.id.record_usage);
            payment = (TextView) itemView.findViewById(R.id.record_payment);
        }
    }
}
