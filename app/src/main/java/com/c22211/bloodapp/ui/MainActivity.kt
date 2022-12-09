package com.c22211.bloodapp.ui

import android.app.TaskStackBuilder
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.c22211.bloodapp.ui.auth.LoginActivity
import com.c22211.bloodapp.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity: AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    lateinit var editor:      Editor

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        preferences = getSharedPreferences( "splash", MODE_PRIVATE )
        editor      = preferences.edit()

        Handler( Looper.myLooper()!! ).postDelayed(Runnable {
            if( FirebaseAuth.getInstance().currentUser == null ) {
                val intent = Intent(this, LoginActivity::class.java)

                preferences.getBoolean( "isMain", true )
                startActivity( intent )
                finish()
            }
            else {
                val intent = Intent( this, HomeActivity::class.java )

                editor.putBoolean( "isMain", true )
                editor.apply()
                TaskStackBuilder.create( this )
                    .addNextIntentWithParentStack( intent )
                    .startActivities()
            }
        }, 0 )
    }
}