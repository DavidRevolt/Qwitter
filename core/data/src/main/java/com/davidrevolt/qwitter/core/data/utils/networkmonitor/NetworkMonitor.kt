package com.davidrevolt.qwitter.core.data.utils.networkmonitor

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    fun isOnline(): Flow<Boolean>
}