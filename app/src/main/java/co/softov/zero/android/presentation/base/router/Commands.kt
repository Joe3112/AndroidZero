package co.softov.zero.android.presentation.base.router

import com.github.terrakok.cicerone.Command

sealed class NotificationCommand : Command {
    data class Error(val message: String) : NotificationCommand()
}