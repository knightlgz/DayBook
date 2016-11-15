package k3.daybook.account.adapter;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import k3.daybook.R;
import k3.daybook.account.model.Account;
import k3.daybook.data.DataFactory;

/**
 * @author Kyson LEE
 */

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder> {

    public AccountsAdapter() {
        updateData();
    }

    class AccountsViewHolder extends RecyclerView.ViewHolder {
        ToggleButton isValid;
        TextView title, balance, expire, limit;
        Context mContext;

        AccountsViewHolder(View itemView) {
            super(itemView);
            isValid = (ToggleButton) itemView.findViewById(R.id.item_accounts_validity);
            title = (TextView) itemView.findViewById(R.id.item_accounts_title);
            balance = (TextView) itemView.findViewById(R.id.item_accounts_balance);
            expire = (TextView) itemView.findViewById(R.id.item_accounts_expire);
            limit = (TextView) itemView.findViewById(R.id.item_accounts_limit);
            mContext = itemView.getContext();
        }

        public Context getContext() {
            return mContext;
        }

        public String getString(@StringRes int stringId) {
            return getContext().getResources().getString(stringId);
        }
    }

    @Override
    public AccountsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AccountsViewHolder viewHolder = new AccountsViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_accounts, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AccountsViewHolder holder, int position) {
        Account currentAccount = DataFactory.getInstance().getAccounts().get(position);
        holder.title.setText(String.format(holder.getString(R.string.account_name),
                currentAccount.getName()));
        holder.balance.setText(String.format(holder.getString(R.string.account_balance),
                currentAccount.getBalance()));

        if (currentAccount.getType() == Account.DEBITCARD) {
            holder.expire.setText(String.format(holder.getString(R.string.account_expire),
                    currentAccount.getExpire()));
            holder.limit.setText(R.string.parameter_not_mentioned);
        } else if (currentAccount.getType() == Account.CREDITCARD) {
            holder.expire.setText(String.format(holder.getString(R.string.account_expire),
                    currentAccount.getExpire()));
            holder.limit.setText(String.format(holder.getString(R.string.account_limit),
                    String.valueOf(currentAccount.getLimit())));
        } else {
            holder.expire.setText(String.format(holder.getString(R.string.account_expire),
                    holder.getString(R.string.parameter_not_mentioned)));
            holder.limit.setText(String.format(holder.getString(R.string.account_limit),
                    holder.getString(R.string.parameter_not_mentioned)));
        }

        if (currentAccount.isAccountValid()) {
            holder.isValid.setChecked(true);
            holder.title.setTextColor(holder.getContext().getResources()
                    .getColor(R.color.account_valid));
            holder.balance.setTextColor(holder.getContext().getResources()
                    .getColor(R.color.account_valid));
            holder.expire.setTextColor(holder.getContext().getResources()
                    .getColor(R.color.account_valid));
            holder.limit.setTextColor(holder.getContext().getResources()
                    .getColor(R.color.account_valid));
        } else {
            holder.isValid.setChecked(false);
            holder.title.setTextColor(holder.getContext().getResources()
                    .getColor(R.color.account_invalid));
            holder.balance.setTextColor(holder.getContext().getResources()
                    .getColor(R.color.account_invalid));
            holder.expire.setTextColor(holder.getContext().getResources()
                    .getColor(R.color.account_invalid));
            holder.limit.setTextColor(holder.getContext().getResources()
                    .getColor(R.color.account_invalid));
        }
    }

    @Override
    public int getItemCount() {
        return DataFactory.getInstance().getAccounts().size();
    }

    public void updateData() {
        DataFactory.getInstance().updateAccountsFromDB();
    }
}
