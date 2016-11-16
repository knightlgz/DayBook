package k3.daybook.setting.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import k3.daybook.R;
import k3.daybook.data.manager.AccountManager;
import k3.daybook.util.ContextProvider;

/**
 * @author Kyson LEE
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new PaymentAdapter.ViewHolder(LayoutInflater.from(
                ContextProvider.getApplicationContext()).inflate(R.layout.item_payment, parent,
                false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PaymentAdapter.ViewHolder holder, int position) {
        holder.mPaymentName.setText(AccountManager.getInstance().getPayments().get(position)
                .getName());
    }

    @Override
    public int getItemCount() {
        return AccountManager.getInstance().getPayments().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText mPaymentName;

        public ViewHolder(View itemView) {
            super(itemView);
            mPaymentName = (EditText) itemView.findViewById(R.id.item_payment_name);
        }
    }
}
