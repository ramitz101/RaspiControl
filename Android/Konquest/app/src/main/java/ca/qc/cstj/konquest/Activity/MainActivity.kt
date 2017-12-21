package ca.qc.cstj.konquest.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.fragments.AccueilFragment
import ca.qc.cstj.konquest.fragments.RunesFragment
//import ca.qc.cstj.konquest.fragments.ExplorationDetailsFragment
import ca.qc.cstj.konquest.fragments.UniteDetailsFragment
import ca.qc.cstj.konquest.fragments.UniteListFragment
import ca.qc.cstj.konquest.fragments.*
import ca.qc.cstj.konquest.helpers.*
import ca.qc.cstj.konquest.models.Runes
import ca.qc.cstj.konquest.models.Explorateur
import ca.qc.cstj.konquest.models.Exploration
import ca.qc.cstj.konquest.models.Unite
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(),
        UniteListFragment.OnListFragmentInteractionListener,
        AccueilFragment.OnFragmentInteractionListener,
        ExplorationListFragment.OnListFragmentInteractionListener
        AccueilFragment.OnFragmentInteractionListener,
        RunesFragment.OnFragmentInteractionListener/*, ExplorationDetailsFragment.OnClickListener*/
{
    override fun onListFragmentInteraction(item: Exploration) {
        // Fragment Exploration.
    }

    override fun onFragmentInteraction(uri: Uri) {
        // Fragment Accueil.
    }
    /*override fun OnClickListener(item: Exploration) {
        // Fragment Accueil.
    }
*/

    /*override fun onFragmentInteraction(uri: Uri) {
        // Pour le fragment_exploration_details Runes.
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/


    // Ressources : https://stackoverflow.com/questions/11434056/how-to-run-a-method-every-x-seconds


    var authorization : String = ""
    var explorateur : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        //savedInstanceState.
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        afficherRunes()

        // On obtient l'explorateur.
        val preferences : SharedPreferences = this.getSharedPreferences(EXPLORATEUR, 0)
        explorateur = preferences.getString(EXPLORATEUR,null)
        //preferences?.getString()
        //var token = preferences?.getString(TOKEN,null)
        var explorateurJson = JSONObject(explorateur)
        var token = explorateurJson.getString("token")

        // On prepare le token pour envoie.
        authorization = "bearer " + token


        nav_left_view.setNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                //R.id.nav_camera -> {
                  //  Toast.makeText(this,"Camera",Toast.LENGTH_LONG).show()
                //}
                R.id.nav_accueil -> {
                    Runnable {
                        val transaction = fragmentManager.beginTransaction()
                        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        transaction.replace(R.id.contentFrame, AccueilFragment.newInstance(authorization))
                        transaction.commit()
                    }
                }

                R.id.nav_lstUnites -> {
                    Runnable {
                        val transaction = fragmentManager.beginTransaction()
                        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        transaction.replace(R.id.contentFrame, UniteListFragment.newInstance(authorization))
                        transaction.addToBackStack("ListeUnites")
                        transaction.commit()
                    }.run()
                }

                R.id.nav_lstExplorations -> {
                    Runnable {
                        val transaction = fragmentManager.beginTransaction()
                        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        transaction.replace(R.id.contentFrame, ExplorationListFragment.newInstance(authorization))
                        transaction.addToBackStack("ListeExploration")
                        transaction.commit()
                    }.run()
                }


                R.id.nav_Runes -> {
                    Runnable {
                        val transaction = fragmentManager.beginTransaction()
                        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        transaction.replace(R.id.contentFrame, RunesFragment.newInstance(authorization))
                        transaction.addToBackStack("Runes")
                        transaction.commit()
                    }.run()
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        transaction.replace(R.id.contentFrame, AccueilFragment.newInstance(authorization))
        transaction.commit()
        Toast.makeText(this,"Connecté.",Toast.LENGTH_SHORT).show()
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

                postPortalKey("6F11EF96-B3A5-414D-909E-0F8EB9A2B045")
                /*IntentIntegrator(this).initiateScan() // `this` is the current Activity*/
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
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
                Runnable {
                    val transaction = fragmentManager.beginTransaction()
                    //transaction.replace(R.id.contentFrame/*, ExplorationDetailsFragment.newInstance(explorateur, result.contents)*/)
                    transaction.commit()
                }.run()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun deconnexion() {

        // On supprime le token.
        val preferences_token : SharedPreferences? = this.getSharedPreferences(TOKEN, 0)
        var editor_token : SharedPreferences.Editor? = preferences_token?.edit()
        editor_token?.putString(TOKEN,"")
        editor_token?.commit()

        // On envoie à l'écran de connexion.
        val intent = Intent(this,ConnexionActivity::class.java)
        startActivity(intent)

        // On indique que la déconnexion est complété.
        Toast.makeText(this,"Déconnexion completé.",Toast.LENGTH_SHORT).show()
    }



    fun afficherRunes() {

        RUNES_URL.httpGet()
        .header("Authorization" to authorization)
        .responseJson{ request, response, result ->
            when (response.statusCode) {
                200 -> {
                    val runes = Runes(result.get())



                    // On appel la barre de navigation.
                    //var navigationView = findViewById(R.id.nav_right_view)

                    // On demande le menu.
                    //var menu = navigationView.menu

                    // On prepare les noms :
                    //var title_rune_air = "Air : " + runes.air
                    /*var title_rune_darkness = "Darkness : " + runes.darkness
                    var title_rune_earth = "Earth : " + runes.earth
                    var title_rune_energy = "Energy : " + runes.energy
                    var title_rune_fire = "Fire : " + runes.fire
                    var title_rune_life = "Life : " + runes.life
                    var title_rune_light = "Light : " + runes.light
                    var title_rune_logic = "Logic : " + runes.logic
                    var title_rune_music = "Music : " + runes.music
                    var title_rune_space = "Space : " + runes.space
                    var title_rune_toxic = "Toxic : " + runes.toxic
                    var title_rune_water = "Water : " + runes.water*/

                    // On change les Runes.
                    //menu.findItem(R.id.nav_right_view).setTitle(title_rune_air)
                    /*menu.findItem(R.id.nav_darkness).setTitle(title_rune_darkness)
                    menu.findItem(R.id.nav_earth).setTitle(title_rune_earth)
                    menu.findItem(R.id.nav_energy).setTitle(title_rune_energy)
                    menu.findItem(R.id.nav_fire).setTitle(title_rune_fire)
                    menu.findItem(R.id.nav_life).setTitle(title_rune_life)
                    menu.findItem(R.id.nav_light).setTitle(title_rune_light)
                    menu.findItem(R.id.nav_logic).setTitle(title_rune_logic)
                    menu.findItem(R.id.nav_music).setTitle(title_rune_music)
                    menu.findItem(R.id.nav_space).setTitle(title_rune_space)
                    menu.findItem(R.id.nav_toxic).setTitle(title_rune_toxic)
                    menu.findItem(R.id.nav_water).setTitle(title_rune_water)*/

                    //navigationView.setNavigationItemSelectedListener(this)
                }
                404 -> {
                    Toast.makeText(this, "Erreur: ressource non trouvée!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun postPortalKey(key:String) {

        Runnable {
            val transaction = fragmentManager.beginTransaction()
            //transaction.replace(R.id.contentFrame, ExplorationDetailsFragment.newInstance(explorateur, key))
            //transaction.commit()
        }.run()


    }
}
