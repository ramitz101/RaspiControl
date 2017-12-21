package ca.qc.cstj.konquest.fragments


import android.os.Bundle
import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import ca.qc.cstj.konquest.Activity.MainActivity

import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.helpers.EXPLORATION_URL
import ca.qc.cstj.konquest.helpers.PORTALKEY_URL
import ca.qc.cstj.konquest.models.Exploration
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_exploration_details.*
import kotlinx.android.synthetic.main.fragment_exploration_details.view.*
import kotlinx.android.synthetic.main.fragment_unite_details.view.*
import org.json.JSONObject

/*
/**
 * A simple [Fragment] subclass.
 */
class ExplorationDetailsFragment : Fragment() {

        var portalKey = ""
        var explorateur = ""
        var ilYAUneUnite = true
        lateinit var exploration: JSONObject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        portalKey =  arguments!!.getString(ARG_PORTALKEY)
        explorateur = arguments!!.getString(ARG_EXPLORATEUR)




    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {



         var explorateurJson = JSONObject(explorateur)
       var explorateurUser = explorateurJson.getJSONObject("user")
       var explorateurRunes = explorateurUser.getJSONObject("runes")



        val URL = PORTALKEY_URL + portalKey

        URL.httpGet()
        .responseJson{ _, response, result ->
            var test = response
            when (response.statusCode) {
                200 -> {
                    // le protail existe
                    var explorationString = result.get().content
                    exploration= JSONObject(explorationString)


                    // true si il y a une unite
                    if ( exploration.getString("unit") != "{}")
                    {
                        var explorationUnite = exploration.getJSONObject("unit")
                        var explorationRunesUnite = explorationUnite.getJSONObject("kernel")
                        var explorationImage = explorationUnite.getString("imageURL")

                        unit_life.text = explorationUnite.getString("life")
                        unit_name.text = explorationUnite.getString("name")
                        unit_speed.text = explorationUnite.getString("speed")
                        unit_affinity.text = explorationUnite.getString("affinity")


                        BindUniteRunes(explorationRunesUnite)

                        Picasso.with(image_unite.context).load(explorationImage).fit().centerInside().into(image_unite)
                    }else {
                        ilYAUneUnite = false // boutton non disponible
                        Toast.makeText(this.context, "Aucune unite à débloquer", Toast.LENGTH_SHORT).show()

                    }

                    // 1. regarder si il y a une unite
                    // 2. est ce que je peux la débloquer. si oui offrir l'option de la débloquer et terminer le voyage. si non, aucune option
                    // 3.ajouter les runes ou les soustraire. et la unite si il la débloquée
                    // 4. créer un exploration dans la liste d'exploration

                        }
                        404 -> {
                            // le portail existe pas
                        } else ->{
                    }
                    }
                }
                }
        //        404 -> {
         //           // le portail existe pas
         //       } else ->{
         //       var ee = ""
         //       }
         //   }
       // }

        air.text = explorateurRunes.getString("air")
        darkness.text = explorateurRunes.getString("darkness").toString()
        earth.text = explorateurRunes.getString("earth").toString()
        energy.text = explorateurRunes.getString("energy").toString()
        fire.text = explorateurRunes.getString("fire").toString()
        life.text = explorateurRunes.getString("life").toString()
        light.text = explorateurRunes.getString("light").toString()
        logic.text = explorateurRunes.getString("logic").toString()
        music.text = explorateurRunes.getString("music").toString()
        space.text = explorateurRunes.getString("space").toString()
        toxic.text = explorateurRunes.getString("toxic").toString()
        water.text = explorateurRunes.getString("water").toString()


        var token = explorateurJson.getString("token")

        // On prepare le token pour envoie.
        var authorization = "bearer " + token

        view!!.fin_voyage.setOnClickListener {

            allerAccueil(authorization)
        }

                var token = explorateurJson.getString("token")

                // On prepare le token pour envoie.
                var authorization = "bearer " + token


        view!!.debloquer_unite.setOnClickListener{
            //if (ilYAUneUnite) {



                var explorationConstruite = Exploration(exploration.getString("dateExploration"),
                        exploration.getString("destination"),
                        exploration.getJSONObject("runes").toString(),
                        exploration.getJSONObject("unit").toString()

                )
                var explorateurString = explorationConstruite.toString()




                EXPLORATION_URL.httpPost()
                .header("Authorization" to authorization,"Content-Type" to "application/json" )
                .body(explorateurString).responseJson { request, response, result ->
                    when (response.statusCode) {
                        201 -> {
                            Toast.makeText(this.context, "Exploration envoyé", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            //Toast.makeText(this.context, response.statusCode, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            //}else{
                //Toast.makeText(this.context, "Aucune unite à débloquer", Toast.LENGTH_SHORT).show()
            //}

            allerAccueil(authorization)

        }
        super.onViewCreated(view, savedInstanceState)





    }

    override fun onDetach() {
        super.onDetach()
    }
    fun allerAccueil(auth : String) {
        val transaction  = fragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame,AccueilFragment.newInstance(auth))
        transaction.commit()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment_exploration_details
        return inflater.inflate(R.layout.fragment_exploration_details, container, false)

    }

     fun BindUniteRunes(explorationRunes: JSONObject){
         unite_air.text = explorationRunes.getString("air")
         unite_darkness.text = explorationRunes.getString("darkness")
         unite_earth.text = explorationRunes.getString("earth")
         unite_energy.text = explorationRunes.getString("energy")
         unite_fire.text = explorationRunes.getString("fire")
         unite_life.text = explorationRunes.getString("life")
         unite_light.text = explorationRunes.getString("light")
         unite_logic.text = explorationRunes.getString("logic")
         unite_music.text = explorationRunes.getString("music")
         unite_space.text = explorationRunes.getString("space")
         unite_toxic.text = explorationRunes.getString("toxic")
         unite_water.text = explorationRunes.getString("water")
    }

    interface OnClickListener {
        fun OnClickListener(item: Exploration)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_EXPLORATEUR = "explorateur"
        private val ARG_PORTALKEY = "portalKey"


         // TODO: Customize parameter initialization
         fun newInstance(explorateur:String, portalKey:String): ExplorationDetailsFragment {
             val fragment = ExplorationDetailsFragment()
             val args = Bundle()
             args.putString(ARG_EXPLORATEUR,explorateur)
             args.putString(ARG_PORTALKEY,portalKey)
             fragment.arguments = args
             return fragment
         }
    }



}// Required empty public constructor

*/