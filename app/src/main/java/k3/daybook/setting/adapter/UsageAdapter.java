package k3.daybook.setting.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import k3.daybook.R;
import k3.daybook.data.manager.UsageManager;
import k3.daybook.util.ContextProvider;

/**
 * @author Kyson LEE
 */

public class UsageAdapter extends RecyclerView.Adapter<UsageAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(
                ContextProvider.getApplicationContext())
                .inflate(R.layout.item_usage, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mUsageName.setText(UsageManager.getInstance().getUsages().get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return UsageManager.getInstance().getUsages().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText mUsageName;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsageName = (EditText) itemView.findViewById(R.id.item_usage_name);
        }
    }
}
