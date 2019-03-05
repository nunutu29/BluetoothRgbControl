package com.example.homediy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homediy.Fragments.Adapters.MyDeviceListAdapter;
import com.example.homediy.Fragments.Interfaces.IFragmentWithName;
import com.example.homediy.Models.Device;
import com.example.homediy.R;

import java.util.ArrayList;

public class MyDeviceListFragment extends Fragment implements IFragmentWithName
{
    private OnMyDeviceListFragmentInteraction FragmentInteraction;

    public String getName()
    {
        return "MyDeviceListFragment";
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my_device_list, container, false);

        FloatingActionButton fab = view.findViewById(R.id.addDevice);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentInteraction.onAddMyDeviceClick();
            }
        });

        if (view instanceof CoordinatorLayout)
        {
            Context context = view.getContext();
            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMyDeviceList);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyDeviceListAdapter(FragmentInteraction));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyDeviceListFragmentInteraction) {
            FragmentInteraction = (OnMyDeviceListFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnMyDeviceListFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FragmentInteraction = null;
    }

    public interface OnMyDeviceListFragmentInteraction
    {
        void onMyDeviceInteraction(Device device);
        ArrayList<Device> getMyDeviceList();
        void onAddMyDeviceClick();
    }
}
