package ng.com.tinweb.www.simone20.today;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;
import ng.com.tinweb.www.simone20.databinding.TodayCallListBinding;

/**
 * Created by kamiye on 02/09/2016.
 */
class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> {

    private TodayCallListBinding adapterBinding;
    private List<SimOneReminder> reminders;
    private CallActionListener callActionListener;

    TodayAdapter(List<SimOneReminder> reminders, CallActionListener callActionListener) {
        this.reminders = reminders;
        this.callActionListener = callActionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        adapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.today_call_list, parent, false);
        return new ViewHolder(adapterBinding.getRoot());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO add the dynamic data here
        Context context = adapterBinding.getRoot().getContext();

        SimOneReminder reminder = reminders.get(position);

        adapterBinding.contactNameTextView.setText(reminder.getContactName());
        int interval = reminder.getInterval();

        String dayString = context.getResources()
                .getQuantityString(R.plurals.days, interval, interval);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            adapterBinding.contactInfoTextView.setText(Html
                    .fromHtml(context.getString(R.string.status_badge, interval, dayString),
                            Html.FROM_HTML_MODE_COMPACT));
        } else {
            adapterBinding.contactInfoTextView.setText(Html
                    .fromHtml(context.getString(R.string.status_badge, interval, dayString)));
        }
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewHolder(View itemView) {
            super(itemView);
            adapterBinding.callIconImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            SimOneReminder reminder = reminders.get(position);

            String contactName = reminder.getContactName();

            List<String> phones = reminder.getPhones();

            callActionListener.onCallClick(contactName, phones.get(0));
        }
    }
}
