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
 * Created by kamiye on 27/09/2016.
 */

class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<SimOneContact> contactList;
    private ContactListBinding contactListBinding;
    private ContactActionListener contactActionListener;

    ContactListAdapter(List<SimOneContact> contactList,
                              ContactActionListener contactActionListener) {
        this.contactList = contactList;
        this.contactActionListener = contactActionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        contactListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.contact_list, parent, false);

        return new ViewHolder(contactListBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO save the first letter here
        String name = contactList.get(position).getName();

        contactListBinding.contactNameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            contactListBinding.addIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    SimOneContact contact = contactList.get(position);
                    contactActionListener.onClickAdd(contact);
                }
            });
        }

    }

}
