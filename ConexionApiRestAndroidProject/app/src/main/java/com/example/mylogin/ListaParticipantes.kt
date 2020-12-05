package com.example.logindemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mylogin.DetalleParticipante
import org.json.JSONException
import org.json.JSONObject

class ListaParticipantes : AppCompatActivity() {
    private val URL_ROOT = "http://192.168.1.4/API_PERSONAL/v1/?opt="
    val URL_GET_PERSONAL = URL_ROOT + "getpersonal"

    lateinit var myPartcipantListView : ListView
    //private var myPartcipantList: MutableList<participante>? = null
    lateinit var myPartcipantList: MutableList<Participante>
    lateinit var txt_id_detalle : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_participantes)

        myPartcipantListView = findViewById(R.id.participanteListView) as ListView
        myPartcipantList = mutableListOf<Participante>()
        loadPersonal()

        myPartcipantListView.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?,
                                     position: Int, id: Long) {
                val participante_seleccion = myPartcipantList.get(position)
                val intent = Intent(applicationContext, DetalleParticipante::class.java)
                intent.putExtra("ID_PARTICIPANTE",participante_seleccion.id_servidor)
                startActivity(intent)
            }
        })
    }

    private fun loadPersonal() {

        val requestQueue = Volley.newRequestQueue(this@ListaParticipantes)
        val stringRequest = object : StringRequest(Request.Method.POST, URL_GET_PERSONAL,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        val array = obj.getJSONArray("listPersonal")

                        for (i in 0..array.length() - 1) {
                            val objectParticipantes = array.getJSONObject(i)
                            val myParticipanteListIn = Participante(
                                    objectParticipantes.getString("nombre"),
                                    objectParticipantes.getString("cedula"),
                                    objectParticipantes.getString("celular"),
                                    objectParticipantes.getString("correo"),
                                    objectParticipantes.getString("id")
                            )
                            myPartcipantList.add(myParticipanteListIn)
                        }
                        val adapter = participantes_list(this, myPartcipantList)
                        myPartcipantListView.adapter = adapter

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                                .show()
                    }
                }) {
        }

        requestQueue.add(stringRequest);
    }

}