package com.example.conexionapirest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

class MainActivity : AppCompatActivity() {
    lateinit var agregarPersonal : Button
    lateinit var listarPersonal : Button
    lateinit var txt_nombre : EditText
    lateinit var txt_cedula : EditText

    private val URL_ROOT = "http://192.168.1.5/API_PERSONAL/v1/?opt="
    val URL_ADD_PERSONAL = URL_ROOT + "addpersonal"
    val URL_LISTAR_PERSONAL = URL_ROOT + "getpersonal"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      /*  val gso: GoogleSignInOptions =
            Creador(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().construir()
        mGoogleSignInClient = GoogleSignIn . getClient ( esto , gso );*/

        agregarPersonal = findViewById(R.id.btn_agregar)
        listarPersonal = findViewById(R.id.btn_listar)
        txt_nombre = findViewById(R.id.txt_nombre)
        txt_cedula = findViewById(R.id.txt_cedula)

        agregarPersonal.setOnClickListener{

            addPersonal()
        }

        listarPersonal.setOnClickListener{
            val intent = Intent(application, ListaParticipantes::class.java)
            startActivity(intent)
        }
    }

    private fun addPersonal() {
        val nombre = txt_nombre.text.toString()
        val cedula = txt_cedula.text.toString()
        val requestQueue = Volley.newRequestQueue(this@MainActivity)
        val stringRequest = object : StringRequest(Request.Method.POST, URL_ADD_PERSONAL,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(
                        applicationContext,
                        obj.getString("message"),
                        Toast.LENGTH_LONG
                    ).show()
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

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>{
                val params = HashMap<String, String>()
                params.put("nombre", nombre)
                params.put("cedula", cedula)
                return params
            }

        }

        //VolleySingleton.instance?.addToRequestQueque(stringRequest)
        requestQueue.add(stringRequest);
    }
}