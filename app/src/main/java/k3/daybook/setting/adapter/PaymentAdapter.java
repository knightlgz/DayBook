package k3.daybook.setting.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import k3.daybook.util.DataProvider;

/**
 * @author Kyson LEE
 */

public class PaymentAdapter extends RecyclerView.Adapter<UsageAdapter.ViewHolder> {
    @Override
    public UsageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(UsageAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return DataProvider.getInstance().getPayments().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
