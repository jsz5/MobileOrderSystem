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
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth



class CreateAccountActivity : AppCompatActivity() {

    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder()
    )
    val RC_SIGN_IN = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://mobileordersystem.page.link/g3bg")
            .setHandleCodeInApp(true)
            .setIOSBundleId(" mobileordersystem.firebaseapp.com ")
            .setAndroidPackageName(
                "com.example.mobileordersystem",
                true, /* installIfNotAvailable */
                "0" /* minimumVersion */
            )
            .build()
//        val auth = FirebaseAuth.getInstance()
//        auth.sendSignInLinkToEmail("j.szolomicka@gmail.com", actionCodeSettings)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("AAAAAAA", "Email sent.")
//                }
//            }
//        if (AuthUI.canHandleIntent(intent)) {
//            if (intent.extras == null) {
//                return
//            }
//            val link = intent.extras!!.getString(ExtraConstants.EMAIL_LINK_SIGN_IN)
//            if (link != null) {
//                startActivityForResult(
//                    AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setEmailLink(link)
//                        .setAvailableProviders(arrayListOf(AuthUI.IdpConfig.EmailBuilder().enableEmailLinkSignIn()
//                            .setActionCodeSettings(actionCodeSettings).build()))
//                        .build(),
//                    RC_SIGN_IN
//                )
//            }
//        }


        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    arrayListOf(
                        AuthUI.IdpConfig.EmailBuilder().enableEmailLinkSignIn()
                            .setActionCodeSettings(actionCodeSettings).build()
                    )
                )
                .build(),
            RC_SIGN_IN
        )


//        val actionCodeSettings = ActionCodeSettings.newBuilder()
//            .setUrl("https://mobileordersystem.page.link/g3bg")
//            .setHandleCodeInApp(true)
//            .setIOSBundleId(" mobileordersystem.firebaseapp.com ")
//            .setAndroidPackageName(
//                "com.example.mobileordersystem",
//                true, /* installIfNotAvailable */
//                "12" /* minimumVersion */)
//            .build()
//
//        val auth = FirebaseAuth.getInstance()
//        auth.sendSignInLinkToEmail("j.szolomicka@gmail.com", actionCodeSettings)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("AAA", "Email sent.")
//                }
//            }
//        val intent = intent
//        val emailLink = intent.data!!.toString()
//        if (auth.isSignInWithEmailLink(emailLink)) {
//            val email = "j.szolomicka@gmail.com"
//            auth.signInWithEmailLink(email, emailLink)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d("BBBB", "Successfully signed in with email link!")
//                        val result = task.result
//
//                    } else {
//                        Log.e("CCCC", "Error signing in with email link", task.exception)
//                    }
//                }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        


        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (null != user) {
                    if ("password" == user.getProviderData().get(0).getProviderId()) {
                        if (!user.isEmailVerified()) {
                            /* Send Verification Email */
                            user.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    /* Check Success */
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Verification Email Sent To: " + user.getEmail()!!,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Log.e("AAAAA", "sendEmailVerification", task.exception)
                                        Toast.makeText(
                                            applicationContext,
                                            "Failed To Send Verification Email!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            Toast.makeText(
                                applicationContext,
                                "Email not verified!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    return
                }
            }else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }






}
