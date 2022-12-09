package com.c22211.bloodapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.c22211.bloodapp.R
import com.c22211.bloodapp.databinding.FragmentProfileBinding
import com.c22211.bloodapp.model.UserModel
import com.c22211.bloodapp.ui.auth.LoginActivity
import com.c22211.bloodapp.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment: Fragment() {

    lateinit var binding:   FragmentProfileBinding
    lateinit var db:        FirebaseFirestore
    lateinit var auth:      FirebaseAuth
    lateinit var btnLogOut: Button

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentProfileBinding.inflate( layoutInflater )

        db   = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val currentUserId = auth.currentUser!!.uid

        Config.showDialog( requireContext() )
        db.collection( "users" ).document( currentUserId ).get().addOnSuccessListener { result ->
            val data = result.toObject( UserModel::class.java )

            binding.txtName.setText( data!!.name.toString() )
            binding.txtEmail.setText( data!!.email.toString() )
            binding.txtBlood.setText( data!!.blood ).toString()
            binding.txtDistrict.setText( data!!.district ).toString()
            binding.txtRegency.setText( data!!.division ).toString()
            binding.txtPhone.setText( data!!.phone.toString() )
            Config.hideDialog()
        }

        return binding.root
    }

    override fun onViewCreated( view: View, savedInstanceState: Bundle? ) {
        super.onViewCreated( view, savedInstanceState )

        btnLogOut = view.findViewById( R.id.btn_logOut )
        btnLogOut.setOnClickListener {
            val mIntent = Intent( requireActivity(), LoginActivity::class.java )

            startActivity( mIntent )
        }
    }
}