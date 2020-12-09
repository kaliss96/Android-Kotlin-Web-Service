package com.example.logindemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*


const val RC_SIGN_IN=123

class MainActivity : AppCompatActivity() {
    private val EMAIL = "email"

    private val TAG = "FacebookLogin"
    private var mCallbackManager: CallbackManager? = null
    lateinit var signOut: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create()
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        val loginButton = findViewById<LoginButton>(R.id.login_fbutton)
        /*val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired*/

        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().retrieveLoginStatus(this, object : LoginStatusCallback {
            override fun onCompleted(accessToken: AccessToken) {
                // User was previously logged in, can log them in directly here.
                // If this callback is called, a popup notification appears that says
                // "Logged in as <User Name>"

            }

            override fun onFailure() {
                // No access token could be retrieved for the user
            }

            override fun onError(exception: Exception) {
                // An error occurred
            }
        })

        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")

              //
               /* Toast.makeText(applicationContext, loginResult.getAccessToken().getToken(), Toast.LENGTH_LONG)
                    .show()*/


                val intent = Intent(application, Participantes::class.java)
                intent.putExtra("tokefb", loginResult.getAccessToken().getToken())
                startActivity(intent)
                finish();

            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {}
        })





        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_googleid))
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
       // signOut = findViewById<View>(R.id.sign_out_button) as Button
        sign_in_button.visibility = View.VISIBLE
        result_name.visibility = View.GONE

        sign_in_button.setSize(SignInButton.SIZE_STANDARD);
        sign_in_button.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            sign_in_button.visibility = View.GONE


          //  signOut.isClickable = true
          //  signOut.visibility = View.VISIBLE



            val intent = Intent(application, Participantes::class.java)
            startActivity(intent)
            finish();
        }
        /*signOut.setOnClickListener { view: View? ->  mGoogleSignInClient.signOut().addOnCompleteListener { task: Task<Void> -> if (task.isSuccessful) {

            sign_in_button.visibility = View.VISIBLE
            signOut.visibility = View.GONE

            sign_in_button.isClickable = true
            finish();
        }
        }
        }*/
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        else
            mCallbackManager!!.onActivityResult(requestCode, resultCode, data)

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            sign_in_button.visibility = View.GONE
            //sign_out_button.visibility= View.VISIBLE
           // sign_out_button.isClickable=true


            /*tv_name.text = account!!.displayName
            tv_name.visibility = View.VISIBLE*/
            val intent = Intent(application, Participantes::class.java)
            startActivity(intent)
            finish();
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            sign_in_button.visibility = View.VISIBLE
            result_name.text = ""
            result_name.visibility = View.GONE
        }
    }
}