package com.example.jobsearchapp.api

import com.example.jobsearchapp.models.RemoteJob
import retrofit2.Call
import retrofit2.http.GET

interface RemoteJobApi{
    @GET("remote-jobs")
    fun getRemoteJobResponse(): Call<RemoteJob>
}