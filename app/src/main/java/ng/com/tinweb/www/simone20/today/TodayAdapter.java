package ng.com.tinweb.www.simone20.today;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.TodayCallListBinding;

/**
 * Created by kamiye on 02/09/2016.
 */
public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayViewHolder> {

    private TodayCallListBinding callListBinding;
    private String[] array = new String[2];
    private TodayFragment.CallActionListener callActionListener;

    public TodayAdapter(TodayFragment.CallActionListener callActionListener) {
        this.callActionListener = callActionListener;
    }

    @Override
    public TodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        callListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.today_call_list, parent, false);
        return new TodayViewHolder(callListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(TodayViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            callListBinding.contactInfoTextView.setText(Html.fromHtml("<big>4</big>" +  "<br />" +
                    "<small>days</small>", Html.FROM_HTML_MODE_COMPACT));
        }
        else {
            callListBinding.contactInfoTextView.setText(Html.fromHtml("<big>4</big>" +  "<br />" +
                    "<small>days</small>"));
        }
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TodayViewHolder(View itemView) {
            super(itemView);
            callListBinding.callIconImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String contactName = "Kamiye at postion " + position;
            callActionListener.onCallClick(contactName);
        }
    }
}
