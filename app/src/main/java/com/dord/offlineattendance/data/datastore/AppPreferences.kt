package com.dord.offlineattendance.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dord.offlineattendance.domain.model.DomainType
import com.dord.offlineattendance.domain.model.UserSession
import com.dord.offlineattendance.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(Constants.APP_PREFERENCES)

class AppPreferences(
    private val context: Context
) {
    private object Keys {
        val DOMAIN = stringPreferencesKey(Constants.DOMAIN)
        val USER_ID = stringPreferencesKey(Constants.USER_ID)
        val TOKEN = stringPreferencesKey(Constants.TOKEN)
        val LOGGED_IN = booleanPreferencesKey(Constants.LOGGED_IN)
        val LANGUAGE_KEY = stringPreferencesKey(Constants.LANGUAGE_KEY)
    }

    val domainFlow: Flow<DomainType> =
        context.dataStore.data.map { prefs ->
            runCatching {
                DomainType.valueOf(
                    prefs[Keys.DOMAIN] ?: DomainType.RSETI.name
                )
            }.getOrDefault(DomainType.RSETI)
        }

    suspend fun saveDomain(domain: DomainType) {
        context.dataStore.edit {
            it[Keys.DOMAIN] = domain.name
        }
    }

    suspend fun getSelectedDomain(): DomainType {
        val domain = context.dataStore.data.first()[Keys.DOMAIN]
        return DomainType.valueOf(domain ?: DomainType.RSETI.name)
    }

    val getLanguage: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.LANGUAGE_KEY] ?: "en"
    }

    suspend fun saveLanguage(code: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.LANGUAGE_KEY] = code
        }
    }

    val sessionFlow: Flow<UserSession?> =
        context.dataStore.data.map {
            val loggedIn = it[Keys.LOGGED_IN] ?: false
            if (!loggedIn) null
            else UserSession(
                userId = it[Keys.USER_ID].orEmpty(),
                token = it[Keys.TOKEN].orEmpty(),
                isLoggedIn = true
            )
        }

    suspend fun saveSession(userId: String, token: String) {
        context.dataStore.edit {
            it[Keys.USER_ID] = userId
            it[Keys.TOKEN] = token
            //it[Keys.LOGGED_IN] = true
        }
    }

    suspend fun markLoggedIn() {
        context.dataStore.edit {
            it[Keys.LOGGED_IN] = true
        }
    }

    suspend fun getToken(): String {
        return context.dataStore.data.first()[Keys.TOKEN] ?: ""
    }

    suspend fun clearSession() {
        context.dataStore.edit {
            it.remove(Keys.USER_ID)
            it.remove(Keys.TOKEN)
            it.remove(Keys.LOGGED_IN)
        }
    }
}