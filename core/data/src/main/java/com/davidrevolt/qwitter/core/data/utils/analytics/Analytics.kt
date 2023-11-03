package com.davidrevolt.qwitter.core.data.utils.analytics

/**
 * Log Service
 * Sends log crashes to server.
 */

interface Analytics {
    fun logException(throwable: Throwable)
}