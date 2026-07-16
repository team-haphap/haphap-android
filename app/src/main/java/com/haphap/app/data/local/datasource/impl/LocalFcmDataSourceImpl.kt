package com.haphap.app.data.local.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.haphap.app.data.local.datasource.api.LocalFcmDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalFcmDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalFcmDataSource {

    override suspend fun getFcmToken(): String? = dataStore.data
        .map { prefs ->
            prefs[FCM_TOKEN]
        }.firstOrNull()

    override suspend fun setFcmToken(fcmToken: String) {
        dataStore.edit { prefs ->
            prefs[FCM_TOKEN] = fcmToken
        }
    }

    companion object {
        private val FCM_TOKEN = stringPreferencesKey("FCM_TOKEN")
    }
}
