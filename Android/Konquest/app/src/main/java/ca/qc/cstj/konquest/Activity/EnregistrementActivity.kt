package ca.qc.cstj.konquest.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ca.qc.cstj.konquest.R

class EnregistrementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enregistrement)
    }

    override fun onBackPressed() {
        val intent = Intent(this@EnregistrementActivity,ConnexionActivity::class.java)
        startActivity(intent)
    }
}