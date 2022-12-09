package com.c22211.bloodapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.c22211.bloodapp.adapter.UserAdapter
import com.c22211.bloodapp.databinding.FragmentHomeBinding
import com.c22211.bloodapp.model.UserModel
import com.c22211.bloodapp.utils.Config
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment: Fragment() {

    lateinit var binding:    FragmentHomeBinding
    lateinit var db:         FirebaseFirestore
    lateinit var searchText: String

    private lateinit var list:    ArrayList<UserModel>
    private lateinit var adapter: UserAdapter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        binding = FragmentHomeBinding.inflate( layoutInflater )

        list    = ArrayList()
        adapter = UserAdapter( this, list )
        db      = FirebaseFirestore.getInstance()

        Config.showDialog( requireContext() )
        db.collection( "users" ).addSnapshotListener { value, error ->
            val list = arrayListOf<UserModel>()
            val data = value?.toObjects( UserModel::class.java )

            list.addAll( data!! )
            binding.rvUser.adapter = UserAdapter( this, list )
            adapter.updateData( list )
            Config.hideDialog()
        }
        searchData()

        return binding.root
    }

    private fun searchData() {
        binding.edtSearch.addTextChangedListener(
            object: TextWatcher {
                override fun beforeTextChanged( s: CharSequence?, start: Int, count: Int, after: Int ) { }

                override fun onTextChanged( s: CharSequence?, start: Int, before: Int, count: Int ) { }

                override fun afterTextChanged( s: Editable? ) {
                    searchText = s.toString().toLowerCase()
                    updateRecylerView()
                }
            }
        )
    }

    private fun updateRecylerView() {
        val data = ArrayList<UserModel>()

        for( item in list ) {
            val coinName = item.phone!!.lowercase( Locale.getDefault() )

            if( coinName.contains(searchText) ) {
                data.add( item )
            }
        }
        adapter.updateData( data )
    }
}