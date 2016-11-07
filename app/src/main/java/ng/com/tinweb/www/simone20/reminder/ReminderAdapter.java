package ng.com.tinweb.www.simone20.reminder;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.reminder.Reminder;
import ng.com.tinweb.www.simone20.databinding.RemindersListBinding;

/**
 * Created by kamiye on 08/09/2016.
 */
class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private ReminderActionsListener reminderActionsListener;
    private List<Reminder> reminders;
    private RemindersListBinding remindersBinding;

    ReminderAdapter(List<Reminder> reminders, ReminderActionsListener reminderActionsListener) {
        this.reminders = reminders;
        this.reminderActionsListener = reminderActionsListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        remindersBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.reminders_list, parent, false);

        return new ViewHolder(remindersBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Reminder reminder = reminders.get(position);
        remindersBinding.contactNameTextView.setText(reminder.getContactName());
        int daysLeft = reminder.getDaysLeft();
        int interval = reminder.getInterval();
        int progress = (interval - daysLeft) * 100 / interval;
        // TODO getting this position will be dynamic
        if (position == 3) {
            remindersBinding.fartherRemindersTextView.setVisibility(View.VISIBLE);
        }

        // TODO add dynamic view addition here
         remindersBinding.circularProgressBar.setProgressWithAnimation(progress, 5000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            remindersBinding.circularInfoTextView.setText(Html.fromHtml("<big>"+ daysLeft +"</big>" +  "<br />" +
                    "<small>days</small>", Html.FROM_HTML_MODE_COMPACT));
        }
        else {
            remindersBinding.circularInfoTextView.setText(Html.fromHtml("<big>"+ daysLeft +"</big>" +  "<br />" +
                    "<small>days</small>"));
        }
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewHolder(View itemView) {
            super(itemView);
            remindersBinding.editIconImageView.setOnClickListener(this);
            remindersBinding.deleteIconImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Reminder reminder = reminders.get(position);
            if (view.getId() == remindersBinding.editIconImageView.getId()) {
                reminderActionsListener.onEditAction(reminder);
            }
            if (view.getId() == remindersBinding.deleteIconImageView.getId()) {
                reminderActionsListener.onDeleteAction(reminder);
            }
        }
    }
}
