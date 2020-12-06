package com.example.logindemo

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class ListaParticipantes : AppCompatActivity() {
    private val URL_ROOT = "https://apiregistro.000webhostapp.com/API_PERSONAL/v1/?opt="
    val URL_GET_PERSONAL = URL_ROOT + "getpersonal"
    val listPartici = ArrayList<Participante>()
    lateinit var myPartcipantListView : ListView
    //private var myPartcipantList: MutableList<participante>? = null
    lateinit var myPartcipantList: MutableList<Participante>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_participantes)

        myPartcipantListView = findViewById(R.id.participanteListView) as ListView
        myPartcipantList = mutableListOf<Participante>()
        loadPersonal()

        myPartcipantListView.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?,
                                     position: Int, id: Long) {
                val requestQueue = Volley.newRequestQueue(this@ListaParticipantes)
                val stringRequest = object : StringRequest(Request.Method.POST, URL_GET_PERSONAL,
                        Response.Listener<String> { response ->
                            try {
                                val obj = JSONObject(response)
                                val array = obj.getJSONArray("listPersonal")

                                val intent = Intent(application, DetalleParticipante::class.java)
                                intent.putExtra("participante", listPartici[position])
                                startActivity(intent)

                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        },
                        object : Response.ErrorListener {
                            override fun onErrorResponse(volleyError: VolleyError) {
                                Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                                        .show()
                            }
                        }){}
                requestQueue.add(stringRequest);
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
                                    objectParticipantes.getString("detalle")
                            )
                            listPartici.add(myParticipanteListIn)
                            myPartcipantList.add(myParticipanteListIn)
                            val adapter = participantes_list(this, myPartcipantList)
                            myPartcipantListView.adapter = adapter
                        }

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