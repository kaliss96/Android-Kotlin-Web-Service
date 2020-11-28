package com.example.conexionapirest

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class participantes_list(private val context: Activity, internal var participante: List<participante>) : ArrayAdapter<participante>(context, R.layout.participantes_layout_view, participante) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.participantes_layout_view, null, true)

        val textViewNombre = listViewItem.findViewById(R.id.id_nombre_partc) as TextView
        val textViewCedula = listViewItem.findViewById(R.id.id_cedula_partc) as TextView

        val myparticipante = participante[position]
        textViewNombre.text = myparticipante.nombre
        textViewCedula.text = myparticipante.cedula

        return listViewItem
    }
}