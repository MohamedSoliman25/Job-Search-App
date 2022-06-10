package com.example.jobsearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.jobsearchapp.databinding.ActivityMainBinding
import com.example.jobsearchapp.db.FavouriteJobDatabase
import com.example.jobsearchapp.repository.RemoteJobRepository
import com.example.jobsearchapp.viewmodel.RemoteJobViewModel
import com.example.jobsearchapp.viewmodel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    lateinit var mainViewModel: RemoteJobViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val remoteJobRepository = RemoteJobRepository(FavouriteJobDatabase(this))

        val viewModelProviderFactory = RemoteJobViewModelFactory(
                application,
                remoteJobRepository
        )
        mainViewModel = ViewModelProvider(
                this,
                viewModelProviderFactory
        ).get(RemoteJobViewModel::class.java)
    }

}