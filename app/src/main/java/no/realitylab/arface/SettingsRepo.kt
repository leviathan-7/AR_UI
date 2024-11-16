package no.realitylab.arface

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SettingsRepo(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "PreferencesFilename",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun mustacheBool(): Boolean {
        return (sharedPreferences.getString("MustacheBool", "") ?: return true) == "true"
    }
    fun makeUpBool(): Boolean {
        return (sharedPreferences.getString("MakeUpBool", "") ?: return true) == "true"
    }
    fun switchMustacheBool() {
        sharedPreferences.edit().putString("MustacheBool", (if(mustacheBool()) "false" else "true")).apply()
    }
    fun switchMakeUpBool() {
        sharedPreferences.edit().putString("MakeUpBool", (if(makeUpBool()) "false" else "true")).apply()
    }

}