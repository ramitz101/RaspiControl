package ca.qc.cstj.konquest.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.fragments.UniteDetailsFragment
import ca.qc.cstj.konquest.fragments.UniteListFragment
import ca.qc.cstj.konquest.helpers.TOKEN_INFORMATION
import ca.qc.cstj.konquest.models.Unite
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), UniteListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        Toast.makeText(this,"Connecté.",Toast.LENGTH_SHORT).show()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_left_view.setNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                //R.id.nav_camera -> {
                  //  Toast.makeText(this,"Camera",Toast.LENGTH_LONG).show()
                //}

                R.id.nav_lstUnites -> {
                    Runnable {
                        val transaction = fragmentManager.beginTransaction()
                        //transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        transaction.replace(R.id.contentFrame, UniteListFragment.newInstance(1))
                        //transaction.addToBackStack("ListeSuccursale")
                        transaction.commit()
                    }.run()
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

            R.id.action_deconnexion -> {
                Toast.makeText(this,"Déconnexion completé.",Toast.LENGTH_SHORT).show()
                val preferences_Token_Info : SharedPreferences? = this.getSharedPreferences(TOKEN_INFORMATION, 0)
                var editor_Token_Info : SharedPreferences.Editor? = preferences_Token_Info?.edit()
                editor_Token_Info?.putBoolean(TOKEN_INFORMATION,false)
                editor_Token_Info?.commit()
                val intent = Intent(this@MainActivity,ConnexionActivity::class.java)
                startActivity(intent)
            }
            R.id.action_scanner-> {

                IntentIntegrator(this).initiateScan(); // `this` is the current Activity
            }

            else -> return super.onOptionsItemSelected(item)

        }

        return true
    }

    fun updateMenuConnexionTitle(item: MenuItem) {

    }

    override fun onListFragmentInteraction(unite: Unite?) {
        //nav_left_view.setNavigationItemSelectedListener(this)
        Runnable {
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.contentFrame, UniteDetailsFragment(unite!!.href))
            //transaction.addToBackStack("DetailsLivre")
            transaction.commit()
        }.run()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
