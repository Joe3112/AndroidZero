package com.iiitech.operations.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.iiitech.operations.presentation.base.router.AppRouter
import com.iiitech.operations.presentation.base.router.FlowRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class AppNavigation

@Qualifier
annotation class FlowNavigation

@Module
@InstallIn(ApplicationComponent::class)
object NavigationModule {

    private val appCicerone = Cicerone.create(AppRouter())
    private val flowCicerone = Cicerone.create(FlowRouter())

    @Singleton
    @Provides
    fun provideAppRouter(): AppRouter = appCicerone.router

    @AppNavigation
    @Singleton
    @Provides
    fun provideAppNavigationHolder(): NavigatorHolder = appCicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun provideFlowRouter(): FlowRouter = flowCicerone.router

    @FlowNavigation
    @Singleton
    @Provides
    fun provideFlowNavigationHolder(): NavigatorHolder = flowCicerone.getNavigatorHolder()

}
