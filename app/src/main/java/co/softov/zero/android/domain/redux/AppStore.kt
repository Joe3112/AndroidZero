package co.softov.zero.android.domain.redux


import co.softov.zero.android.domain.redux.middleware.LoggerMiddleware
import javax.inject.Inject
import javax.inject.Singleton

@JvmSuppressWildcards
@Singleton
class AppStore
@Inject constructor(middleware: AppStoreMiddleware) :
    SimpleStore<AppState>(AppState(), reducers, middleware.list)

val reducers = listOf<Reducer<AppState>>(

)

class AppStoreMiddleware @Inject constructor(
    logger: LoggerMiddleware,
) {
    // IMPORTANT: The order of the elements dictates the order in which the middleware is called.
    val list = listOf(logger)
}