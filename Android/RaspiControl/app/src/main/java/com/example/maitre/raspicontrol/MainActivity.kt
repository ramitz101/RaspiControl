package com.example.maitre.raspicontrol

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.RequiresPermission
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.maitre.raspicontrol.R.id.*
import com.example.maitre.raspicontrol.fragments.AjustementFragments
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity(),AjustementFragments.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    var mmSocket: BluetoothSocket?=null;
    var mmDevice : BluetoothDevice? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var mBlueTooth : BluetoothAdapter  = BluetoothAdapter.getDefaultAdapter()

        if(mBlueTooth == null)
        {
            // Marche pas
        }

        if(!mBlueTooth.isEnabled())
        {
            var enableBtIntent : Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent,1)
        }

      var pairedDevices :   Set<BluetoothDevice> = mBlueTooth.bondedDevices


        if(pairedDevices.size > 0)
        {
            for(dev : BluetoothDevice in pairedDevices)
            {
                var deviceName : String = dev.name
                var deviceHardware : String  = dev.address
                if(deviceHardware == "B8:27:EB:FF:EC:00")
                    mmDevice = dev
            }

        }



        var tmp : BluetoothSocket? = null
        var MON_UUID : UUID = UUID.fromString("7be1fcb3-5776-42fb-91fd-2ee7b5bbb86d")

        Toast.makeText(this,mmDevice.toString(),Toast.LENGTH_LONG).show()
       // tmp = mmDevice!!.createRfcommSocketToServiceRecord(MON_UUID)

        //mmSocket = tmp
        Toast.makeText(this,tmp.toString(),Toast.LENGTH_LONG).show()

        //mmSocket!!.connect()












        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onFragmentInteraction(uri: Uri){

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_control -> {
                // Handle the camera action
            }
            R.id.nav_Ajustement -> {
                Runnable {
                    val transaction = fragmentManager.beginTransaction()
                    transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    transaction.replace(R.id.contentFrame, AjustementFragments.newInstance(mmSocket,mmDevice))
                    transaction.addToBackStack("AjustementFragment")
                    transaction.commit()
                }.run()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
