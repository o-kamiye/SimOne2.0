package ng.com.tinweb.www.simone20.today;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
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
    private String[] array = new String[]{"Someone", "Another one"};

    @Override
    public TodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        callListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.today_call_list, parent, false);
        return new TodayViewHolder(callListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(TodayViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder {

        public TodayViewHolder(View itemView) {
            super(itemView);
        }

    }
}
