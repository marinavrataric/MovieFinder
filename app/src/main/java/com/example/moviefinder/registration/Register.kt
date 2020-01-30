package com.example.moviefinder.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.moviefinder.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_btn_register.setOnClickListener {
            val email = email_et_register.text.toString()
            val password = password_et_register.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fields 'email' and 'password' cannot be empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("taag","* * * Email is: " + email)
            Log.d("taag","* * * Password is:  $password")

            //spajanje na Firebase, spremanje novih korisnika, sign up

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("taag", "* * * Successfully created user with id: ${task.result!!.user!!.uid}")
                        Toast.makeText(baseContext, "Authentication success. Now log in.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("taag", "* * * Failed to create user.", task.exception)
                        Toast.makeText(baseContext, "Authentication failed. Use minimum 6 characters/Account is already existing.", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                }
        }

        register_image_view.setImageResource(R.drawable.register)

        already_account_tv.setOnClickListener {
            Log.d("taag", "* * * Trying to open Login activity.")
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}
