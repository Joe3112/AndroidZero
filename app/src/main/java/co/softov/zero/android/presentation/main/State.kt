package co.softov.zero.android.presentation.main

import com.iiitech.operations.presentation.base.viewmodel.BaseViewEvent
import com.iiitech.operations.presentation.base.viewmodel.BaseViewState

sealed class MainViewEvent : BaseViewEvent {

}

data class MainViewState(
    val loading: Boolean = false
) : BaseViewState