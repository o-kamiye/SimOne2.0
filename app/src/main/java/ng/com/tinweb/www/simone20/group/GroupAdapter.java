package ng.com.tinweb.www.simone20.group;

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
import ng.com.tinweb.www.simone20.data.group.SimOneGroup;
import ng.com.tinweb.www.simone20.databinding.GroupsListBinding;

/**
 * GroupAdapter - Adapter for managing groups created by user
 */
class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private GroupsListBinding adapterBinding;
    private GroupActionsListener actionsListener;
    private List<SimOneGroup> groups;

    GroupAdapter(List<SimOneGroup> groups, GroupActionsListener actionsListener) {
        this.groups = groups;
        this.actionsListener = actionsListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        adapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.groups_list, parent, false);

        return new ViewHolder(adapterBinding.getRoot());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Context context = adapterBinding.getRoot().getContext();

        SimOneGroup group = groups.get(position);

        adapterBinding.groupNameTextView.setText(group.getName());

        int interval = group.getInterval();

        String dayString = context.getResources()
                .getQuantityString(R.plurals.days, interval, interval);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            adapterBinding.groupInfoTextView.setText(Html
                    .fromHtml(context.getString(R.string.status_badge, interval, dayString),
                            Html.FROM_HTML_MODE_COMPACT));
        } else {
            adapterBinding.groupInfoTextView.setText(Html
                    .fromHtml(context.getString(R.string.status_badge, interval, dayString)));
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewHolder(View itemView) {
            super(itemView);
            adapterBinding.editIconImageView.setOnClickListener(this);
            adapterBinding.deleteIconImageView.setOnClickListener(this);
            adapterBinding.groupInfoLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int id = view.getId();

            int position = getAdapterPosition();

            SimOneGroup group = groups.get(position);

            if (id == adapterBinding.editIconImageView.getId()) {
                actionsListener.onEditAction(group);
            }
            if (id == adapterBinding.deleteIconImageView.getId()) {
                actionsListener.onDeleteAction(group.getName());
            }
            if (id == adapterBinding.groupInfoLayout.getId()) {
                actionsListener.onSelectInfo(group.getName());
            }
        }
    }
}
