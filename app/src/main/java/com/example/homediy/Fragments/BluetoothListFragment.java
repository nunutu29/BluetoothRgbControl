package com.example.homediy.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.homediy.Fragments.Adapters.BluetoothListAdapter;
import com.example.homediy.Fragments.Interfaces.IFragmentWithName;
import com.example.homediy.Models.Device;
import com.example.homediy.Models.DeviceType;
import com.example.homediy.R;

import java.util.Set;

public class BluetoothListFragment extends Fragment implements IFragmentWithName
{
    private BluetoothListFragmentInteraction mFragmentInteraction;
    private BluetoothListAdapter mBluetoothListAdapter;

    public String getName()
    {
        return BluetoothListFragment.class.getSimpleName();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof BluetoothListFragmentInteraction) {
            mFragmentInteraction = (BluetoothListFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BluetoothListFragmentInteraction");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mView = inflater.inflate(R.layout.fragment_bluetooth_device_list, container, false);

        mBluetoothListAdapter = new BluetoothListAdapter(mFragmentInteraction);

        if (mView instanceof LinearLayout)
        {
            RecyclerView recyclerView = mView.findViewById(R.id.recyclerViewBluetoothList);
            recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            recyclerView.setAdapter(mBluetoothListAdapter);
        }

        return mView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(mFragmentInteraction.getBluetoothAdapter() != null)
        {
            setPairedDevices();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mFragmentInteraction = null;
    }

    private void setPairedDevices()
    {
        mBluetoothListAdapter.clear();
        Set<BluetoothDevice> pairedDevices = mFragmentInteraction.getBluetoothAdapter().getBondedDevices();

        if (pairedDevices.size() > 0)
        {
            for (BluetoothDevice bluetoothDevice : pairedDevices)
            {
                Device device = new Device(bluetoothDevice.getAddress(), bluetoothDevice.getName(),
                        bluetoothDevice.getAddress(), DeviceType.Bluetooth);

                mBluetoothListAdapter.addDevice(device);
            }
        }
    }

    public interface BluetoothListFragmentInteraction
    {
        void onBluetoothDeviceInteraction(Device device);
        BluetoothAdapter getBluetoothAdapter();
    }
}
