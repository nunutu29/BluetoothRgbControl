package com.example.homediy.Fragments.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homediy.Fragments.MyDeviceListFragment.OnMyDeviceListFragmentInteraction;
import com.example.homediy.Models.Device;
import com.example.homediy.R;

import java.util.ArrayList;

public class MyDeviceListAdapter extends RecyclerView.Adapter<DeviceHolder>
{
    private final ArrayList<Device> Devices;
    private final OnMyDeviceListFragmentInteraction FragmentInteraction;

    public MyDeviceListAdapter(@NonNull OnMyDeviceListFragmentInteraction fragmentInteraction)
    {
        FragmentInteraction = fragmentInteraction;
        Devices = FragmentInteraction.getMyDeviceList();
    }

    @Override
    @NonNull
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new DeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceHolder holder, int position)
    {
        holder.device = Devices.get(position);
        holder.mNameView.setText(holder.device.Name);
        holder.mDetailView.setText(holder.device.Details);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentInteraction.onMyDeviceInteraction(holder.device);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return Devices.size();
    }
}
