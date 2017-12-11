package ca.qc.cstj.konquest.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.cstj.konquest.R

import ca.qc.cstj.konquest.fragments.UnitListFragment.OnListFragmentInteractionListener

class UnitRecyclerViewAdapter(private val mValues: List<Unit>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_unit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mValues[position])
        //holder.mIdView.text = mValues[position].id
        //holder.mContentView.text = mValues[position].content

        holder.mView.setOnClickListener {
            mListener!!.onListFragmentInteraction(holder.unit)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        //val mIdView: TextView
        //val mContentView: TextView
        var unit: Unit? = null

        init {
            //mIdView = mView.findViewById(R.id.id) as TextView
            //mContentView = mView.findViewById(R.id.content) as TextView
        }

        fun bind(unit: Unit){
            this.unit = unit
        }

        //override fun toString(): String {
         //   return super.toString() + " '" + mContentView.text + "'"
        //}
    }
}
