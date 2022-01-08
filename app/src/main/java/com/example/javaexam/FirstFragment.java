package com.example.javaexam;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.example.javaexam.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class FirstFragment extends Fragment {
    private static final String TAG = "myLog";

    private FragmentFirstBinding binding;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ContactViewModel model;
    private int selectedItemId = -1;
    private ContactAdapter contactAdapter;
    ContactDao contactDao;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        model = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactDao = SingletonDb.getDbInstance(getContext()).db.contactDao();
        SetContactsFromDatabase();
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void SetContactsFromDatabase() {
        contacts.clear();
        model.getAllContact(requireContext()).observe(getViewLifecycleOwner(), it -> {
            Log.d(TAG, "SetContactsFromDatabase: " + it);
            contacts.addAll(it);
            contactAdapter.setList((ArrayList<Contact>) it);
            contactAdapter.notifyDataSetChanged();
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(view);
        requireActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);

        contactAdapter = new ContactAdapter(contacts, this, new IRecycleOnClickListener() {
            @Override
            public void onClick(View view, int position) {
                int permissionStatus = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE);

                if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + contacts.get(position).phone));
                    startActivity(callIntent);
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CALL_PHONE}, 1);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                view.showContextMenu();
                selectedItemId = position;
            }
        });

        binding.contactList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.contactList.setAdapter(contactAdapter);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.clear();
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.layout_context_menu, menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);

        switch (item.getItemId()) {
            case 2131230986:
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.addToBackStack("SecondFragment");
                ft.replace(R.id.fragment_place, new SecondFragment(contacts.get(selectedItemId))).commit();
                break;
            case 2131230987:
                model.deleteContact(contacts.get(selectedItemId), requireContext());
                break;
            default:
                Log.d(TAG, "onContextItemSelected: default.error");
                break;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}