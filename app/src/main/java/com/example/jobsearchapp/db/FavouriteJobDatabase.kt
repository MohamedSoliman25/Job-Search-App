package com.example.jobsearchapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.jobsearchapp.models.FavouriteJob

@Database(entities = [FavouriteJob::class], version = 1)
abstract class FavouriteJobDatabase : RoomDatabase() {

    abstract fun getFavJobDao(): FavouriteJobDAO

    companion object {
        // i use Volatile for make instance visible to other threads
        @Volatile
        private var instance: FavouriteJobDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        FavouriteJobDatabase::class.java,
                        "remoteJob_db"
                ).build()
    }
}