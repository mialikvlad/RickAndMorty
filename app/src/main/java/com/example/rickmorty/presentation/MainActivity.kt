package com.example.rickmorty.presentation

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.example.rickmorty.R
import com.example.rickmorty.data.model.NightMode
import com.example.rickmorty.presentation.manager.SharedPrefersManager
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val prefsManager: SharedPrefersManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d( "QQQ","Activity created")

        if (savedInstanceState == null) {
            setTheme(R.style.Theme_RickMorty)
            Log.d("QQQ1","loading screen")
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        AppCompatDelegate.setDefaultNightMode(
            when (prefsManager.nightMode) {
                NightMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                NightMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        setContentView(R.layout.activity_main)
    }
}