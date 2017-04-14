package ng.com.tinweb.www.simone20.reminder;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;
import ng.com.tinweb.www.simone20.databinding.RemindersListBinding;

/**
 * Created by kamiye on 08/09/2016.
 */
class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private WeakReference<ReminderActionsListener> reminderActionsListener;
    private List<SimOneReminder> simOneReminders;
    private RemindersListBinding remindersBinding;
    private boolean isThisWeek = true;

    ReminderAdapter(List<SimOneReminder> simOneReminders,
                    ReminderActionsListener reminderActionsListener) {
        this.simOneReminders = simOneReminders;
        this.reminderActionsListener = new WeakReference<>(reminderActionsListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        remindersBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.reminders_list, parent, false);

        return new ViewHolder(remindersBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SimOneReminder simOneReminder = simOneReminders.get(position);
        remindersBinding.contactNameTextView.setText(simOneReminder.getContactName());
        int daysLeft = simOneReminder.getDaysLeft();
        int interval = simOneReminder.getInterval();
        int progress = (interval - daysLeft) * 100 / interval;
        if (daysLeft > 6 && isThisWeek) {
            remindersBinding.fartherRemindersTextView.setVisibility(View.VISIBLE);
            isThisWeek = !isThisWeek;
        }

        remindersBinding.circularProgressBar.setProgressWithAnimation(progress, 5000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            remindersBinding.circularInfoTextView.setText(Html.fromHtml("<big>" + daysLeft + "</big>" + "<br />" +
                    "<small>days</small>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            remindersBinding.circularInfoTextView.setText(Html.fromHtml("<big>" + daysLeft + "</big>" + "<br />" +
                    "<small>days</small>"));
        }
    }

    @Override
    public int getItemCount() {
        return simOneReminders.size();
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
            SimOneReminder simOneReminder = simOneReminders.get(position);
            if (view.getId() == remindersBinding.editIconImageView.getId()) {
                if (reminderActionsListener.get() != null) {
                    reminderActionsListener.get().onEditAction(simOneReminder);
                }
            }
            if (view.getId() == remindersBinding.deleteIconImageView.getId()) {
                if (reminderActionsListener.get() != null) {
                    reminderActionsListener.get().onDeleteAction(simOneReminder);
                }
            }
        }
    }
}
