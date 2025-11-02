package com.example.partidoya.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.example.partidoya.R
import androidx.core.net.toUri
import androidx.credentials.CustomCredential
import com.example.partidoya.viewModels.GoogleAuth
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

@RequiresApi(Build.VERSION_CODES.O)
suspend fun googleAuth(activity: ComponentActivity, googleAuth: GoogleAuth){
    val credentialManager = CredentialManager.create(activity)

    val googleIdOption = GetSignInWithGoogleOption
        .Builder(activity.getString(R.string.web_client_id))
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {
        val result: GetCredentialResponse = credentialManager.getCredential(
            context = activity,
            request = request
        )

        val credential = result.credential
        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken
            Log.i("GOOGLE_SIGN_IN", "ID Token: $idToken")

           googleAuth.auth(idToken);
        }
    } catch (e: GetCredentialException) {
        Log.e("GOOGLE_SIGN_IN", "Sign-in failed: ${e.message}")

    }
}
