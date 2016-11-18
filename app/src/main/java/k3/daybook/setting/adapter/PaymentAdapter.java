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
    private final int TYPE_ITEM = 0;
    private final int TYPE_FOOTER = 1;

    private View mFooterView;

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER && mFooterView != null) {
            return new ViewHolder(mFooterView);
        }
        ViewHolder viewHolder = new PaymentAdapter.ViewHolder(LayoutInflater.from(
                ContextProvider.getApplicationContext()).inflate(R.layout.item_payment, parent,
                false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PaymentAdapter.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            holder.mPaymentName.setText(AccountManager.getInstance().getPaymentNameList()
                    .get(position));
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
    }

    @Override
    public int getItemCount() {
        if (mFooterView == null) {
            return AccountManager.getInstance().getPaymentNameList().size();
        }
        return AccountManager.getInstance().getPaymentNameList().size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        EditText mPaymentName;
        ImageView mPaymentDelete;

        ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mFooterView) {
                return;
            }
            mPaymentName = (EditText) itemView.findViewById(R.id.item_payment_name);
            mPaymentDelete = (ImageView) itemView.findViewById(R.id.item_payment_delete);
        }
    }
}
