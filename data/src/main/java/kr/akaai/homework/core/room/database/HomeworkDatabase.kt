package kr.akaai.homework.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.akaai.homework.core.room.dao.FavoriteUserDao
import kr.akaai.homework.core.room.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1)
abstract class HomeworkDatabase: RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}