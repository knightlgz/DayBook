package k3.daybook.setting.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import k3.daybook.R;
import k3.daybook.data.constant.GlobalConfig;
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
        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView BtnAdd = (TextView) v.findViewById(R.id.tv_footer_append);
                BtnAdd.setVisibility(View.GONE);
                final EditText newPayment = (EditText) v.findViewById(R.id.et_setting_add);
                newPayment.setVisibility(View.VISIBLE);
                final ImageView BtnSave = (ImageView) v.findViewById(R.id.iv_setting_save);
                BtnSave.setVisibility(View.VISIBLE);
                BtnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AccountManager.getInstance().addPayment(newPayment.getText().toString());
                        if (getItemCount() <= GlobalConfig.LIMIT_PAYMENTS_SIZE) {
                            BtnAdd.setVisibility(View.VISIBLE);
                        } else {
                            BtnAdd.setVisibility(View.GONE);
                        }
                        newPayment.setText("");
                        newPayment.setVisibility(View.GONE);
                        BtnSave.setVisibility(View.GONE);
                    }
                });
            }
        });

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
            holder.mPaymentName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        holder.mPaymentDelete.setVisibility(View.GONE);
                        holder.mPaymentStore.setVisibility(View.VISIBLE);
                    } else {
                        holder.mPaymentDelete.setVisibility(View.VISIBLE);
                        holder.mPaymentStore.setVisibility(View.GONE);
                    }
                }
            });
            holder.mPaymentDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountManager.getInstance().deletePaymentByIndex(position);
                    notifyDataSetChanged();
                }
            });
            holder.mPaymentStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountManager.getInstance().renamePaymentByIndex(
                            holder.mPaymentName.getText().toString(), position);
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
        ImageView mPaymentStore;

        ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mFooterView) {
                return;
            }
            mPaymentName = (EditText) itemView.findViewById(R.id.item_payment_name);
            mPaymentDelete = (ImageView) itemView.findViewById(R.id.item_payment_delete);
            mPaymentStore = (ImageView) itemView.findViewById(R.id.item_payment_store);
        }
    }
}
