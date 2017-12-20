package ca.qc.cstj.konquest.fragments


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.helpers.PORTALKEY_URL
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.fragment_exploration_details.*


/**
 * A simple [Fragment] subclass.
 */
class ExplorationDetailsFragment : Fragment() {

        var portalKey = ""
        var authorization = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        portalKey =  arguments!!.getString(ARG_PORTALKEY)
        authorization = arguments!!.getString(ARG_AUTHORIZATION)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {






        val URL = PORTALKEY_URL + portalKey

        URL.httpGet()
        .responseJson{ request, response, result ->
            when (response.statusCode) {
                200 -> {
                    // le protail existe
                    var exploration = result.get()
                    var explorationRunes = exploration.obj()["runes"].toString()

                    unite_air.text = explorationRunes[0].toString()
                    unite_darkness.text = explorationRunes[1].toString()

                    // true si il y a des runes et une unite
                    if ( exploration.obj()["unit"].toString() != "{}" && exploration.obj()["runes"].toString() != "{}")
                    {
                        //
                        if(  exploration.obj()["unit"].toString() != "{}")
                        {

                        }else {
                            //unite vide
                            debloquer_unite.isEnabled = false // boutton non disponible

                        }

                        if (exploration.obj()["runes"].toString() != "{}")
                        {

                        }else{
                            // runes vide

                        }
                    }else {
                       unite_air.text = exploration.obj()["runes"].toString()
                    }

                    // 1. regarder si il y a une unite
                    // 2. est ce que je peux la débloquer. si oui offrir l'option de la débloquer et terminer le voyage. si non, aucune option
                    // 3.ajouter les runes ou les soustraire. et la unite si il la débloquée
                    // 4. créer un exploration dans la liste d'exploration

                }
                404 -> {
                    // le portail existe pas
                }
            }
        }


        // Inflate the layout for this fragment_exploration_details
        return inflater.inflate(R.layout.fragment_exploration_details, container, false)

    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_AUTHORIZATION = "authorization"
        private val ARG_PORTALKEY = "portalKey"

         // TODO: Customize parameter initialization
         fun newInstance(authorization:String, portalKey:String): ExplorationDetailsFragment {
             val fragment = ExplorationDetailsFragment()
             val args = Bundle()
             args.putString(ARG_AUTHORIZATION,authorization)
             args.putString(ARG_PORTALKEY,portalKey)
             fragment.arguments = args
             return fragment
         }
    }

}// Required empty public constructor
