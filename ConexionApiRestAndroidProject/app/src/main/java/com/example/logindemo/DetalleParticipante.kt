package com.example.logindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logindemo.R
import kotlinx.android.synthetic.main.activity_detalle_participante.*
import org.json.JSONObject
import org.json.JSONException
class DetalleParticipante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_participante)
        var intent=getIntent()
        val participante = intent.getSerializableExtra("participante") as Participante


        try{
        val obj =  JSONObject(participante.detalle)
          /*  Toast.makeText(applicationContext, obj.getString("id"), Toast.LENGTH_LONG)
                    .show()*/




            idTxtDesc.text="realizado civiris: recibimos el valor: "+participante.nombre+"-"+obj.getString("id")





        } catch (e: JSONException) {
            e.printStackTrace()
            /*Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG)
                    .show()*/
        }

    }


}