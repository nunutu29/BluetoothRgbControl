package com.example.homediy;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.homediy.Fragments.Interfaces.IFragmentWithName;
import com.example.homediy.Fragments.MyDeviceListFragment;
import com.example.homediy.Fragments.BluetoothListFragment;
import com.example.homediy.Fragments.RgbFragment;
import com.example.homediy.Models.Device;
import com.example.homediy.Models.DeviceType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements
        MyDeviceListFragment.OnMyDeviceListFragmentInteraction,
        BluetoothListFragment.BluetoothListFragmentInteraction,
        RgbFragment.OnRgbFragmentInteraction
{
    private IFragmentWithName CurrentFragment;
    private FrameLayout RootElement;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RootElement = findViewById(R.id.frame_container);

        setFragment(new MyDeviceListFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {
        if(mBluetoothAdapter != null)
        {
            mBluetoothAdapter.disable();
            mBluetoothAdapter = null;
        }
        super.onDestroy();
    }

    protected void setFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(CurrentFragment != null)
        {
            fragmentTransaction.addToBackStack(CurrentFragment.getName());
        }

        fragmentTransaction.replace(RootElement.getId(), fragment);
        fragmentTransaction.commit();

        //save this fragment as current
        if(fragment instanceof IFragmentWithName)
        {
            CurrentFragment = (IFragmentWithName) fragment;
        }
    }

    public void onMyDeviceInteraction(Device device)
    {

    }

    public ArrayList<Device> getMyDeviceList()
    {
        return new ArrayList<Device>();
    }

    public void onAddMyDeviceClick(){
        setFragment(new BluetoothListFragment());
    }

    public void onBluetoothDeviceInteraction(Device device)
    {
        RgbFragment rgbFragment = RgbFragment.newInstance(device);
        setFragment(rgbFragment);
    }

    public BluetoothAdapter getBluetoothAdapter()
    {
        if(mBluetoothAdapter != null)
            return mBluetoothAdapter;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            Snackbar.make(findViewById(R.id.frame_container), "Device does not support Bluetooth.", Snackbar.LENGTH_LONG).show();
            return null;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }
        return mBluetoothAdapter;
    }


}
