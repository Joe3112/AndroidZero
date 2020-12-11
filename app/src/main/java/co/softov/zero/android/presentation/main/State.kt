package co.softov.zero.android.presentation.main

import co.softov.zero.android.presentation.base.viewmodel.BaseViewEvent
import co.softov.zero.android.presentation.base.viewmodel.BaseViewState

sealed class MainViewEvent : BaseViewEvent {

}

data class MainViewState(
    val loading: Boolean = false
) : BaseViewState