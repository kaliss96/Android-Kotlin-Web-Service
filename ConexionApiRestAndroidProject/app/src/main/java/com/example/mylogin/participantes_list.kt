package com.example.logindemo

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ArrayAdapter
import android.widget.TextView
import java.security.AccessControlContext
import java.text.FieldPosition

class participantes_list(private val context: Activity, internal var participante: List<Participante>) : ArrayAdapter<Participante>(context, R.layout.participantes_layout_view, participante) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.participantes_layout_view, null, true)

        val textViewNombre = listViewItem.findViewById(R.id.id_nombre_partc) as TextView
        val textViewCedula = listViewItem.findViewById(R.id.id_cedula_partc) as TextView
        val textViewCelular = listViewItem.findViewById(R.id.id_celular_partc) as TextView
        val textViewCorreo = listViewItem.findViewById(R.id.id_correo_partc) as TextView

        val myparticipante = participante[position]
        textViewNombre.text = myparticipante.nombre
        textViewCedula.text = myparticipante.cedula
        textViewCelular.text = myparticipante.celular
        textViewCorreo.text = myparticipante.correo

        return listViewItem
    }
}