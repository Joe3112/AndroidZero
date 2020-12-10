package co.softov.zero.android.domain.redux.middleware

import androidx.viewbinding.BuildConfig
import co.softov.zero.android.domain.redux.*
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoggerMiddleware @Inject constructor() : Middleware<AppState> {

    override fun invoke(
        state: AppState,
        action: Action,
        dispatch: Dispatch,
        next: Next<AppState>,
        scope: CoroutineScope
    ): Action {
        if (BuildConfig.DEBUG) {
            Timber.d("store-middleware ---> in $action")
            val returnValue = next(state, action, dispatch)
            Timber.d("store-middleware <--- out $returnValue")
            return returnValue
        }

        return next(state, action, dispatch)
    }
}