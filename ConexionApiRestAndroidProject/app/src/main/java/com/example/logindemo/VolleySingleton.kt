package com.example.logindemo

import android.app.Application
import android.content.ContentValues.TAG
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton :Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val requestQueue: RequestQueue? = null
        get() {
            if (field == null){
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    fun <T> addToRequestQueque(request: Request<T>){
        request.tag = TAG
        requestQueue?.add(request)
    }

    companion object{
        private val TAG = VolleySingleton::class.java.simpleName
        @get:Synchronized var instance:VolleySingleton? = null
            private set
    }
}