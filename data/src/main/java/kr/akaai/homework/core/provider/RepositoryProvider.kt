package kr.akaai.homework.core.provider

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.akaai.homework.core.repository.favorite_user.FavoriteUserRepositoryImpl
import kr.akaai.homework.core.repository.favorite_user.LocalFavoriteUserDataSource
import kr.akaai.homework.core.repository.github_api.GithubApiDataSource
import kr.akaai.homework.core.repository.github_api.GithubApiRepositoryImpl
import kr.akaai.homework.core.room.dao.FavoriteUserDao
import kr.akaai.homework.repository.FavoriteUserRepository
import kr.akaai.homework.repository.GithubApiRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider {
    @Provides
    @Singleton
    fun provideGithubApiRepository(githubApiDataSource: GithubApiDataSource): GithubApiRepository =
        GithubApiRepositoryImpl(githubApiDataSource)

    @Provides
    @Singleton
    fun provideGithubApiDataSource(retrofit: Retrofit): GithubApiDataSource =
        GithubApiDataSource(retrofit)

    @Provides
    @Singleton
    fun provideFavoriteUserRepository(localFavoriteUserRepository: LocalFavoriteUserDataSource): FavoriteUserRepository =
        FavoriteUserRepositoryImpl(localFavoriteUserRepository)

    @Provides
    @Singleton
    fun provideLocalFavoriteUserDataSource(dao: FavoriteUserDao): LocalFavoriteUserDataSource =
        LocalFavoriteUserDataSource(dao)
}