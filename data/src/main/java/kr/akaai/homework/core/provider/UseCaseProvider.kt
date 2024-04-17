package kr.akaai.homework.core.provider

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.akaai.homework.core.usecase.favorite_user.GetFavoriteUsersUseCaseImpl
import kr.akaai.homework.core.usecase.favorite_user.InsertFavoriteUserUseCaseImpl
import kr.akaai.homework.core.usecase.github_api.GetFollowerListUseCaseImpl
import kr.akaai.homework.core.usecase.github_api.GetUserInfoUseCaseImpl
import kr.akaai.homework.repository.FavoriteUserRepository
import kr.akaai.homework.repository.GithubApiRepository
import kr.akaai.homework.usecase.favorite_user.GetFavoriteUsersUseCase
import kr.akaai.homework.usecase.favorite_user.InsertFavoriteUserUseCase
import kr.akaai.homework.usecase.github_api.GetFollowerListUseCase
import kr.akaai.homework.usecase.github_api.GetUserInfoUseCase
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

    @Provides
    @Singleton
    fun providesGetFavoriteUsersUseCase(repository: FavoriteUserRepository): GetFavoriteUsersUseCase = GetFavoriteUsersUseCaseImpl(repository)
    @Provides
    @Singleton
    fun providesInsertFavoriteUserUseCase(repository: FavoriteUserRepository): InsertFavoriteUserUseCase = InsertFavoriteUserUseCaseImpl(repository)
}