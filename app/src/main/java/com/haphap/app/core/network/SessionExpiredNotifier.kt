package com.haphap.app.core.network

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionExpiredNotifier @Inject constructor() {
    private val _event = Channel<Unit>(Channel.CONFLATED)
    val event: Flow<Unit> = _event.receiveAsFlow()

    fun notifySessionExpired() {
        _event.trySend(Unit)
    }
}
