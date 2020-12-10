package co.softov.zero.android.presentation.base.router

import com.github.terrakok.cicerone.*
import javax.inject.Inject


open class AppRouter : Router() {
    fun showErrorNotification(message: String) {
        executeCommands(NotificationCommand.Error(message))
    }
}

class FlowRouter : AppRouter()

class OpsRouter @Inject constructor(
    private val appRouter: AppRouter,
    private val flowRouter: FlowRouter
) {

    /**
     * Open new screen and add it to the screens chain.
     *
     * @param screen screen
     * @param clearContainer if FALSE then new screen shows over previous
     */
    @JvmOverloads
    fun navigateTo(screen: Screen, clearContainer: Boolean = true) {
        flowRouter.navigateTo(screen, clearContainer)
    }

    /**
     * Clear all screens and open new one as root.
     *
     * @param screen screen
     */
    fun newRootScreen(screen: Screen) {
        appRouter.newRootScreen(screen)
    }

    /**
     * Replace current screen.
     *
     * By replacing the screen, you alters the backstack,
     * so by going fragmentBack you will return to the previous screen
     * and not to the replaced one.
     *
     * @param screen screen
     */
    fun replaceScreen(screen: Screen) {
        flowRouter.replaceScreen(screen)
    }

    /**
     * Return fragmentBack to the needed screen from the chain.
     *
     * Behavior in the case when no needed screens found depends on
     * the processing of the [BackTo] command in a [Navigator] implementation.
     *
     * @param screen screen
     */
    fun backTo(screen: Screen?) {
        flowRouter.backTo(screen)
    }

    /**
     * Opens several screens inside single transaction.
     *
     * @param screens
     * @param showOnlyTopScreenView if FALSE then all screen views show together
     */
    @JvmOverloads
    fun newChain(vararg screens: Screen, showOnlyTopScreenView: Boolean = true) {
        flowRouter.newChain(*screens, showOnlyTopScreenView = showOnlyTopScreenView)
    }

    /**
     * Clear current stack and open several screens inside single transaction.
     *
     * @param screens
     * @param showOnlyTopScreenView if FALSE then all screen views show together
     */
    @JvmOverloads
    fun newRootChain(vararg screens: Screen, showOnlyTopScreenView: Boolean = true) {
        appRouter.newRootChain(*screens, showOnlyTopScreenView = showOnlyTopScreenView)
    }

    /**
     * Remove all screens from the chain and exit.
     *
     * It's mostly used to finish the application or close a supplementary navigation chain.
     */
    fun finishChain() {
        flowRouter.finishChain()
    }

    /**
     * Return to the previous screen in the chain.
     *
     * Behavior in the case when the current screen is the root depends on
     * the processing of the [Back] command in a [Navigator] implementation.
     */
    fun exit() {
        flowRouter.exit()
    }

    fun showErrorNotification(message: String) {
        flowRouter.showErrorNotification(message)
    }

}