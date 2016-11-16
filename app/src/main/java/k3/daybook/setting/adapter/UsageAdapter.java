package k3.daybook.setting.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import k3.daybook.R;
import k3.daybook.data.manager.UsageManager;

/**
 * @author Kyson LEE
 */

public class UsageAdapter extends RecyclerView.Adapter<UsageAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvUsageName.setText(UsageManager.getInstance().getUsages().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return UsageManager.getInstance().getUsages().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsageName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsageName = (TextView) itemView.findViewById(R.id.tv_item_usage_name);
        }
    }
}
