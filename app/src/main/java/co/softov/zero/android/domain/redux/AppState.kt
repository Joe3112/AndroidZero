package co.softov.zero.android.domain.redux

import co.softov.zero.android.domain.redux.state.ZeroState

data class AppState(
    val state: ZeroState = ZeroState()
)