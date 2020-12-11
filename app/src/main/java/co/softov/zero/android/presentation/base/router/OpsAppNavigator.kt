package co.softov.zero.android.presentation.base.router

import androidx.fragment.app.*
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.snackbar.Snackbar
import co.softov.zero.android.presentation.extensions.hideKeyboard


open class OpsAppNavigator @JvmOverloads constructor(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager,
    fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory
) : AppNavigator(activity, containerId, fragmentManager, fragmentFactory) {

    override fun applyCommand(command: Command) {
        when (command) {
            is NotificationCommand.Error -> {
                fragmentManager.fragments.lastOrNull()?.let { fragment ->
                    fragment.hideKeyboard()
                    Snackbar.make(fragment.requireView(), command.message, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
            else -> super.applyCommand(command)
        }
    }
}