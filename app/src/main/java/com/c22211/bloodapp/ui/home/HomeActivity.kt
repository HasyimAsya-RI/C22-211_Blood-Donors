package com.c22211.bloodapp.ui.home

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.c22211.bloodapp.R
import com.c22211.bloodapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity: AppCompatActivity() {

    lateinit var binding:               ActivityHomeBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var auth:                  FirebaseAuth

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivityHomeBinding.inflate( layoutInflater )
        setContentView( binding.root )

        auth = FirebaseAuth.getInstance()

        actionBarDrawerToggle = ActionBarDrawerToggle( this, binding.drawerLayout, R.string.open, R.string.close )
        binding.drawerLayout.addDrawerListener( actionBarDrawerToggle!! )

        val navHostFragment = supportFragmentManager.findFragmentById( R.id.containerView )
        val navController   = navHostFragment!!.findNavController()
        val popupMenu       = PopupMenu( this, null )

        popupMenu.inflate( R.menu.bottom_menu )
        binding.bottomBar.setupWithNavController( popupMenu.menu, navController )

        navController.addOnDestinationChangedListener(
            object: NavController.OnDestinationChangedListener {
                override fun onDestinationChanged( controller: NavController, destination: NavDestination, arguments: Bundle? ) {
                    title =
                        when( destination.id ) {
                            R.id.homeFragment    -> "Daftar Darah"
                            R.id.profileFragment -> "Profil Pengguna"
                            else                 -> ""
                        }
                }
            }
        )
    }
}