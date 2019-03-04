package com.example.homediy.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homediy.Fragments.Adapters.BluetoothListAdapter;
import com.example.homediy.Fragments.Interfaces.IFragmentWithName;
import com.example.homediy.Models.Device;
import com.example.homediy.Models.DeviceType;
import com.example.homediy.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class BluetoothListFragment extends Fragment implements IFragmentWithName
{
    private BluetoothListFragmentInteraction FragmentInteraction;
    private View mView;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothListAdapter mBluetoothListAdapter;

    public String getName()
    {
        return "BluetoothListFragment";
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof BluetoothListFragmentInteraction) {
            FragmentInteraction = (BluetoothListFragmentInteraction) context;
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
        mView = inflater.inflate(R.layout.fragment_bluetooth_device_list, container, false);

        mBluetoothAdapter = getBluetoothAdapter();
        mBluetoothListAdapter = new BluetoothListAdapter(FragmentInteraction);

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

        if(mBluetoothAdapter != null)
        {
            setPairedDevices();
            scanNewDevices();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if(mBluetoothAdapter != null){
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        FragmentInteraction = null;
        mBluetoothAdapter = null;
    }

    private void setStatus(String message)
    {
        TextView mTextView = mView.findViewById(R.id.bluetoothStatus);
        mTextView.setText(message);
        mTextView.setVisibility(View.VISIBLE);
    }

    private ArrayList<Device> setPairedDevices()
    {
        mBluetoothListAdapter.clear();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0)
        {
            ArrayList<Device> devices = new ArrayList<>();

            for (BluetoothDevice bluetoothDevice : pairedDevices)
            {
                Device device = new Device(0, bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.Bluetooth);
                mBluetoothListAdapter.addDevice(device);
            }
            return  devices;
        }
        return null;
    }

    private void scanNewDevices()
    {
        mBluetoothAdapter.startDiscovery();

        setStatus("Scanning");

        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                //Finding devices
                if (BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Device device = new Device(0, bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.Bluetooth);
                    mBluetoothListAdapter.addDevice(device);
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        Objects.requireNonNull(getActivity()).registerReceiver(mReceiver, filter);
    }

    private BluetoothAdapter getBluetoothAdapter()
    {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            setStatus("Device does not support Bluetooth");
            return null;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }
        return mBluetoothAdapter;
    }

    public interface BluetoothListFragmentInteraction
    {
        void onBluetoothDeviceInteraction(Device device);
    }
}
