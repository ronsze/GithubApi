package kr.akaai.homework.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("FavoriteUser")
data class FavoriteUserEntity(
    @PrimaryKey val userId: String,
    val profileImageUrl: String
)