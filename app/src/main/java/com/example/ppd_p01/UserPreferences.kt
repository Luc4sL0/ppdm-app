package com.example.ppd_p01
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

object UserPreferences {
    private val USER_ID_KEY = intPreferencesKey("user_id")

    suspend fun saveUserId(context: Context, userId: Int) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    fun getUserId(context: Context): Flow<Int?> {
        return context.dataStore.data.map{ prefs ->
            prefs[USER_ID_KEY]
        }
    }
}