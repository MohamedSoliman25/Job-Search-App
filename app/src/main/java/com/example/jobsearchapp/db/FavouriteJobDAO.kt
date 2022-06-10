package com.example.jobsearchapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jobsearchapp.models.FavouriteJob

@Dao
interface FavouriteJobDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteJob(job:FavouriteJob):Long

    @Query("SELECT * FROM fav_job ORDER BY id DESC ")
    fun getAllFavJob():LiveData<List<FavouriteJob>>
    @Delete
    suspend fun deleteFavouriteJob(job:FavouriteJob)

}