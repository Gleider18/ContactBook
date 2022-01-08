package com.example.javaexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{

    private ArrayList<Contact> contacts;
    private IRecycleOnClickListener listener;
    private FirstFragment firstFragment;

    public ContactAdapter(ArrayList<Contact> contacts, FirstFragment firstFragment, IRecycleOnClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
        this.firstFragment = firstFragment;
    }

    public void setList(ArrayList<Contact> it) {
        contacts = it;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView surname;
        private final TextView name;
        private final TextView phone;
        private IRecycleOnClickListener listener;

        public ContactViewHolder(View view, FirstFragment firstFragment, IRecycleOnClickListener listener) {
            super(view);

            surname = (TextView) view.findViewById(R.id.surname);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
            this.listener = listener;

            firstFragment.registerForContextMenu(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public TextView getSurnameTextView() {
            return surname;
        }

        public TextView getNameTextView() {
            return name;
        }

        public TextView getPhoneTextView() {
            return phone;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onClick(view, position);

        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            listener.onLongClick(view, position);
            return true;
        }
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_view, viewGroup, false);
        return new ContactViewHolder(view, firstFragment, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder viewHolder, int position) {
        Contact contact = contacts.get(position);
        viewHolder.getNameTextView().setText(contact.name);
        viewHolder.getSurnameTextView().setText(contact.surname);
        viewHolder.getPhoneTextView().setText(contact.phone);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

}
