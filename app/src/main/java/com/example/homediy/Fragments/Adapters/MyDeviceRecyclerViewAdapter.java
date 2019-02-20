package com.example.homediy.Fragments.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homediy.Fragments.Interfaces.ListFragmentInteraction;
import com.example.homediy.Models.Device;
import com.example.homediy.R;

import java.util.ArrayList;

public class MyDeviceRecyclerViewAdapter
        extends RecyclerView.Adapter<MyDeviceRecyclerViewAdapter.ViewHolder>
{
    private final ArrayList<Device> Devices;
    private final ListFragmentInteraction<Device> FragmentInteraction;
    private final String Tag;

    public MyDeviceRecyclerViewAdapter(String tag, @NonNull ListFragmentInteraction<Device> fragmentInteraction ) {
        FragmentInteraction = fragmentInteraction;
        Tag = tag;
        Devices = FragmentInteraction.onNeedList();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.device = Devices.get(position);
        holder.mNameView.setText(holder.device.Name);
        holder.mDetailView.setText(holder.device.Details);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentInteraction.onListFragmentInteraction(Tag, holder.device);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Device device;
        final View mView;
        final TextView mNameView;
        final TextView mDetailView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
            mDetailView = view.findViewById(R.id.details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
