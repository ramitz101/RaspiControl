package ca.qc.cstj.konquest.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.helpers.ENREGISTREMENT_URL
import ca.qc.cstj.konquest.helpers.TOKEN
import ca.qc.cstj.konquest.helpers.TOKEN_INFORMATION
import ca.qc.cstj.konquest.models.Explorateur
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_enregistrement.*
import org.json.JSONObject

class EnregistrementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enregistrement)

        buttonEnregistrement.setOnClickListener {

            var enregistrement = Explorateur(editTextCourrielEnregistrement.text.toString(),
                                             editTextPseudonymeEnregistrement.text.toString(),
                                             editTextMotDePasseEnregistrement.text.toString(),
                                            null,
                                            null,
                                            null,
                                            null,
                                            null)

            ENREGISTREMENT_URL.httpPost()
            .header("Content-Type" to "application/json")
            .body(enregistrement.toJson()).responseJson() { _, response, result ->
                when(response.statusCode) {
                    201 -> {
                        editTextCourrielEnregistrement.text.clear()
                        editTextPseudonymeEnregistrement.text.clear()
                        editTextMotDePasseEnregistrement.text.clear()

                        Toast.makeText(this,"Connexion en cours...", Toast.LENGTH_SHORT).show()

                        // Mettre le token dans une variable.
                        val jObject : JSONObject = result as JSONObject
                        var token : String = jObject.getString("token")

                        //Sauvegarder Token.
                        val preferences_Token_Info : SharedPreferences? = this.getSharedPreferences(TOKEN_INFORMATION, 0)
                        var editor_Token_Info : SharedPreferences.Editor? = preferences_Token_Info?.edit()
                        editor_Token_Info?.putBoolean(TOKEN_INFORMATION,true)
                        editor_Token_Info?.commit()

                        val preferences_Token : SharedPreferences? = this.getSharedPreferences(TOKEN, 0)
                        var editor_Token : SharedPreferences.Editor? = preferences_Token?.edit()
                        editor_Token!!.putString(TOKEN,token)
                        editor_Token?.commit()

                        // On change d'activity.
                        val intent = Intent(this@EnregistrementActivity,MainActivity::class.java)
                        startActivity(intent)
                    }
                    404 -> {

                    }
                    500 -> {

                    }

                }
                }
            }
    }

    override fun onBackPressed() {
        val intent = Intent(this@EnregistrementActivity,ConnexionActivity::class.java)
        startActivity(intent)
    }
}
