package ca.qc.cstj.konquest.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.helpers.CONNEXION_URL
import ca.qc.cstj.konquest.models.Explorateur
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_connexion.*


class ConnexionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        buttonLienEnregistrement.setOnClickListener {
            val intent = Intent(this@ConnexionActivity,EnregistrementActivity::class.java)
            startActivity(intent)
        }

        buttonConnexion.setOnClickListener {

            var connexion = Explorateur(editTextCourriel.text.toString(),
                                        null,
                                        editTextMotDePasse.text.toString(),
                                        null,
                                        null,
                                        null,
                                        null,
                                        null)

            CONNEXION_URL.httpPost()
            .header("Content-Type" to "application/json")
            .body(connexion.toJson()).responseJson() { _, response, _ ->
                when(response.statusCode) {
                    200 -> {
                        // On s'occupe de la transition de NON CONNECTÉ À CONNECTÉ.
                        editTextCourriel.text.clear()
                        editTextMotDePasse.text.clear()
                        Toast.makeText(this,"Connexion en cours...",Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@ConnexionActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                    403 -> {

                    }
                    404 -> {

                    }
                    500 -> {

                    }
                }
            }
        }

    }
}
