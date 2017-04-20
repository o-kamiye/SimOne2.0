package ng.com.tinweb.www.simone20.contact;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.data.contact.SimOneContact;
import ng.com.tinweb.www.simone20.databinding.ContactListBinding;

/**
 * ContactListAdapter
 */

class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<SimOneContact> contacts;
    private ContactListBinding adapterBinding;
    private ContactActionListener actionListener;

    ContactListAdapter(List<SimOneContact> contacts,
                              ContactActionListener actionListener) {
        this.contacts = contacts;
        this.actionListener = actionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        adapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.contact_list, parent, false);

        return new ViewHolder(adapterBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO save the first letter here
        String name = contacts.get(position).getName();

        adapterBinding.contactNameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            adapterBinding.addIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    SimOneContact contact = contacts.get(position);
                    actionListener.onClickAdd(contact);
                }
            });
        }

    }

}
