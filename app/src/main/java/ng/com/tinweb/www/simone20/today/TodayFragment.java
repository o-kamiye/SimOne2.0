package ng.com.tinweb.www.simone20.today;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import ng.com.tinweb.www.simone20.R;
import ng.com.tinweb.www.simone20.SimOne;
import ng.com.tinweb.www.simone20.data.reminder.SimOneReminder;
import ng.com.tinweb.www.simone20.databinding.FragmentTodayBinding;
import ng.com.tinweb.www.simone20.util.LinearLayoutDecorator;

public class TodayFragment extends Fragment implements TodayContract.View,
        CallActionListener {

    private static final int CALL_PERMISSION_CODE = 27;

    private FragmentTodayBinding fragmentBinding;
    private String contactName;
    private String phoneNumber;

    @Inject
    TodayPresenter todayPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimOne.get(getActivity().getApplication())
                .getAppComponent()
                .subComponent(new TodayModule(this))
                .inject(this);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_today,
                container, false);
        setUpTodayFragment();
        setupRecyclerView();
        return fragmentBinding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CALL_PERMISSION_CODE
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            callContact(contactName, phoneNumber);

        }

    }

    @Override
    public void onRemindersLoaded(List<SimOneReminder> reminders) {
        // Set total simOneReminders for the day
        fragmentBinding.todayCallsTextView.setText(getResources()
                .getQuantityString(R.plurals.no_of_calls_today,
                        reminders.size(), reminders.size()));
        fragmentBinding.todayCallsRecyclerView.setAdapter(new TodayAdapter(reminders, this));
    }

    @Override
    public void onReminderLoadingError(String message) {
        // TODO implement error message showing here. Don't forget
    }

    @Override
    public void onCallClick(String contactName, String phoneNumber) {
        callContact(contactName, phoneNumber);
    }

    private void setUpTodayFragment() {
        todayPresenter.loadReminders();
    }

    private void setupRecyclerView() {

        Context context = fragmentBinding.getRoot().getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        LinearLayoutDecorator layoutDecorator = new LinearLayoutDecorator(context, null);

        fragmentBinding.todayCallsRecyclerView.setLayoutManager(layoutManager);

        fragmentBinding.todayCallsRecyclerView.addItemDecoration(layoutDecorator);
    }

    private void callContact(String contactName, String phoneNumber) {

        Uri number = Uri.parse("tel:" + phoneNumber);

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CALL_PHONE)) {

                Toast.makeText(getContext(), "Permission needed to make phone call",
                        Toast.LENGTH_LONG).show();

                requestCallPermission();

            } else {

                requestCallPermission();
            }
            this.contactName = contactName;
            this.phoneNumber = phoneNumber;
        }
        else {

            getActivity().startActivity(new Intent(Intent.ACTION_CALL, number));

            Toast.makeText(getContext(), "Calling " + contactName, Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void requestCallPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CALL_PHONE},
                CALL_PERMISSION_CODE);
    }

}
