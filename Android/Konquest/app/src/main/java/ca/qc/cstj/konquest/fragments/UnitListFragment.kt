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

import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.adapters.UnitRecyclerViewAdapter
import ca.qc.cstj.konquest.helpers.UNITS_URL
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet

class UnitListFragment : Fragment() {
    // Les paramètres personnalisé.
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null
    private var units = mutableListOf<Unit>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_unit_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }

            // Aller dans le recycler view des untis.
            view.adapter = UnitRecyclerViewAdapter(units, mListener)

            UNITS_URL.httpGet().responseJson { request, response, result ->
                when(response.statusCode) {
                    200 -> {
                        createUnitList(result.get())
                        view.adapter.notifyDataSetChanged()
                    }
                }

            }
        }
        return view
    }

    // la fonction qui créer la liste de units et qui insert les données à l'intérieur.
    fun createUnitList(json: Json) {
        // On crée un tableau.
        val tabJson = json.array()
        // On nettoie les anciennes données.
        units.clear()
        // On insert les nouvelles données de units.
        for( i in 0.. (json.array().length() -1 )) {
            // TODO : IL existe un packet dans Kotlin qui s'appel Unit et ici il y fait référence au lieu de notre model...
            // TODO : Merci de trouver une solution.
            //units.add(Unit(Json(tabJson[i].toString())))
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
        fun onListFragmentInteraction(unit: Unit?)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): UnitListFragment {
            val fragment = UnitListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
