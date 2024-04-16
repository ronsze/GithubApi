package kr.akaai.homework.core.provider

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.akaai.homework.core.usecase.GetFollowerListUseCaseImpl
import kr.akaai.homework.core.usecase.GetUserInfoUseCaseImpl
import kr.akaai.homework.repository.GithubApiRepository
import kr.akaai.homework.usecase.GetFollowerListUseCase
import kr.akaai.homework.usecase.GetUserInfoUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseProvider {
    @Provides
    @Singleton
    fun providesGetFollowerList(repository: GithubApiRepository): GetFollowerListUseCase = GetFollowerListUseCaseImpl(repository)
    @Provides
    @Singleton
    fun providesGetUserInfoUseCase(repository: GithubApiRepository): GetUserInfoUseCase = GetUserInfoUseCaseImpl(repository)
}