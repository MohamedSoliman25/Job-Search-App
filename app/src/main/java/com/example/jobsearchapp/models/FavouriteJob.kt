package com.example.jobsearchapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Entity(tableName = "fav_job")
data class FavouriteJob(
        val candidateRequiredLocation: String?,
        val category: String?,
        val companyLogoUrl: String?,
        val companyName: String?,
        val description: String?,
        val jobId: Int?,
        val jobType: String?,
        val publicationDate: String?,
        val salary: String?,
        val title: String?,
        val url: String?
){
        @PrimaryKey(autoGenerate = true)
        var id:Int =0
}