package com.lazamelezi.moviesapp.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lazamelezi.moviesapp.domain.local.FavoriteMoviesEntity
import com.lazamelezi.moviesapp.domain.local.FavoriteMoviesEntityDao

@Database(entities = [FavoriteMoviesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMoviesDao(): FavoriteMoviesEntityDao
}