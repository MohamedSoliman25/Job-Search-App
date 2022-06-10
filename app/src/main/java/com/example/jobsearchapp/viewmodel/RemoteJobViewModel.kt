package com.example.jobsearchapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsearchapp.models.FavouriteJob
import com.example.jobsearchapp.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(
        app:Application,
        private val remoteJobRepository: RemoteJobRepository
        ) :AndroidViewModel(app){

            fun remoteJobResult() = remoteJobRepository.remoteJobResult()


    fun addFavJob(job:FavouriteJob) = viewModelScope.launch {
        remoteJobRepository.addFavouriteJob(job)
    }

    fun deleteJob(job:FavouriteJob) = viewModelScope.launch {
        remoteJobRepository.deleteJob(job)
    }

    fun getAllFavJobs() = remoteJobRepository.getAllFavJobs()

}