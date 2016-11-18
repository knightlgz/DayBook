package k3.daybook.setting.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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
    public void onBindViewHolder(final PaymentAdapter.ViewHolder holder, final int position) {
        holder.mPaymentName
                .setText(AccountManager.getInstance().getPaymentNameList().get(position));
        holder.mPaymentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                AccountManager.getInstance().renamePaymentByIndex(
                        holder.mPaymentName.getText().toString(), position);
            }
        });
        holder.mPaymentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManager.getInstance().deletePaymentByIndex(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return AccountManager.getInstance().getPaymentNameList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        EditText mPaymentName;
        ImageView mPaymentDelete;

        ViewHolder(View itemView) {
            super(itemView);
            mPaymentName = (EditText) itemView.findViewById(R.id.item_payment_name);
            mPaymentDelete = (ImageView) itemView.findViewById(R.id.item_payment_delete);
        }
    }
}
