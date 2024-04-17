package kr.akaai.homework.core.provider

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.akaai.homework.Constants.DATABASE_NAME
import kr.akaai.homework.core.room.database.HomeworkDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomProvider {
    @Provides
    @Singleton
    fun provideHomeworkDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            HomeworkDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: HomeworkDatabase) = database.favoriteUserDao()
}