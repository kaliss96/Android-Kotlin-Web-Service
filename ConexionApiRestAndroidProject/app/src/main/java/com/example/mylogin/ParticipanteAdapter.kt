package com.example.mylogin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.logindemo.Participante
import com.example.logindemo.R
import kotlinx.android.synthetic.main.activity_detalle_participante.view.*
import kotlinx.android.synthetic.main.activity_participantes.view.*

class ParticipanteAdapter(private val mContext: Context, private val listaPartici: List<Participante>) : ArrayAdapter<Participante>(mContext, 0, listaPartici) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.participantes_layout_view, parent, false)

        val participante = listaPartici[position]

        layout.txt_id.text = participante.nombre
        layout.txt_cedula.setText(participante.cedula)
        layout.txt_cel.setText(participante.celular)
        layout.txt_correo.setText(participante.correo)

        return layout
    }

}