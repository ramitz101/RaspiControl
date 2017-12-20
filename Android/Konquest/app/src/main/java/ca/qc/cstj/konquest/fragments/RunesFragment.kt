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
                              savedInstanceState: Bundle): View? {

        RUNES_URL.httpGet()
                .header("Authorization" to authorization)
                .responseJson { _, response, result ->
                    when (response.statusCode) {
                        200 -> {
                            val runes = Runes(result.get())

                            // On prepare les noms :
                            var title_rune_air = "Air : " + runes.air
                            var title_rune_darkness = "Darkness : " + runes.darkness
                            var title_rune_earth = "Earth : " + runes.earth
                            var title_rune_energy = "Energy : " + runes.energy
                            var title_rune_fire = "Fire : " + runes.fire
                            var title_rune_life = "Life : " + runes.life
                            var title_rune_light = "Light : " + runes.light
                            var title_rune_logic = "Logic : " + runes.logic
                            var title_rune_music = "Music : " + runes.music
                            var title_rune_space = "Space : " + runes.space
                            var title_rune_toxic = "Toxic : " + runes.toxic
                            var title_rune_water = "Water : " + runes.water
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
