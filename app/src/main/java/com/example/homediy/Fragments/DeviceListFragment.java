package com.example.homediy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homediy.Fragments.Adapters.MyDeviceRecyclerViewAdapter;
import com.example.homediy.Fragments.Interfaces.ListFragmentInteraction;
import com.example.homediy.Models.Device;
import com.example.homediy.R;

import java.util.ArrayList;

public class DeviceListFragment extends Fragment {

    private OnDeviceListFragmentInteraction fragmentInteraction;
    private boolean VisibleAddDevice;

    public DeviceListFragment() {

    }

    public static DeviceListFragment Instance(boolean visibleAddDevice) {
        DeviceListFragment fragment = new DeviceListFragment();
        fragment.VisibleAddDevice = visibleAddDevice;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);

        if(VisibleAddDevice){
            FloatingActionButton fab = view.findViewById(R.id.addDevice);
            fab.setVisibility(View.VISIBLE);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentInteraction.onAddDeviceClick();
                }
            });
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyDeviceRecyclerViewAdapter("Y", fragmentInteraction));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDeviceListFragmentInteraction) {
            fragmentInteraction = (OnDeviceListFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDeviceListFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteraction = null;
    }

    public interface OnDeviceListFragmentInteraction extends ListFragmentInteraction<Device>
    {
        void onListFragmentInteraction(String Tag, Device device);
        ArrayList<Device> onNeedList();
        void onAddDeviceClick();
    }
}
