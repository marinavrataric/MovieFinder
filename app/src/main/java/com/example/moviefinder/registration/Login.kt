package com.example.moviefinder.registration

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.moviefinder.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.moviefinder.R.layout.activity_login)

        login_btn.setOnClickListener {
            val email = email_et_login.text.toString()
            val password = password_et_login.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fields 'email' and 'password' cannot be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("taag", "* * * Login with email: $email")
            Log.d("taag", "* * * Login with password: $password")

            //Firebase Sign In
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("taag", "* * * Successfully sign in with id: ${task.result!!.user!!.uid}")
                        getSharedPreferences("userId", Context.MODE_PRIVATE).edit().putString("userId", task.result!!.user!!.uid).apply()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("taag", "* * * Failed to sign in.", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                }
        }
        back_to_register_tv.setOnClickListener {
            Log.d("taag", "* * * Trying to open Register activity.")
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}