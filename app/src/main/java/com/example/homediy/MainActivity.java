package com.example.homediy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.homediy.Fragments.Interfaces.IFragmentWithName;
import com.example.homediy.Fragments.MyDeviceListFragment;
import com.example.homediy.Fragments.BluetoothListFragment;
import com.example.homediy.Models.Device;
import com.example.homediy.Models.DeviceType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements
        MyDeviceListFragment.OnMyDeviceListFragmentInteraction,
        BluetoothListFragment.BluetoothListFragmentInteraction
{
    private IFragmentWithName CurrentFragment;
    private FrameLayout RootElement;

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
        CurrentFragment = (IFragmentWithName) fragment;
    }

    public void onMyDeviceInteraction(Device device)
    {

    }

    public ArrayList<Device> getMyDeviceList()
    {
        ArrayList<Device> devices = new ArrayList<>();
        devices.add(new Device(1, "Name", "Detail", DeviceType.Bluetooth));
        return devices;
    }

    public void onAddMyDeviceClick(){
        setFragment(new BluetoothListFragment());
    }

    public void onBluetoothDeviceInteraction(Device device)
    {

    }


}
