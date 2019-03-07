package com.example.homediy.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
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
    private static final String ARG_DEVICE = "ARG_DEVICE";
    private Device mDevice;
    private TextView rgbStatus;
    private OnRgbFragmentInteraction mFragmentInteraction;

    private static final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    public String getName()
    {
        return RgbFragment.class.getSimpleName();
    }

    private static String OnMessage()
    {
        return "X";
    }

    private static String OffMessage()
    {
        return "Y";
    }

    private static String RgbMessage(Rgb rgb)
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
        Log("Connecting...");

        if(getBluetoothDevice() == null)
        {
            Log("Connection failed");
            return;
        }

        if(mBluetoothSocket != null && mBluetoothSocket.isConnected())
        {
            Log("Connected");
            return;
        }

        try
        {
            mBluetoothSocket = getBluetoothDevice().createInsecureRfcommSocketToServiceRecord(PORT_UUID);
            mBluetoothSocket.connect();

            mOutputStream = mBluetoothSocket.getOutputStream();
            mInputStream = mBluetoothSocket.getInputStream();

            Log("Connected");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            Log("Connection failed");
        }
    }

    public void BluetoothDisconnect()
    {
        try
        {
            mOutputStream.close();
            mInputStream.close();
            mBluetoothSocket.close();

            mBluetoothDevice = null;
            mBluetoothSocket = null;
            mOutputStream = null;
            mInputStream = null;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void Log(String mess)
    {
        if(rgbStatus != null)
        {
            rgbStatus.setText(mess);
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
        final View view = inflater.inflate(R.layout.fragment_rgb, container, false);

        if(view instanceof FrameLayout)
        {
            ColorPickerView colorPickerView = view.findViewById(R.id.color_picker_view);

            rgbStatus = view.findViewById(R.id.rgbStatus);

            colorPickerView.addOnColorChangedListener(new OnColorChangedListener()
            {
                @Override
                public void onColorChanged(int intColor)
                {

                String rgbMessage = RgbMessage(new Rgb(intColor));
                try
                {
                    if(mOutputStream != null)
                    {
                        mOutputStream.write(rgbMessage.getBytes());
                    }
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
        super.onResume();

        if(getActivity() != null)
        {
            Handler mainHandler = new Handler(getActivity().getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    BluetoothConnect();
                }
            };
            mainHandler.post(myRunnable);

        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mFragmentInteraction = null;
        BluetoothDisconnect();
    }

    public interface OnRgbFragmentInteraction
    {
        BluetoothAdapter getBluetoothAdapter();
    }
}
