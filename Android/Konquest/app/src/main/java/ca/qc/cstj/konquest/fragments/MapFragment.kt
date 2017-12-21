package ca.qc.cstj.konquest.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.qc.cstj.konquest.R
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    private var authorization = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authorization = arguments!!.getString(ARG_AUTHORIZATION)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonMapQuitter.setOnClickListener {
            val transaction  = fragmentManager.beginTransaction()
            transaction.replace(R.id.contentFrame,AccueilFragment.newInstance(authorization))
            transaction.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // Les paramètres.
        private val ARG_AUTHORIZATION= "authorization"
        fun newInstance(auth:String): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            args.putString(ARG_AUTHORIZATION, auth)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
