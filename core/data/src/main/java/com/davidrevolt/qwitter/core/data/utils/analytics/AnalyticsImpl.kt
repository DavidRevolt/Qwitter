package com.davidrevolt.qwitter.core.data.utils.analytics

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class AnalyticsImpl @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : Analytics {

    override fun logException(throwable: Throwable) {
        firebaseCrashlytics.recordException(throwable)
        Log.d("AppLog", "Crashlytics sent report: ${throwable.message}")
    }
}