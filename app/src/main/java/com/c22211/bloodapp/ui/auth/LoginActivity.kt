package com.c22211.bloodapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.c22211.bloodapp.databinding.ActivityLoginBinding
import com.c22211.bloodapp.ui.home.HomeActivity
import com.c22211.bloodapp.utils.Config
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var auth:    FirebaseAuth

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivityLoginBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener {
            val email    = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if( email.isEmpty() ) {
                binding.edtEmail.setError( "Masukkan email Anda!" )
                binding.edtEmail.requestFocus()
            }
            else if( password.isEmpty() ) {
                binding.edtPassword.setError( "Masukkan kata sandi!" )
                binding.edtPassword.requestFocus()
            }
            else {
                auth.signInWithEmailAndPassword( email, password ).addOnCompleteListener {
                    Config.showDialog( this )
                    if( it.isSuccessful ) {
                        val intent = Intent( this, HomeActivity::class.java )

                        startActivity( intent )
                        finish()
                        Toast.makeText( this, "Masuk Berhasil", Toast.LENGTH_SHORT ).show()
                    }
                    else {
                        Config.hideDialog()
                        Toast.makeText( this, it.exception!!.message, Toast.LENGTH_SHORT ).show()
                    }
                }
            }
        }

        binding.btnRegistration.setOnClickListener {
            val intent = Intent( this, RegistrationActivity::class.java )

            startActivity( intent )
        }

        binding.btnForgotPassword.setOnClickListener {
            val intent = Intent( this, ForgotActivity::class.java )

            startActivity( intent )
        }
    }
}