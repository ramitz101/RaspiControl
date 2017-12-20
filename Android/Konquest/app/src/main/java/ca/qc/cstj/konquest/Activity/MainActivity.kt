package ca.qc.cstj.konquest.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils.isEmpty
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.fragments.RunesFragment
import ca.qc.cstj.konquest.fragments.UniteDetailsFragment
import ca.qc.cstj.konquest.fragments.UniteListFragment
import ca.qc.cstj.konquest.helpers.EXPLORATEUR_URL
import ca.qc.cstj.konquest.helpers.PORTALKEY_URL
import ca.qc.cstj.konquest.helpers.RUNES_URL
import ca.qc.cstj.konquest.helpers.TOKEN
import ca.qc.cstj.konquest.models.Runes
import ca.qc.cstj.konquest.models.Explorateur
import ca.qc.cstj.konquest.models.Unite
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), UniteListFragment.OnListFragmentInteractionListener {


    /*override fun onFragmentInteraction(uri: Uri) {
        // Pour le fragment_exploration_details Runes.
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

    var authorization : String = ""

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


        afficherRunes()




        // On obtient le token.
        val preferences : SharedPreferences? = this.getSharedPreferences(TOKEN, 0)
        var token = preferences?.getString(TOKEN,null)

        // On prepare le token pour envoie.
        authorization = "bearer " + token


        nav_left_view.setNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                //R.id.nav_camera -> {
                  //  Toast.makeText(this,"Camera",Toast.LENGTH_LONG).show()
                //}

                R.id.nav_lstUnites -> {
                    Runnable {
                        val transaction = fragmentManager.beginTransaction()
                        //transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        transaction.replace(R.id.contentFrame, UniteListFragment(authorization))
                        //transaction.addToBackStack("ListeUnite")
                        transaction.commit()
                    }.run()
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
                postPortalKey("6F11EF96-B3A5-414D-909E-0F8EB9A2B045")
                rafraichirDataMain()
                /*IntentIntegrator(this).initiateScan() // `this` is the current Activity*/
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
                postPortalKey(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun rafraichirDataMain() {

        // On effectue la requête.
        EXPLORATEUR_URL.httpGet()
        .header("Authorization" to authorization)
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

        val URL = PORTALKEY_URL + key

        URL.httpGet()

        .responseJson{ request, response, result ->
            when (response.statusCode) {
                200 -> {
                    // le protail existe
                    Toast.makeText(this, "Le portail est grand ouvert!", Toast.LENGTH_SHORT).show()

                    val obj = response
                    /*if(isEmpty(obj)) {

                    }*/



                    // 1. regarder si il y a une unite
                    // 2. est ce que je peux la débloquer. si oui offrir l'option de la débloquer et terminer le voyage. si non, aucune option
                    // 3.ajouter les runes ou les soustraire. et la unite si il la débloquée
                    // 4. créer un exploration dans la liste d'exploration








                }
                404 -> {
                    // le portail existe pas
                    Toast.makeText(this, "Erreur: ce portail mène au néant", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
