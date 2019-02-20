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

import com.example.homediy.Fragments.DeviceListFragment;
import com.example.homediy.Fragments.AddDeviceListFragment;
import com.example.homediy.Models.Device;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements
        DeviceListFragment.OnDeviceListFragmentInteraction,
        AddDeviceListFragment.OnDeviceListFragmentInteraction
{
    private static DeviceListFragment DefaultFragment = DeviceListFragment.Instance(true);
    private FrameLayout RootElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RootElement = findViewById(R.id.frame_container);

        setFragment(DefaultFragment);
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

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(RootElement.getId(), fragment);
        fragmentTransaction.commit();
    }

    public void onListFragmentInteraction(String Tag, Device device){
        //TODO identificare in qualche modo quale fragment ha chiamato questa callback
        // cambiare nome metodo ?
        // aggiungere un altro input ?
        // aggiungere un tipo di ritorno ?
    }

    public ArrayList<Device> onNeedList(){
        return new ArrayList<Device>();
    }

    public void onAddDeviceClick(){
        setFragment(new AddDeviceListFragment());
    }

}
