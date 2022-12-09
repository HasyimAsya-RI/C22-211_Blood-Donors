package com.c22211.bloodapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.c22211.bloodapp.databinding.ActivityForgotBinding
import com.c22211.bloodapp.utils.Config
import com.google.firebase.auth.FirebaseAuth

class ForgotActivity: AppCompatActivity() {

    lateinit var binding: ActivityForgotBinding
    lateinit var auth:    FirebaseAuth

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivityForgotBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.btnFindAccount.setOnClickListener {
            val email = binding.edtEmail.text.toString()

            if( email.isEmpty() ) {
                binding.edtEmail.setError("Masukkan email Anda!")
                binding.edtEmail.requestFocus()
            }
            else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    Config.showDialog( this )
                    if( it.isSuccessful ) {
                        val intent = Intent( this, LoginActivity::class.java )

                        Toast.makeText( this, "Kode Berhasil Dikirim ke Email Anda", Toast.LENGTH_SHORT ).show()
                        startActivity( intent )
                        finish()
                    }
                    else {
                        Config.hideDialog()
                        Toast.makeText( this, it.exception!!.message, Toast.LENGTH_SHORT ).show()
                    }
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent( this, LoginActivity::class.java )

            startActivity( intent )
        }
    }
}