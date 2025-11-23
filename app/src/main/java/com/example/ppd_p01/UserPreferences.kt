package com.example.ppd_p01

import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

object UserPreferences {
    private val USER_ID_KEY = intPreferencesKey("user_id")
    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")

    suspend fun saveUserId(context: Context, userId: Int) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    suspend fun saveUserEmail(context: Context, email: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_EMAIL_KEY] = email
        }
    }

    fun getUserId(context: Context): Flow<Int?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID_KEY]
        }
    }

    fun getUserEmail(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_EMAIL_KEY]
        }
    }
}
