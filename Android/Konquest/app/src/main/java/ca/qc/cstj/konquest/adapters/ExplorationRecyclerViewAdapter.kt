package ca.qc.cstj.konquest.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.cstj.konquest.R

import ca.qc.cstj.konquest.fragments.ExplorationListFragment.OnListFragmentInteractionListener
import ca.qc.cstj.konquest.models.Exploration
import kotlinx.android.synthetic.main.card_exploration.view.*

class ExplorationRecyclerViewAdapter(private val mValues: List<Exploration>,
                                     private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<ExplorationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_exploration, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mValues[position])
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var exploration : Exploration? = null

        var lblDateExploration = mView.textViewExplorateurDateExploration
        var lblDepart = mView.textViewExplorateurDepart
        var lblDestination = mView.textViewExplorateurDestination
        var lblUnit = mView.textViewExplorateurUnit
        var lblRune = mView.textViewExplorateurRune


        fun bind(exploration: Exploration)
        {
            this.exploration = exploration

            lblDateExploration.text = exploration.dateExploration
            lblDepart.text = exploration.depart
            lblDestination.text = exploration.destination

            if(exploration.runes == "{}")
            {
                lblRune.text = "Aucune(s) rune(s) gagnée."
            }
            else
            {
                lblRune.text = "A gagné de(s) rune(s)."
            }

            if(exploration.unit == "{}")
            {
                lblUnit.text = "Aucune unit gagnée."
            }
            else
            {
                lblUnit.text = "Une unit gagnée."
            }
        }
    }
}
