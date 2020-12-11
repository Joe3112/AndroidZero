package co.softov.zero.android.presentation.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.softov.zero.android.BuildConfig
import co.softov.zero.android.domain.redux.Action
import co.softov.zero.android.domain.redux.AppState
import co.softov.zero.android.domain.redux.AppStore
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

abstract
class BaseViewModel<ViewState : BaseViewState, ViewEvent : BaseViewEvent>(
    private val store: AppStore
) : ViewModel() {

    abstract val initialState: ViewState

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData: LiveData<ViewState> get() = stateMutableLiveData

    protected open val enableDebug = false
    private val stateTimeTravelDebugger: StateTimeTravelDebugger? by lazy {
        when (BuildConfig.DEBUG && enableDebug) {
            true -> StateTimeTravelDebugger(this::class.java.simpleName)
            false -> null
        }
    }

    // Delegate will handle state event deduplication
    // (multiple states of the same type holding the same data will not be dispatched multiple times to LiveData stream)
    protected var state by Delegates.observable(initialState) { _, old, new ->
        stateMutableLiveData.value = new

        viewModelScope.launch {
            if (new != old) {
                stateTimeTravelDebugger?.apply {
                    addStateTransition(old, new)
                    logLast()
                }
            }
        }
    }

    fun sendEvent(event: ViewEvent) {
        stateTimeTravelDebugger?.addEvent(event)
        state = onReduceState(event)
    }

    fun dispatchStoreAction(action: Action) {
        store.dispatch(action)
    }

    fun loadData() {
        store.state
            .onEach { onAppStateChange(it) }
            .launchIn(viewModelScope)

        onLoadData()
    }

    protected open fun onLoadData() {}

    protected abstract fun onAppStateChange(state: AppState)

    protected abstract fun onReduceState(event: ViewEvent): ViewState

}
