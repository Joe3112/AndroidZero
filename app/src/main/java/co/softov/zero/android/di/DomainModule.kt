import co.softov.zero.android.domain.redux.AppState
import co.softov.zero.android.domain.redux.AppStore
import co.softov.zero.android.domain.redux.Store
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class StoreModule {

    @Singleton
    @Binds
    abstract fun bindAppStore(appStore: AppStore): Store<AppState>

}