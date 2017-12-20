package ca.qc.cstj.konquest.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.helpers.CONNEXION_URL
import ca.qc.cstj.konquest.helpers.TOKEN
import ca.qc.cstj.konquest.helpers.TOKEN_INFORMATION
import ca.qc.cstj.konquest.models.Explorateur
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_connexion.*


class ConnexionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        // On obtient le token.
        val preferences_token : SharedPreferences? = this.getSharedPreferences(TOKEN, 0)
        var token = preferences_token?.getString(TOKEN,null)

        // On obtient si le token est bon.
        val preferences_token_information : SharedPreferences? = this.getSharedPreferences(TOKEN_INFORMATION, 0)
        var token_information = preferences_token_information?.getBoolean(TOKEN_INFORMATION,false)


        // Si le token n'égale pas rien, on connecte.
        if(token != "" && token_information == true)
        {
            val intent = Intent(this@ConnexionActivity,MainActivity::class.java)
            startActivity(intent)
        }


        // Aller sur l'activity d'enregistrement.
        buttonLienEnregistrement.setOnClickListener {
            val intent = Intent(this@ConnexionActivity,EnregistrementActivity::class.java)
            startActivity(intent)
        }

        // On tente de se connecter.
        buttonConnexion.setOnClickListener {

            var connexion = Explorateur(null,
                                        null,
                                        null,
                                        editTextCourriel.text.toString(),
                                        editTextMotDePasse.text.toString(),
                                        null,
                                        null,
                                        null,
                                        null)

            CONNEXION_URL.httpPost()
            .header("Content-Type" to "application/json")
            .body(connexion.toJson()).responseJson { _, response, result ->
                when(response.statusCode) {
                    201 -> {
                        // On s'occupe de la transition de NON CONNECTÉ À CONNECTÉ.
                        editTextCourriel.text.clear()
                        editTextMotDePasse.text.clear()
                        Toast.makeText(this,"Connexion en cours...",Toast.LENGTH_SHORT).show()

                        // Mettre le token dans une variable.
                        var token = result.get().obj().getString("token")

                        //Sauvegarder Token.
                        val preferences_Token : SharedPreferences? = this.getSharedPreferences(TOKEN, 0)
                        var editor_Token : SharedPreferences.Editor? = preferences_Token?.edit()
                        editor_Token!!.putString(TOKEN,token)
                        editor_Token?.commit()

                        // On indique que le token est bon.
                        val preferences_tokenInformation : SharedPreferences? = this.getSharedPreferences(ca.qc.cstj.konquest.helpers.TOKEN_INFORMATION, 0)
                        var editor_tokenInformation : SharedPreferences.Editor? = preferences_tokenInformation?.edit()
                        editor_tokenInformation?.putBoolean(TOKEN_INFORMATION,true)
                        editor_tokenInformation?.commit()

                        // On change d'activity.
                        val intent = Intent(this@ConnexionActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                    401 -> { Toast.makeText(this,"Mauvais courriel ou mot de passe.",Toast.LENGTH_LONG).show() }
                    403 -> { }
                    404 -> { Toast.makeText(this,"Votre compte est introuvable.",Toast.LENGTH_LONG).show() }
                    500 -> { Toast.makeText(this,"Le serveur à une erreur interne.",Toast.LENGTH_LONG).show() }
                    503 -> { Toast.makeText(this,"Le serveur est en maintenance. Merci de patienter.",Toast.LENGTH_LONG).show() }
                }
            }
        }

    }
}
