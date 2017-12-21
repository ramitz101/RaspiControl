package ca.qc.cstj.konquest.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.cstj.konquest.R

//import ca.qc.cstj.konquest.fragments.UniteListFragment.OnListFragmentInteractionListener
import ca.qc.cstj.konquest.models.Unite
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_unite.view.*

class UniteRecyclerViewAdapter(private val mValues: List<Unite>,
                               private val mListener: OnListFragmentInformationUnique?) : RecyclerView.Adapter<UniteRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_unite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mValues[position])
        //holder.mIdView.text = mValues[position].id
        //holder.mContentView.text = mValues[position].content

        holder.mView.setOnClickListener {
            mListener!!.onListFragmentInteraction(holder.unite)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        var lblName = mView.lblName
        var imgUnite = mView.imgUnite
        var unite: Unite? = null

        fun bind(unite: Unite){
            this.unite = unite

            // Image
            Picasso.with(imgUnite.context).load(unite.imageURL).fit().centerInside().into(imgUnite)
            lblName.text = unite.name
        }
    }
}
