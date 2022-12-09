package com.c22211.bloodapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.c22211.bloodapp.databinding.ActivityRegistrationBinding
import com.c22211.bloodapp.model.UserModel
import com.c22211.bloodapp.ui.home.HomeActivity
import com.c22211.bloodapp.utils.AddressUtils
import com.c22211.bloodapp.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationActivity: AppCompatActivity() {

    lateinit var binding:   ActivityRegistrationBinding
    lateinit var db:        FirebaseFirestore
    lateinit var auth:      FirebaseAuth
    lateinit var blood:     String
    lateinit var division:  String
    lateinit var districts: String

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivityRegistrationBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        db   = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val item = arrayOf(
            "Pilih Golongan Darah",
            "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"
        )

        binding.spinnerBlood.setAdapter(
            ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item, item )
        )

        binding.spinnerBlood.setOnItemSelectedListener(
            object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                    blood = binding.spinnerBlood.getSelectedItem().toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        )

        val items = AddressUtils.getDivisions()

        binding.spinnerRegency.setAdapter(
            ArrayAdapter( this, android.R.layout.simple_spinner_dropdown_item, items )
        )

        binding.spinnerRegency.setOnItemSelectedListener(
            object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                    division = binding.spinnerRegency.getSelectedItem().toString()
                    binding.spinnerDistrict.setAdapter(
                        ArrayAdapter( this@RegistrationActivity, android.R.layout.simple_spinner_dropdown_item, AddressUtils.getDistrict(division) )
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) { }
            }
        )

        binding.spinnerDistrict.setOnItemSelectedListener(
            object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long ) {
                    districts = binding.spinnerDistrict.getSelectedItem().toString()
                }

                override fun onNothingSelected( parent: AdapterView<*>? ) { }
            }
        )

        binding.btnSignUp.setOnClickListener {
            val email    = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val name     = binding.edtName.text.toString()
            val phone    = binding.edtPhone.text.toString()

            if( email.isEmpty() ) {
                binding.edtEmail.setError( "Masukkan email Anda!" )
                binding.edtEmail.requestFocus()
            }
            else if( password.isEmpty() ) {
                binding.edtPassword.setError( "Masukkan kata sandi!" )
                binding.edtPassword.requestFocus()
            }
            else if( name.isEmpty() ) {
                binding.edtName.setError( "Masukkan nama Anda!" )
                binding.edtName.requestFocus()
            }
            else if( blood.equals("Pilih Golongan Darah") ) {
                Toast.makeText( this, "Pilih golongan darah Anda!", Toast.LENGTH_SHORT ).show()
            }
            else if( division.equals("Pilih Kabupaten") ) {
                Toast.makeText( this, "Pilih kabupaten Anda!", Toast.LENGTH_SHORT ).show()
            }
            else if( districts.equals("Pilih Kecamatan") ) {
                Toast.makeText( this, "Pilih kecamatan Anda!", Toast.LENGTH_SHORT ).show()
            }
            else if( phone.isEmpty() ) {
                binding.edtPhone.setError( "Masukkan nomor telepon Anda!" )
                binding.edtPhone.requestFocus()
            }
            else {
                auth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener {
                    if( it.isSuccessful ) {
                        val currentUserId = auth.currentUser!!.uid
                        val data = UserModel(
                            currentUserId,
                            name,
                            phone,
                            blood,
                            division,
                            districts,
                            email,
                            password
                        )

                        Config.showDialog( this )
                        db.collection( "users" ).document( currentUserId ).set( data ).addOnCompleteListener {
                            val intent = Intent( this, HomeActivity::class.java )

                            startActivity( intent )
                            finish()
                            Toast.makeText( this, "Registrasi Berhasil", Toast.LENGTH_SHORT ).show()
                            }
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