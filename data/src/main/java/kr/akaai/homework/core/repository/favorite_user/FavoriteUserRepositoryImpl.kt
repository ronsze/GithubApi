package kr.akaai.homework.core.repository.favorite_user

import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.repository.FavoriteUserRepository
import javax.inject.Inject

class FavoriteUserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalFavoriteUserDataSource
): FavoriteUserRepository {
    override suspend fun insertFavoriteUser(favoriteUser: FavoriteUserData) = localDataSource.insertFavoriteUser(favoriteUser)
    override suspend fun getFavoriteUsers(): List<FavoriteUserData> = localDataSource.getFavoriteUsers()
}