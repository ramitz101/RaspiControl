package ca.qc.cstj.konquest

import android.app.PendingIntent.getActivity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_left_view.setNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.nav_camera -> {
                    Toast.makeText(this,"Camera",Toast.LENGTH_LONG).show()
                }
                R.id.nav_gallery -> {

                }
                R.id.nav_slideshow -> {

                }
                R.id.nav_manage -> {

                }
                R.id.nav_share -> {

                }
                R.id.nav_send -> {

                }
            }

            drawer_layout.closeDrawer(GravityCompat.START)
            true

        }

        nav_right_view.setNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.nav_settings -> {

                }
                R.id.nav_logout -> {

                }
                R.id.nav_help -> {

                }
                R.id.nav_about -> {

                }

            }

            drawer_layout.closeDrawer(GravityCompat.START)
            true

        }


        nav_right_view.setNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.nav_help -> {
                    Toast.makeText(this,"Help",Toast.LENGTH_LONG).show()
                }
            }

            drawer_layout.closeDrawer(GravityCompat.START)
            true

        }

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if(drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END)
        }else{
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

            R.id.action_connexion_deconnexion -> {
                
            }
            R.id.action_openRight -> {
                drawer_layout.openDrawer(GravityCompat.END)
                return true
            }

            else -> return super.onOptionsItemSelected(item)

        }

        return true
    }

    fun updateMenuConnexionTitle(item: MenuItem) {

    }


}
