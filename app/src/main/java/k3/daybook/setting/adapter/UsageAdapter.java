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

public class UsageAdapter extends RecyclerView.Adapter<UsageAdapter.UsageHolder> {
    private final int TYPE_ITEM = 0;
    private final int TYPE_FOOTER = 1;

    private View mFooterView;

    public void setFooterView(View footerView) {
        mFooterView = footerView;

        final TextView BtnAdd = (TextView) mFooterView.findViewById(R.id.tv_footer_append);
        final EditText newUsage = (EditText) mFooterView.findViewById(R.id.et_setting_add);
        final ImageView BtnSave = (ImageView) mFooterView.findViewById(R.id.iv_setting_save);

        mFooterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BtnAdd.setVisibility(View.GONE);
                newUsage.setVisibility(View.VISIBLE);
                newUsage.requestFocus();
                BtnSave.setVisibility(View.VISIBLE);
            }
        });

        newUsage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    BtnAdd.setVisibility(View.VISIBLE);
                    newUsage.setText("");
                    newUsage.setVisibility(View.GONE);
                    BtnSave.setVisibility(View.GONE);
                }
            }
        });

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManager.getInstance().addUsage(newUsage.getText().toString());
                newUsage.setText("");
                newUsage.setVisibility(View.GONE);
                BtnSave.setVisibility(View.GONE);
                notifyDataSetChanged();
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
    public UsageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new UsageHolder(mFooterView);
        }
        UsageHolder viewHolder = new UsageHolder(LayoutInflater.from(
                ContextProvider.getApplicationContext())
                .inflate(R.layout.item_usage, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UsageHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            holder.mUsageName.setText(AccountManager.getInstance().getUsageNameByIndex(position));
            holder.mUsageName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        holder.mUsageDelete.setVisibility(View.GONE);
                        holder.mUsageStore.setVisibility(View.VISIBLE);
                    } else {
                        holder.mUsageDelete.setVisibility(View.VISIBLE);
                        holder.mUsageStore.setVisibility(View.GONE);
                    }
                }
            });
            holder.mUsageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountManager.getInstance().deleteUsageByIndex(position);
                    notifyDataSetChanged();
                }
            });
            holder.mUsageStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountManager.getInstance().renameUsageByIndex(
                            holder.mUsageName.getText().toString(), position);
                    holder.mUsageStore.setVisibility(View.GONE);
                    holder.mUsageDelete.setVisibility(View.VISIBLE);
                    holder.mUsageName.clearFocus();
                }
            });
        } else if (getItemViewType(position) == TYPE_FOOTER) {
            if (getItemCount() <= GlobalConfig.LIMIT_PAYMENT_SIZE) {
                mFooterView.findViewById(R.id.tv_footer_append).setVisibility(View.VISIBLE);
            } else {
                mFooterView.findViewById(R.id.tv_footer_append).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mFooterView == null) {
            return AccountManager.getInstance().getUsageSize();
        }
        return AccountManager.getInstance().getUsageSize() + 1;
    }

    public class UsageHolder extends RecyclerView.ViewHolder {

        EditText mUsageName;
        ImageView mUsageDelete;
        ImageView mUsageStore;

        public UsageHolder(View itemView) {
            super(itemView);
            if (itemView == mFooterView) {
                return;
            }
            mUsageName = (EditText) itemView.findViewById(R.id.item_usage_name);
            mUsageDelete = (ImageView) itemView.findViewById(R.id.item_usage_delete);
            mUsageStore = (ImageView) itemView.findViewById(R.id.item_usage_store);
        }
    }
}
