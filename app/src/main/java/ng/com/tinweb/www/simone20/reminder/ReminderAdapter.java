package ng.com.tinweb.www.simone20.reminder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.RemindersListBinding;

/**
 * Created by kamiye on 08/09/2016.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

    private ReminderActionsListener reminderActionsListener;
    private String[] array = new String[2];
    private RemindersListBinding remindersBinding;

    public ReminderAdapter(ReminderActionsListener reminderActionsListener) {
        this.reminderActionsListener = reminderActionsListener;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        remindersBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.reminders_list, parent, false);
        return new ReminderViewHolder(remindersBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {
        // TODO add dynamic view addition here
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ReminderViewHolder(View itemView) {
            super(itemView);
            remindersBinding.editIconImageView.setOnClickListener(this);
            remindersBinding.deleteIconImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String contactId = "Position " + position;
            if (view.getId() == remindersBinding.editIconImageView.getId()) {
                reminderActionsListener.onEditAction(contactId);
            }
            if (view.getId() == remindersBinding.deleteIconImageView.getId()) {
                reminderActionsListener.onDeleteAction(contactId);
            }
        }
    }
}
