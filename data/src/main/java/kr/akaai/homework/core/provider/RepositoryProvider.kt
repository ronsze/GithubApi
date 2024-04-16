package kr.akaai.homework.core.provider

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.akaai.homework.core.repository.github_api.GithubApiDataSource
import kr.akaai.homework.repository.GithubApiRepository
import kr.akaai.homework.core.repository.github_api.GithubApiRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvider {
    @Provides
    @Singleton
    fun provideGithubApiRepository(githubApiDataSource: GithubApiDataSource): GithubApiRepository {
        return GithubApiRepositoryImpl(githubApiDataSource)
    }
}