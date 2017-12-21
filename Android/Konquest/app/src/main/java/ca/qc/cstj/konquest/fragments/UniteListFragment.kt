package ca.qc.cstj.konquest.fragments

import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.adapters.UniteRecyclerViewAdapter
import ca.qc.cstj.konquest.helpers.UNITES_URL
import ca.qc.cstj.konquest.models.Unite
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet

class UniteListFragment: Fragment() {
    // Les paramètres personnalisé.
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null
    private var unites = mutableListOf<Unite>()
    private var authorization = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authorization = arguments!!.getString(ARG_AUTHORIZATION)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_unite_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }

            // Aller dans le recycler view des untis.
            view.adapter = UniteRecyclerViewAdapter(unites, mListener)


            UNITES_URL.httpGet()
            .header("Authorization" to authorization)
            .responseJson { _, response, result ->
                when(response.statusCode) {
                    200 -> {
                        createUniteList(result.get())
                        view.adapter.notifyDataSetChanged()
                        Toast.makeText(this.context, "Tout Fonctionne!", Toast.LENGTH_SHORT).show()
                    }
                    404-> {
                        Toast.makeText(this.context, "Erreur: ressource non trouvée!", Toast.LENGTH_SHORT).show()
                    }
                    401-> {
                        Toast.makeText(this.context, "Erreur: vous devez être connecté pour faire ceci", Toast.LENGTH_SHORT).show()
                    }
                    503-> {
                        Toast.makeText(this.context, "Service temporairement indisponible ou en maintenance", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this.context, "Une erreur est survenue", Toast.LENGTH_SHORT).show()

                    }
                }

            }
        }
        return view
    }

    // la fonction qui créer la liste de units et qui insert les données à l'intérieur.
    fun createUniteList(json: Json) {
        // On crée un tableau.
        val tabJson = json.array()
        // On nettoie les anciennes données.
        //unites.clear()
        // On insert les nouvelles données de units.
        for( i in 0.. (json.array().length() -1 )) {
            unites.add(Unite(Json(tabJson[i].toString())))
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(unite: Unite?)
    }

   companion object {

        // TODO: Customize parameter argument names
        private val ARG_AUTHORIZATION = "authorization"

        // TODO: Customize parameter initialization
        fun newInstance(auth:String): UniteListFragment {
            val fragment = UniteListFragment()
            val args = Bundle()
            args.putString(ARG_AUTHORIZATION, auth)
            fragment.arguments = args
            return fragment
        }
    }
}
