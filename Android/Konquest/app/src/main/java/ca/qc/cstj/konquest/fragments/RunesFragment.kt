package ca.qc.cstj.konquest.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.helpers.RUNES_URL
import ca.qc.cstj.konquest.models.Runes
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.fragment_runes.*


class RunesFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var authorization: String = ""

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authorization = arguments.getString(ARG_PARAM1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        RUNES_URL.httpGet()
                .header("Authorization" to authorization)
                .responseJson { _, response, result ->
                    when (response.statusCode) {
                        200 -> {
                            val runes = Runes(result.get())

                            TextViewRuneAir.text = runes.air
                            TextViewRuneDarkness.text = runes.darkness
                            TextViewRuneEarth.text = runes.earth
                            TextViewRuneEnergy.text = runes.energy
                            TextViewRuneFire.text = runes.fire
                            TextViewRuneLife.text = runes.life
                            TextViewRuneLight.text = runes.light
                            TextViewRuneLogic.text = runes.logic
                            TextViewRuneMusic.text = runes.music
                            TextViewRuneSpace.text = runes.space
                            TextViewRuneToxic.text = runes.toxic
                            TextViewRuneWater.text = runes.water

                        }
                        404 -> {
                            //Toast.makeText(this, "Erreur: ressource non trouvée!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }


        // Inflate the layout for this fragment_exploration_details
        return inflater.inflate(R.layout.fragment_runes, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Rename parameter arguments, choose names that match
        // the fragment_exploration_details initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "Authorization"

        fun newInstance(param1: String): RunesFragment {
            val fragment = RunesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }
}
