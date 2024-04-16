package kr.akaai.homework.core.provider

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.akaai.homework.core.util.FavoriteUserModule
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleProvider {
    @Provides
    @Singleton
    fun provideFavoriteUserModule(@ApplicationContext context: Context, sharedPreferences: SharedPreferences): FavoriteUserModule {
        return FavoriteUserModule(context, sharedPreferences)
    }
}