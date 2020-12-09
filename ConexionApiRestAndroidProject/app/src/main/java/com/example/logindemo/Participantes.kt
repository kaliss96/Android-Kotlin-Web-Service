package com.example.logindemo

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_participantes.*
import org.json.JSONException
import org.json.JSONObject


class Participantes : AppCompatActivity() {

    lateinit var agregarPersonal : Button
    lateinit var logout_facebook_btnn : Button
    lateinit var listarPersonal : Button
    lateinit var txt_nombre : EditText
    lateinit var txt_cedula : EditText
    lateinit var txt_celular : EditText
    lateinit var txt_correo : EditText
    lateinit var txt_observaciones : EditText
    //lateinit var prefHelper: PrefHelper
    private var mCallbackManager: CallbackManager? = null
    private val URL_ROOT = "https://apiregistro.000webhostapp.com/API_PERSONAL/v1/?opt="
    val URL_ADD_PERSONAL = URL_ROOT + "addpersonal"
    val URL_LISTAR_PERSONAL = URL_ROOT + "getpersonal"
    private val TAG = "FacebookLogin"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participantes)
        mCallbackManager = CallbackManager.Factory.create()

        agregarPersonal = findViewById(R.id.btn_agregar)
        listarPersonal = findViewById(R.id.btn_listar)
        txt_nombre = findViewById(R.id.txt_nombre)
        txt_cedula = findViewById(R.id.txt_cedula)
        txt_celular = findViewById(R.id.txt_cel)
        txt_correo = findViewById(R.id.txt_correo)
        txt_observaciones = findViewById(R.id.txt_obs)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_googleid))
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        var intent=getIntent()
        val tokefb = intent.getStringExtra("tokefb")


        val acct = GoogleSignIn.getLastSignedInAccount(this)



        if(tokefb!=null)
        {
           /* Toast.makeText(applicationContext, tokefb, Toast.LENGTH_LONG)
                .show()*/
            logout_facebook.visibility= View.VISIBLE

        }
        if (acct != null) {

            sign_out_google.visibility= View.VISIBLE

        }





        sign_out_google.setOnClickListener { view: View? ->  mGoogleSignInClient.signOut().addOnCompleteListener { task: Task<Void> -> if (task.isSuccessful) {

            /*Toast.makeText(applicationContext, "Cerrado", Toast.LENGTH_LONG)
                    .show()*/
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
            sign_out_google.visibility= View.GONE

            finish();
                }
                }
                }



        agregarPersonal.setOnClickListener{
            addPersonal()
        }

        logout_facebook.setOnClickListener{
            logoutFace()
        }

        listarPersonal.setOnClickListener{
            val intent = Intent(application, ListaParticipantes::class.java)
            startActivity(intent)
        }


    }


    private fun logoutFace() {
        // Logout from Facebook

            LoginManager.getInstance().logOut()
             val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
        finish();
        logout_facebook.visibility= View.GONE


    }

    private fun moveIntent(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun addPersonal() {
        val nombre = txt_nombre.text.toString()
        val cedula = txt_cedula.text.toString()
        val celular = txt_celular.text.toString()
        val correo = txt_correo.text.toString()
        val detalle = txt_observaciones.text.toString()

        val requestQueue = Volley.newRequestQueue(this@Participantes)
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
                params.put("celular", celular)
                params.put("correo", correo)
                params.put("detalle", detalle)
                return params
            }

        }

        //VolleySingleton.instance?.addToRequestQueque(stringRequest)
        requestQueue.add(stringRequest);
    }

}