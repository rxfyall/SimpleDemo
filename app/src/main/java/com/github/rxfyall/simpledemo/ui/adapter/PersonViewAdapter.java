package com.github.rxfyall.simpledemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.rxfyall.simpledemo.R;
import com.github.rxfyall.simpledemo.data.Person;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rcfgnu on 5/17/16.
 */
public class PersonViewAdapter extends RecyclerView.Adapter<PersonViewAdapter.ViewHolder> {

    private List<Person> mPersons;

    private int mRowLayout;
    private Context context;

    public PersonViewAdapter(List<Person> mPersons, int mRowLayout) {
        this.mPersons = mPersons;
        this.mRowLayout = mRowLayout;
    }

    public void addPersons(List<Person> persons) {
        mPersons.clear();
        mPersons.addAll(persons);
        notifyDataSetChanged();
    }

    public void addPerson(int position, Person person) {
        if (position < 0) {
            position = 0;
        }
        mPersons.add(position, person);
        notifyItemInserted(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);
        context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Person person = mPersons.get(position);
        holder.firstName.setText(person.getFirstName());
        holder.lastName.setText(person.getLastName());
        Glide.with(context)
                .load(person.getAvatar())
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return mPersons == null ? 0 : mPersons.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.first_name)
        TextView firstName;
        @BindView(R.id.last_name)
        TextView lastName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
