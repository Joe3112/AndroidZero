package co.softov.zero.android.domain.redux.state

import co.softov.zero.android.domain.redux.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

data class ZeroState(
    val zero: Int = 0
)

sealed class UserAction : Action.FeatureAction() {
    object ZeroAction : UserAction()
}

fun AppState.reduceUserState(action: Action): AppState {
    return when (action) {
        is UserAction -> copy(state = state.reduce(action))
        else -> this
    }
}

fun ZeroState.reduce(action: Action): ZeroState {
    return when (action) {
        is UserAction.ZeroAction -> TODO("Do Something!")
        else -> this // No-Op
    }
}

@Singleton
class ZeroMiddleware : Middleware<AppState> {

    override fun invoke(
        state: AppState,
        action: Action,
        dispatch: Dispatch,
        next: Next<AppState>,
        scope: CoroutineScope
    ): Action {
        when (action) {

            is Init -> scope.launch {

            }

            else -> NoOp
        }
        return next(state, action, dispatch)
    }
}