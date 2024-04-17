package kr.akaai.homework.core.repository.favorite_user

import kr.akaai.homework.core.mapper.FavoriteUserMapper.toData
import kr.akaai.homework.core.mapper.FavoriteUserMapper.toEntity
import kr.akaai.homework.core.room.dao.FavoriteUserDao
import kr.akaai.homework.model.favorite.FavoriteUserData
import kr.akaai.homework.repository.FavoriteUserRepository
import javax.inject.Inject

class LocalFavoriteUserDataSource @Inject constructor(
    private val favoriteUserDao: FavoriteUserDao
): FavoriteUserRepository {
    override suspend fun insertFavoriteUser(favoriteUser: FavoriteUserData) = favoriteUserDao.insertFavoriteUser(favoriteUser.toEntity())
    override suspend fun getFavoriteUsers(): List<FavoriteUserData> = favoriteUserDao.getFavoriteUsers().map { it.toData() }
}