package com.example.mobileordersystem.authorization

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth



class LoginActivity : AppCompatActivity() {

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build())
    val RC_SIGN_IN = 100
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        val actionCodeSettings = ActionCodeSettings.newBuilder()
//            .setUrl("https://mobileordersystem.page.link/g3bg")
//            .setHandleCodeInApp(true)
//            .setIOSBundleId(" mobileordersystem.firebaseapp.com ")
//            .setAndroidPackageName(
//                "com.example.mobileordersystem",
//                true, /* installIfNotAvailable */
//                "0" /* minimumVersion */
//            )
//            .build()
//        startActivityForResult(
//            AuthUI.getInstance()
//                .createSignInIntentBuilder()
//                .setAvailableProviders(
//                    arrayListOf(
//                        AuthUI.IdpConfig.EmailBuilder().enableEmailLinkSignIn()
//                            .setActionCodeSettings(actionCodeSettings).build()
//                    )
//                )
//                .build(),
//            RC_SIGN_IN
//        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in

                val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)

                // ...
            } else {
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)

                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

//
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.i("czemu", "czemu")
//
//
//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)
//            if (resultCode == Activity.RESULT_OK) {
//                val user = FirebaseAuth.getInstance().currentUser
//                Log.i(user.toString(), "USERRRRRR")
//                if (null != user) {
//                    if ("password" == user.getProviderData().get(0).getProviderId()) {
//                        if (!user.isEmailVerified()) {
//                            Log.i("AA", "halo")
//                            /* Send Verification Email */
//                            user.sendEmailVerification()
//                                .addOnCompleteListener { task ->
//                                    /* Check Success */
//                                    if (task.isSuccessful) {
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "Verification Email Sent To: " + user.getEmail()!!,
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        Log.i("wyslal", "ok")
//                                    } else {
//                                        Log.e("AAAAA", "sendEmailVerification", task.exception)
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "Failed To Send Verification Email!",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                        Log.i("nie wyslal", "ok")
//                                    }
//                                }
//                            Toast.makeText(
//                                applicationContext,
//                                "Email not verified!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            Log.i("nie zweryfikowany", "ok")
//                        }
//                        Log.i("cos", "pppp")
//
//                    }
//                    Log.i("tamto", "aaa")
//                    val intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
//
//                }
//            }else {
//                // Sign in failed. If response is null the user canceled the
//                // sign-in flow using the back button. Otherwise check
//                // response.getError().getErrorCode() and handle the error.
//                // ...
//            }
//        }
//    }






}
