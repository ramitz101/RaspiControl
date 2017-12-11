package ca.qc.cstj.konquest.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ca.qc.cstj.konquest.R
import kotlinx.android.synthetic.main.activity_connexion.*


class ConnexionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)

        buttonLienEnregistrement.setOnClickListener {
            val intent = Intent(this@ConnexionActivity,EnregistrementActivity::class.java)
            startActivity(intent)
        }

    }
}
