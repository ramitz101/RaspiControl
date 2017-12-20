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

                    var exploration = result.get()
                    // le protail existe
                    if(  exploration.obj()["unit"].toString() == "{}")
                    {

                    }else {

                    }

                    if (exploration.obj()["runes"].toString() == "{}")
                    {

                    }else{

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
