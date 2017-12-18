package ca.qc.cstj.konquest.Activity

import android.app.PendingIntent.getActivity
import android.content.Context
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
import ca.qc.cstj.konquest.helpers.EXPLORATEUR_URL
import ca.qc.cstj.konquest.helpers.TOKEN
import ca.qc.cstj.konquest.models.Explorateur
import ca.qc.cstj.konquest.models.Unite
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), UniteListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        Toast.makeText(this,"Connecté.",Toast.LENGTH_SHORT).show()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rafraichirDataMain()


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
                deconnexion()
            }
            R.id.action_scanner-> {
                rafraichirDataMain()
                IntentIntegrator(this).initiateScan() // `this` is the current Activity
                rafraichirDataMain()
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

    fun rafraichirDataMain() {

        // On obtient le token.
        val preferences : SharedPreferences? = this.getSharedPreferences(TOKEN, 0)
        var token = preferences?.getString(TOKEN,null)

        // On prepare le token pour envoie.
        var Authorization = "bearer " + token


        // On effectue la requête.
        EXPLORATEUR_URL.httpGet()
        .header("Authorization" to Authorization)
        .responseJson { _, response, result ->
            when(response.statusCode) {
                200 -> {

                    val explorateur = Explorateur(result.get())

                    textViewMainPseudonyme.text = explorateur.pseudonyme
                    textViewMainInox.text = explorateur.inox.toString()
                    textViewMainLocation.text = explorateur.location
                }
            }
        }
    }

    fun deconnexion() {
        Toast.makeText(this,"Déconnexion completé.",Toast.LENGTH_SHORT).show()
        val preferences : SharedPreferences? = this.getSharedPreferences(TOKEN, 0)
        var editor : SharedPreferences.Editor? = preferences?.edit()
        editor?.putString(TOKEN,"")
        editor?.commit()
        val intent = Intent(this,ConnexionActivity::class.java)
        startActivity(intent)
    }
}
