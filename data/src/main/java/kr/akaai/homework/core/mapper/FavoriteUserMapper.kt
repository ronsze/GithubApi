package kr.akaai.homework.core.mapper

import kr.akaai.homework.core.room.entity.FavoriteUserEntity
import kr.akaai.homework.model.favorite.FavoriteUserData

object FavoriteUserMapper {
    fun FavoriteUserEntity.toData() = FavoriteUserData(userId, profileImageUrl)
    fun FavoriteUserData.toEntity() = FavoriteUserEntity(userId, profileImageUrl)
}