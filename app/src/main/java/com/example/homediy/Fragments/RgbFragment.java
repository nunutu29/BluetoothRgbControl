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

import com.example.homediy.BluetoothThread;
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

    private BluetoothThread mBluetoothThread;

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

            if(mDevice != null)
            {
                mBluetoothThread = new BluetoothThread(mDevice,
                    new Handler(){
                        @Override
                        public void handleMessage(Message message) {
                            String msg = (String) message.obj;
                            //Logger
                            TextView textView = view.findViewById(R.id.rgbStatus);
                            textView.setText(msg);
                        }
                    },
                    new Handler() {
                        @Override
                        public void handleMessage(Message message) {
                            String msg = (String) message.obj;

                        }
                });
            }

            mBluetoothThread.start();

            colorPickerView.addOnColorChangedListener(new OnColorChangedListener()
            {
                @Override
                public void onColorChanged(int intColor)
                {

                String rgbMessage = RgbMessage(new Rgb(intColor));
                try
                {
                    Message mess = new Message();
                    mess.obj = rgbMessage;

                    mBluetoothThread.getWriteHandler().handleMessage(mess);
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
        //BluetoothConnect();
        super.onResume();
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mFragmentInteraction = null;

        //BluetoothDisconnect();
    }

    public interface OnRgbFragmentInteraction
    {
        BluetoothAdapter getBluetoothAdapter();
    }
}
