package com.example.jobsearchapp.api

import com.example.jobsearchapp.models.RemoteJob
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteJobApi{
    @GET("remote-jobs?limit=5")
    fun getRemoteJobResponse(): Call<RemoteJob>

    @GET("remote-jobs")
    fun searchJob(@Query("search") query:String?):Call<RemoteJob>


}