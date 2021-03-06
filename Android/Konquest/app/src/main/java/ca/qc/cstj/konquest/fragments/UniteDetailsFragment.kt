package ca.qc.cstj.konquest.fragments

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.models.Unite
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_unite_details.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment_exploration_details must implement the
 * [UniteDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UniteDetailsFragment.newInstance] factory method to
 * create an instance of this fragment_exploration_details.
 */
class UniteDetailsFragment(private val href:String,private val auth:String) : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment_exploration_details
        val url = href
        url.httpGet()
                .header("Authorization" to auth)
                .responseJson { request, response, result ->
            when(response.statusCode){
                200-> {
                    val unite = Unite(result.get())

                    lblName.text = unite.name
                    lblLifeQte.text = unite.life
                    lblSpeedQte.text = unite.speed
                   // Picasso.with(imgUnite.context).load(unite.imageURL).fit().centerInside().into(im)

                }
                404-> {
                    Toast.makeText(this.context, "Erreur: ressource non trouvée!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Inflate the layout for this fragment_exploration_details
        return inflater.inflate(R.layout.fragment_unite_details, container, false)
    }




}// Required empty public constructor
