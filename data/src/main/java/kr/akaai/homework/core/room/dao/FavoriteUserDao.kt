package kr.akaai.homework.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kr.akaai.homework.core.room.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Insert
    fun insertFavoriteUser(user: FavoriteUserEntity)

    @Query("SELECT * FROM FavoriteUser")
    fun getFavoriteUsers(): List<FavoriteUserEntity>
}