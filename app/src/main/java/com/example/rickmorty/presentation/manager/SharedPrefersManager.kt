package com.example.rickmorty.presentation.manager

import android.content.Context
import com.example.rickmorty.data.delegates.PrefsDelegate
import com.example.rickmorty.data.model.NightMode

class SharedPrefersManager(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var nightMode: NightMode by enumPref(KEY_NIGHT_MODE, NightMode.LIGHT)

    private inline fun <reified E : Enum<E>> enumPref(key: String, defaultValue: E) =
        PrefsDelegate(
            sharedPrefs,
            getValue = { getString(key, null)?.let(::enumValueOf) ?: defaultValue },
            setValue = { putString(key, it.name) }
        )

    companion object {
        private const val PREFS_NAME = "prefs"
        private const val KEY_NIGHT_MODE = "night_mode"
    }
}