package com.example.mylogin

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.logindemo.Participante
import com.example.logindemo.R
import kotlinx.android.synthetic.main.activity_detalle_participante.*

class DetalleParticipante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_participante)
        var intent=getIntent()

        val participante = intent.getSerializableExtra("participante") as Participante

        txt_id.text = participante.detalle

    }
}