package com.example.homediy.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.homediy.Fragments.Interfaces.IFragmentWithName;
import com.example.homediy.Models.Device;
import com.example.homediy.Models.Rgb;
import com.example.homediy.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class RgbFragment extends Fragment implements IFragmentWithName
{
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private static final String ARG_DEVICE = "ARG_DEVICE";
    private Device mDevice;

    private OnRgbFragmentInteraction mFragmentInteraction;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    public String getName()
    {
        return "RgbFragment";
    }

    private static final String OnMessage()
    {
        return "X";
    }

    private static final String OffMessage()
    {
        return "Y";
    }

    private static final String RgbMessage(Rgb rgb)
    {
        return rgb.Format("Z {R},{G},{B};");
    }

    public BluetoothDevice getBluetoothDevice()
    {
        if(mBluetoothDevice != null)
            return mBluetoothDevice;

        if (mFragmentInteraction.getBluetoothAdapter() == null)
            return null;

        Set<BluetoothDevice> pairedDevices = mFragmentInteraction.getBluetoothAdapter().getBondedDevices();
        for(BluetoothDevice bluetoothDevice : pairedDevices)
        {
            if(bluetoothDevice.getAddress().equals(mDevice.Key))
            {
                mBluetoothDevice = bluetoothDevice;
                return mBluetoothDevice;
            }
        }

        Toast.makeText(getContext(), String.format("Could not find %s", mDevice.Name), Toast.LENGTH_LONG).show();
        return null;
    }

    public void BluetoothConnect()
    {
        if(getBluetoothDevice() == null)
            return;

        if(mBluetoothSocket != null && mBluetoothSocket.isConnected())
            return;

        try
        {
            mBluetoothSocket = getBluetoothDevice().createInsecureRfcommSocketToServiceRecord(PORT_UUID);
            mBluetoothSocket.connect();

            mOutputStream = mBluetoothSocket.getOutputStream();
            mInputStream = mBluetoothSocket.getInputStream();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void BluetoothDisconnect()
    {
        try
        {
            mBluetoothDevice = null;

            mBluetoothSocket.close();
            mBluetoothSocket = null;

            mOutputStream.close();
            mInputStream.close();

            mOutputStream = null;
            mInputStream = null;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static RgbFragment newInstance(Device device)
    {
        RgbFragment fragment = new RgbFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DEVICE, device);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof OnRgbFragmentInteraction)
        {
            mFragmentInteraction = (OnRgbFragmentInteraction) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnRgbFragmentInteraction");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDevice = (Device)getArguments().getSerializable(ARG_DEVICE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_rgb, container, false);

        if(view instanceof FrameLayout)
        {
            ColorPickerView colorPickerView = view.findViewById(R.id.color_picker_view);
            colorPickerView.addOnColorChangedListener(new OnColorChangedListener()
            {
                @Override
                public void onColorChanged(int intColor)
                {

                String rgbMessage = RgbMessage(new Rgb(intColor));
                try
                {
                    mOutputStream.write(rgbMessage.getBytes());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                }
            });

        }
        return view;
    }

    @Override
    public void onResume()
    {
        BluetoothConnect();
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteraction = null;
        BluetoothDisconnect();
    }

    public interface OnRgbFragmentInteraction
    {
        BluetoothAdapter getBluetoothAdapter();
    }
}
