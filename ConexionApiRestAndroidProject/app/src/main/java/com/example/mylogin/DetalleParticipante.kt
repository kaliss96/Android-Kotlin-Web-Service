package com.example.mylogin

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.logindemo.R


class DetalleParticipante : AppCompatActivity() {

    private var id_detalle: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_participante)

        id_detalle = findViewById<View>(R.id.idTxtDesc) as TextView
        val iin = intent
        val b = iin.extras

        if (b != null) {
            val j = b["name"] as String?
            id_detalle!!.setText(j)
        }
    }
}