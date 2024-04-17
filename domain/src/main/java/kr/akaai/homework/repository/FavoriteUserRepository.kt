package kr.akaai.homework.repository

import kr.akaai.homework.model.favorite.FavoriteUserData

interface FavoriteUserRepository {
    suspend fun insertFavoriteUser(favoriteUser: FavoriteUserData)
    suspend fun getFavoriteUsers(): List<FavoriteUserData>
}