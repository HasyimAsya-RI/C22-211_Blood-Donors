package com.c22211.bloodapp.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.c22211.bloodapp.databinding.UserListItemBinding
import com.c22211.bloodapp.model.UserModel
import com.c22211.bloodapp.ui.home.HomeFragment

class UserAdapter( val context: HomeFragment, var list: ArrayList<UserModel> ): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder( val binding: UserListItemBinding ): RecyclerView.ViewHolder( binding.root )

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): UserViewHolder {
        return UserViewHolder( UserListItemBinding.inflate(LayoutInflater.from( parent.context ), parent, false) )
    }

    fun updateData( dataItem: ArrayList<UserModel> ) {
        list = dataItem
        notifyDataSetChanged()
    }

    override fun onBindViewHolder( holder: UserViewHolder, position: Int ) {
        holder.binding.txtBlood.text     = list[position].blood.toString()
        holder.binding.txtName.text      = list[position].name.toString()
        holder.binding.txtDistrict.text  = list[position].district.toString()
        holder.binding.txtRegency.text   = list[position].division.toString()

        holder.binding.imgContact.setOnClickListener {
            val phone  = list[position].phone.toString()
            val intent = Intent()

            intent.action = Intent.ACTION_DIAL
            intent.data   = Uri.parse( "tel: $phone" )
            context.startActivity( intent )
        }

       setAnimation( holder.binding.root )
    }

    fun setAnimation( view: View ) {
        val animation: Animation = AnimationUtils.loadAnimation( context.requireContext(), android.R.anim.slide_in_left )

        view.animation = animation
    }

    override fun getItemCount() = list.size
}