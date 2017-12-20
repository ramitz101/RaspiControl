package ca.qc.cstj.konquest.fragments


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.qc.cstj.konquest.R


/**
 * A simple [Fragment] subclass.
 */
class ExplorationDetailsFragment(private val authorization:String, private val portaKey:String) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle): View? {
        // Inflate the layout for this fragment_exploration_details
        return inflater.inflate(R.layout.fragment_exploration_details, container, false)
    }

}// Required empty public constructor
