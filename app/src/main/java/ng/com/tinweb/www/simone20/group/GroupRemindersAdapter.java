package ng.com.tinweb.www.simone20.group;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;
import ng.com.tinweb.www.simone20.databinding.GroupRemindersListBinding;

/**
 * GroupRemindersAdapter -
 */
class GroupRemindersAdapter extends RecyclerView.Adapter<GroupRemindersAdapter.ViewHolder> {

    private List<SimOneReminder> reminders;
    private GroupRemindersListBinding adapterBinding;
    private GroupRemindersActionListener actionListener;

    GroupRemindersAdapter(List<SimOneReminder> reminders,
                       GroupRemindersActionListener actionListener) {
        this.reminders = reminders;
        this.actionListener = actionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        adapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.group_reminders_list, parent, false);

        return new ViewHolder(adapterBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = reminders.get(position).getContactName();

        adapterBinding.contactNameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            adapterBinding.deleteIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    SimOneReminder reminder = reminders.get(position);
                    actionListener.onClickDelete(reminder);
                }
            });
        }

    }
}
