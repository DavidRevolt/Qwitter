package com.davidrevolt.qwitter.core.data.utils.networkmonitor

import kotlinx.coroutines.flow.Flow
/**
 * Utility to check if there any internet connection available
 * */
interface NetworkMonitor {
    fun isOnline(): Flow<Boolean>
}