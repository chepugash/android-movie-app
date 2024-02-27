package com.practice.core.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practice.core.common.db.dao.UserDao
import com.practice.core.common.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao() : UserDao
}
