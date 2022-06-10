package com.example.jobsearchapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jobsearchapp.api.RetrofitInstance
import com.example.jobsearchapp.db.FavouriteJobDatabase
import com.example.jobsearchapp.models.FavouriteJob
import com.example.jobsearchapp.models.RemoteJob
import com.example.jobsearchapp.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository(private val db:FavouriteJobDatabase) {

    private val remoteJobService = RetrofitInstance.API_SERVICE
    private val remoteJobResponseLiveData:MutableLiveData<RemoteJob> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse(){

        remoteJobService.getRemoteJobResponse().enqueue(
                object : Callback<RemoteJob> {
                    override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                        remoteJobResponseLiveData.postValue(response.body())
                    }

                    override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                        remoteJobResponseLiveData.postValue(null)
                        Log.d(TAG, "onFailure: ${t.message}")
                    }

                }
        )
    }

    fun remoteJobResult():LiveData<RemoteJob>{
        return remoteJobResponseLiveData
    }

    suspend fun addFavouriteJob(job:FavouriteJob) = db.getFavJobDao().addFavouriteJob(job)
    suspend fun deleteJob(job:FavouriteJob) =db.getFavJobDao().deleteFavouriteJob(job)
    fun getAllFavJobs() = db.getFavJobDao().getAllFavJob()

}