package com.davidrevolt.qwitter.core.data.utils.snackbarmanager

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import javax.inject.Inject

class SnackbarManagerImpl @Inject constructor(private val _snackbarHostState: SnackbarHostState) : SnackbarManager {

    override val snackbarHostState: SnackbarHostState
        get() = _snackbarHostState

    override suspend fun showSnackbar(
        message: String,
        action: String?,
        duration: SnackbarDuration
    ): Boolean {
        return _snackbarHostState.showSnackbar(
            message = message,
            actionLabel = action,
            duration = duration,
        ) == SnackbarResult.ActionPerformed
    }
}