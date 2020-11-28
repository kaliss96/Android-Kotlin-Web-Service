package com.example.conexionapirest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ListaParticipantes : AppCompatActivity() {
    private val URL_ROOT = "http://192.168.1.5/heroapi/HeroApi/v1/?op"
    val URL_GET_PARTICIPANTES = URL_ROOT + "addParticipants"

    lateinit var myPartcipantListView : ListView
    //private var myPartcipantList: MutableList<participante>? = null
    lateinit var myPartcipantList: MutableList<participante>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_participantes)

        myPartcipantListView = findViewById(R.id.participanteListView) as ListView
        myPartcipantList = mutableListOf<participante>()
        loadUsuario()
    }

    private fun loadUsuario() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            URL_GET_PARTICIPANTES,
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("participante")

                        for (i in 0..array.length() -1) {
                            val objectParticipantes = array.getJSONObject(i)
                            val myParticipanteListIn = participante(
                                objectParticipantes.getString("nombre"),
                                objectParticipantes.getString("cedula")
                            )
                            myPartcipantList.add(myParticipanteListIn)
                            val adapter = participantes_list(this, myPartcipantList)
                            myPartcipantListView.adapter = adapter
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()
                    }
                } catch (e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener{ volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }
}