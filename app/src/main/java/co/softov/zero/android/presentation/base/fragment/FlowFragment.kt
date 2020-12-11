package co.softov.zero.android.presentation.base.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import co.softov.zero.android.R
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppScreen
import co.softov.zero.android.di.FlowNavigation
import co.softov.zero.android.presentation.base.router.OpsAppNavigator
import co.softov.zero.android.presentation.extensions.setLaunchScreen
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
abstract class FlowFragment : BaseFragment() {

    @FlowNavigation
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override val layoutResourceId: Int = R.layout.layout_container
    protected open val flowContainerId: Int = R.id.container

    private val currentFragment
        get() = childFragmentManager.findFragmentById(flowContainerId) as? BaseFragment

    protected open val navigator: Navigator by lazy {
        object : OpsAppNavigator(requireActivity(), flowContainerId, childFragmentManager) {

            override fun setupFragmentTransaction(
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment?
            ) {
                onFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(getLaunchScreen())
        }
    }

    abstract fun getLaunchScreen(): AppScreen

    protected open fun onFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {
        // Do nothing by default
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("lifecycle: onResume")
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
        Timber.d("lifecycle: onPause")
    }
}