package com.practice.core.common.di

import android.content.Context
import androidx.room.Room
import com.practice.core.common.db.AppDatabase
import com.practice.core.common.db.dao.UserDao
import org.koin.dsl.module

private const val DATABASE_NAME = "user_db"

val dbModule = module {
    single {
        provideDatabase(
            context = get()
        )
    }
    single {
        provideUserDao(
            database = get()
        )
    }
}

private fun provideDatabase(
    context: Context,
): AppDatabase =
    Room
        .databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
        .build()

private fun provideUserDao(
    database: AppDatabase
) : UserDao = database.getUserDao()
