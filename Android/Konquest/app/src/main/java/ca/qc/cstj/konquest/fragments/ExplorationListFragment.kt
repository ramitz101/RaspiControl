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
import ca.qc.cstj.konquest.adapters.ExplorationRecyclerViewAdapter
import ca.qc.cstj.konquest.adapters.OnListFragmentInformationUnique
import ca.qc.cstj.konquest.helpers.EXPLORATION_URL

import ca.qc.cstj.konquest.models.Exploration
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet

class ExplorationListFragment : Fragment() {

    // Les paramètres du fragment.
    private val mColumnCount = 1
    private var explorations = mutableListOf<Exploration>()
    private var mListener: OnListFragmentInformationUnique? = null
    private var authorization = ""

    // On crée le fragment et ont initialises ses paramètres
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authorization = arguments!!.getString(ARG_AUTHORIZATION)
    }

    // On crée la vue.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_exploration_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }

            view.adapter = ExplorationRecyclerViewAdapter(explorations, mListener)

            // On va chercher les exploration avec le serveur.
            EXPLORATION_URL.httpGet()
            .header("Authorization" to authorization)
            .responseJson { _, response, result ->
                when(response.statusCode) {
                    200 -> {
                        createExplorationList(result.get())
                        view.adapter.notifyDataSetChanged()
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

    // La fonction crée la liste des explorations.
    fun createExplorationList(json: Json) {
        // On crée un tableau.
        val tabJson = json.array()
        // On nettoie les anciennes données.
        explorations.clear()
        // On insert les nouvelles données de units.
        for( i in 0.. (json.array().length() -1 )) {
            explorations.add(Exploration(Json(tabJson[i].toString())))
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInformationUnique) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

   // interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
   //     fun onListFragmentInteraction(item: Exploration)
   // }

    companion object {
        // Les paramètres :
        private val ARG_AUTHORIZATION = "authorization"

        //La fonction qui crée une nouvelle instance du fragment.
        fun newInstance(auth:String): ExplorationListFragment {
            val fragment = ExplorationListFragment()
            val args = Bundle()
            args.putString(ARG_AUTHORIZATION, auth)
            fragment.arguments = args
            return fragment
        }
    }

}
