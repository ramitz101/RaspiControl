package ca.qc.cstj.konquest.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ca.qc.cstj.konquest.R
import ca.qc.cstj.konquest.helpers.ENREGISTREMENT_URL
import ca.qc.cstj.konquest.models.Explorateur
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_enregistrement.*

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

                        Toast.makeText(this,"Compte enregistrée.", Toast.LENGTH_SHORT).show()

                        // On change d'activity.
                        val intent = Intent(this@EnregistrementActivity,ConnexionActivity::class.java)
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
