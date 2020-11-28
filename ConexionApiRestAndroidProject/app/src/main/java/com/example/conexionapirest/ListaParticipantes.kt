package com.example.conexionapirest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ListaParticipantes : AppCompatActivity() {
    private val URL_ROOT = "http://192.168.1.5/API_PERSONAL/v1/?opt="
    val URL_GET_PERSONAL = URL_ROOT + "getpersonal"

    lateinit var myPartcipantListView : ListView
    //private var myPartcipantList: MutableList<participante>? = null
    lateinit var myPartcipantList: MutableList<participante>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_participantes)

        myPartcipantListView = findViewById(R.id.participanteListView) as ListView
        myPartcipantList = mutableListOf<participante>()
        loadPersonal()
    }
    private fun loadPersonal() {

        val requestQueue = Volley.newRequestQueue(this@ListaParticipantes)
        val stringRequest = object : StringRequest(Request.Method.POST, URL_GET_PERSONAL,
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        val array = obj.getJSONArray("listPersonal")

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