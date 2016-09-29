package ng.com.tinweb.www.simone20.group;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.databinding.GroupsListBinding;

/**
 * Created by kamiye on 11/09/2016.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private GroupsListBinding groupsListBinding;
    private GroupActionsListener actionsListener;
    private String[] array = new String[2];

    public GroupAdapter(GroupActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        groupsListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.groups_list, parent, false);

        return new ViewHolder(groupsListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            groupsListBinding.groupInfoTextView.setText(Html.fromHtml("<big>4</big>" +  "<br />" +
                    "<small>days</small>", Html.FROM_HTML_MODE_COMPACT));
        }
        else {
            groupsListBinding.groupInfoTextView.setText(Html.fromHtml("<big>4</big>" +  "<br />" +
                    "<small>days</small>"));
        }
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
            groupsListBinding.editIconImageView.setOnClickListener(this);
            groupsListBinding.deleteIconImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String groupId = "Position " + position;
            if (view.getId() == groupsListBinding.editIconImageView.getId()) {
                actionsListener.onEditAction(groupId);
            }
            if (view.getId() == groupsListBinding.deleteIconImageView.getId()) {
                actionsListener.onDeleteAction(groupId);
            }
        }
    }
}
