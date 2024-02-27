package com.practice.core.common.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.core.common.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT phone FROM users LIMIT 1")
    suspend fun getUser(): UserEntity?
}
