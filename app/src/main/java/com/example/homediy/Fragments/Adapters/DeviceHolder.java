package com.example.homediy.Fragments.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.homediy.Models.Device;
import com.example.homediy.R;

public class DeviceHolder extends RecyclerView.ViewHolder
{
    Device device;

    final View mView;
    final TextView mNameView;
    final TextView mDetailView;

    public DeviceHolder(View view) {
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
