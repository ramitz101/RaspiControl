package ca.qc.cstj.konquest.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.helpers.EXPLORATEUR_URL
import ca.qc.cstj.konquest.models.Explorateur
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.fragment_accueil.*

class AccueilFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    private var authorization = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authorization = arguments!!.getString(ARG_AUTHORIZATION)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // On effectue la requête.
        EXPLORATEUR_URL.httpGet()
                .header("Authorization" to authorization)
                .responseJson { _, response, result ->
                    when(response.statusCode) {
                        200 -> {

                            val explorateur = Explorateur(result.get())

                            textViewMainPseudonyme.text = explorateur.pseudonyme
                            textViewMainInox.text = explorateur.inox.toString()
                            textViewMainLocation.text = explorateur.location
                        }
                    }
                }

        return inflater.inflate(R.layout.fragment_accueil, container, false)
    }

    fun automatiqueFunction(context: Context)
    {
        val handler = Handler()
        val delay : Long = 1000 //milliseconds

        handler.postDelayed(object : Runnable {
            override fun run() {
                // Fait le code que l'on veut répété.

                handler.postDelayed(this, delay)
            }
        }, delay)
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
        fun newInstance(auth:String): AccueilFragment {
            val fragment = AccueilFragment()
            val args = Bundle()
            args.putString(ARG_AUTHORIZATION, auth)
            fragment.arguments = args
            return fragment
        }
    }
}