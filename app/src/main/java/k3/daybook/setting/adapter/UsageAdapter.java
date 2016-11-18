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

public class UsageAdapter extends RecyclerView.Adapter<UsageAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(
                ContextProvider.getApplicationContext())
                .inflate(R.layout.item_usage, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mUsageName.setText(AccountManager.getInstance().getUsageNameList().get(position));
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

    @Override
    public int getItemCount() {
        return AccountManager.getInstance().getUsageNameList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText mUsageName;
        ImageView mUsageDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mUsageName = (EditText) itemView.findViewById(R.id.item_usage_name);
            mUsageDelete = (ImageView) itemView.findViewById(R.id.item_usage_delete);
        }
    }
}
