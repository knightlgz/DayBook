package k3.daybook.setting.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import k3.daybook.R;
import k3.daybook.data.manager.AccountManager;
import k3.daybook.util.ContextProvider;

/**
 * @author Kyson LEE
 */

public class UsageAdapter extends RecyclerView.Adapter<UsageAdapter.ViewHolder> {
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
                final EditText newUsage = (EditText) v.findViewById(R.id.et_setting_add);
                newUsage.setVisibility(View.VISIBLE);
                final ImageView BtnSave = (ImageView) v.findViewById(R.id.iv_setting_save);
                BtnSave.setVisibility(View.VISIBLE);
                BtnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AccountManager.getInstance().addUsage(newUsage.getText().toString());
                        BtnAdd.setVisibility(View.VISIBLE);
                        newUsage.setVisibility(View.GONE);
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
        if (viewType == TYPE_FOOTER) {
            return new ViewHolder(mFooterView);
        }
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(
                ContextProvider.getApplicationContext())
                .inflate(R.layout.item_usage, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            holder.mUsageName
                    .setText(AccountManager.getInstance().getUsageNameList().get(position));
            holder.mUsageName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    AccountManager.getInstance().renameUsageByIndex(
                            holder.mUsageName.getText().toString(), position);
                }
            });
            holder.mUsageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountManager.getInstance().deleteUsageByIndex(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mFooterView == null) {
            return AccountManager.getInstance().getUsageNameList().size();
        }
        return AccountManager.getInstance().getUsageNameList().size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText mUsageName;
        ImageView mUsageDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mFooterView) {
                return;
            }
            mUsageName = (EditText) itemView.findViewById(R.id.item_usage_name);
            mUsageDelete = (ImageView) itemView.findViewById(R.id.item_usage_delete);
        }
    }
}
