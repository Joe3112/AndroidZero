package co.softov.zero.android.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import co.softov.zero.android.R
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.iiitech.operations.di.AppNavigation
import com.iiitech.operations.presentation.base.fragment.BaseFragment
import com.iiitech.operations.presentation.extensions.view.addWindowInsetToPadding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    @AppNavigation
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setTheme(R.style.Theme_AndroidZero)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)
        viewModel.loadData()

        val view = findViewById<View>(R.id.container)
        view.addWindowInsetToPadding(top = true, bottom = true)
    }

    override fun onResumeFragments() {
        Timber.d("Lifecycle: onResumeFragments")
        navigatorHolder.setNavigator(navigator)
        super.onResumeFragments()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
        Timber.d("Lifecycle: onPause")
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}