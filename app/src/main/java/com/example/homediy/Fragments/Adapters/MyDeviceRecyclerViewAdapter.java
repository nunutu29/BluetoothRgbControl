package com.example.homediy.Fragments.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homediy.Fragments.DeviceListFragment.OnDeviceListFragmentInteractionListener;
import com.example.homediy.Models.Device;
import com.example.homediy.R;

import java.util.List;

public class MyDeviceRecyclerViewAdapter extends RecyclerView.Adapter<MyDeviceRecyclerViewAdapter.ViewHolder> {

    private final List<Device> mValues;
    private final OnDeviceListFragmentInteractionListener mListener;

    public MyDeviceRecyclerViewAdapter(List<Device> items, OnDeviceListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_device, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(holder.mItem.Name);
        holder.mDetailView.setText(holder.mItem.Details);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onDeviceListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mDetailView;
        public Device mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.name);
            mDetailView = view.findViewById(R.id.details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDetailView.getText() + "'";
        }
    }
}
