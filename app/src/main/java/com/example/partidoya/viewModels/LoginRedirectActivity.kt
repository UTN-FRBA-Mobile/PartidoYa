package com.example.partidoya.viewModels

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class LoginRedirectActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data: Uri? = intent?.data
        if (data != null) {
            // ID token is in fragment: #id_token=...&state=...
            val idToken = data.getFragmentParameter("id_token")
            if (idToken != null) {
                // Send to backend for verification and JWT
                //sendTokenToBackend(idToken)
                Log.i("GOOGLE_SIGN_IN", "Google token: $idToken")
            }
        }
    }
}

fun Uri.getFragmentParameter(key: String): String? {
    return this.fragment?.split("&")?.firstOrNull { it.startsWith("$key=") }
        ?.substringAfter("=")
}