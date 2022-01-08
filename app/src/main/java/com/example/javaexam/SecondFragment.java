package com.example.javaexam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.javaexam.databinding.FragmentSecondBinding;

import java.util.Objects;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private ContactViewModel model;
    private Contact contact = null;

    public SecondFragment() {
    }

    public SecondFragment(Contact contact) {
        this.contact = contact;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        model = ViewModelProviders.of(this).get(ContactViewModel.class);
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        if (contact != null) {
            binding.editTextPersonName.setText(contact.name);
            binding.editTextPersonSurname.setText(contact.surname);
            binding.editTextPersonPhone.setText(contact.phone);
        }
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        binding.buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contact newContact = new Contact(
                        binding.editTextPersonName.getText().toString(),
                        binding.editTextPersonSurname.getText().toString(),
                        binding.editTextPersonPhone.getText().toString());

                if (contact != null) {
                    newContact.id = contact.id;
                    model.updateContact(newContact, requireContext());
                } else {
                    model.insertContact(newContact, requireContext());
                }
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}