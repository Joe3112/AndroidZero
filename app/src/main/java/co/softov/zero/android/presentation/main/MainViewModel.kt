package co.softov.zero.android.presentation.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import co.softov.zero.android.domain.redux.AppState
import co.softov.zero.android.domain.redux.AppStore
import co.softov.zero.android.domain.redux.Init
import co.softov.zero.android.presentation.base.viewmodel.BaseViewModel
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(
    store: AppStore,
    @Assisted savedState: SavedStateHandle
) : BaseViewModel<MainViewState, MainViewEvent>(store) {

    override val initialState: MainViewState
        get() = MainViewState()

    init {
        Timber.d("init")
        dispatchStoreAction(Init)
    }

    override fun onAppStateChange(state: AppState) {
        Timber.d("onAppStateChange: $state")
    }

    override fun onReduceState(event: MainViewEvent): MainViewState {
        return state
    }
}