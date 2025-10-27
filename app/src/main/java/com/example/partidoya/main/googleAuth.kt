package com.example.partidoya.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.credentials.CredentialManager

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.example.partidoya.R
import androidx.core.net.toUri
import androidx.credentials.CustomCredential
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

suspend fun googleAuth(activity: ComponentActivity){
    val credentialManager = CredentialManager.create(activity)

    val googleIdOption = GetSignInWithGoogleOption.Builder(activity.getString(R.string.web_client_id)).build()
        //.setFilterByAuthorizedAccounts(false)
        //.setServerClientId(activity.getString(R.string.web_client_id))
        //.build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {
        val result: GetCredentialResponse = credentialManager.getCredential(
            context = activity,
            request = request
        )

        val credential = result.credential
        val googleIdToken = credential.data.getString("id_token")
       // viewModel.handleGoogleToken(googleIdToken)
        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken
            Log.i("GOOGLE_SIGN_IN", "ID Token: $idToken")
        }
    } catch (e: GetCredentialException) {
        Log.e("GOOGLE_SIGN_IN", "Sign-in failed: ${e.message}")
        //val getIntent = credentialManager.create(context, request)


        //viewModel.handleGoogleToken(null)
    }
}





fun launchGoogleWebLogin(context: Context) {
    val clientId = context.getString(R.string.web_client_id)
    val redirectUri = "com.example.partidoya:/oauth2redirect"
    val scope = "openid email profile"

    val oauthUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
            "client_id=$clientId&" +
            "redirect_uri=$redirectUri&" +
            "response_type=token&id_token&" + // request ID token
            "scope=$scope&" +
            "prompt=select_account"

    val intent = Intent(Intent.ACTION_VIEW, oauthUrl.toUri())
    context.startActivity(intent)

}