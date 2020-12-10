package co.softov.zero.android.domain.redux

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

typealias Reducer<State> = (State, Action) -> State
typealias Dispatch = (Action) -> Unit
typealias Next<State> = (State, Action, Dispatch) -> Action

interface Middleware<State> {
    operator fun invoke(
        state: State,
        action: Action,
        dispatch: Dispatch,
        next: Next<State>,
        scope: CoroutineScope
    ): Action
}

interface Store<State> {
    /**
     * Model will receive intents to be processed via this function.
     *
     * ModelState is immutable. Processed intents will work much like `copy()`
     * and create a new (modified) modelState from an old one.
     */
    fun dispatch(action: Action)

    fun reduce(current: State, action: Action): State

    /**
     * Stream of changes to ModelState
     *
     * Every time a modelState is replaced by a new one, this Flow will
     * emit.
     *
     * This is what views subscribe to.
     */
    @ExperimentalCoroutinesApi
    val state: StateFlow<State>
}

@OptIn(ExperimentalCoroutinesApi::class)
open class SimpleStore<State>(
    startingState: State,
    private val reducers: List<Reducer<State>>,
    private val middleware: List<Middleware<State>>
) : Store<State>, CoroutineScope {

    private val _state = MutableStateFlow(startingState)
    override val state: StateFlow<State> get() = _state

    private val actions = Channel<Action>()

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO

    init {
        launch {
            actions.receiveAsFlow()
                .onEach { action ->
                    val newAction = dispatchToMiddleWare(action)
                    _state.value = (reduce(state.value, newAction))
                    // Timber.d("Action -> $newAction : State -> ${state.value}")
                }.launchIn(this)
        }
    }

    override fun dispatch(action: Action) {
        launch(Dispatchers.Main) { actions.send(action) }
    }

    override fun reduce(current: State, action: Action): State {
        var newState = current
        for (reducer in reducers) {
            newState = reducer(newState, action)
        }
        return newState
    }

    private fun dispatchToMiddleWare(action: Action): Action {
        return next(0)(state.value, action, ::dispatch)
    }

    private fun next(index: Int): Next<State> {
        if (index == middleware.size) {
            return { _, action, _ -> action }
        }

        return { state, action, dispatch ->
            middleware[index].invoke(state, action, dispatch, next(index + 1), this)
        }
    }
}