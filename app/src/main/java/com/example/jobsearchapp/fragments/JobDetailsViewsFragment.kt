package com.example.jobsearchapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.jobsearchapp.MainActivity
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.FragmentJobDetailsViewsBinding
import com.example.jobsearchapp.models.FavouriteJob
import com.example.jobsearchapp.models.Job
import com.example.jobsearchapp.viewmodel.RemoteJobViewModel
import com.google.android.material.snackbar.Snackbar


class JobDetailsViewsFragment : Fragment(R.layout.fragment_job_details_views) {
    private val TAG = "JobDetailsViewsFragment"

    private  var _binding:FragmentJobDetailsViewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel:RemoteJobViewModel
    private lateinit var currentJob:Job
    private val args:JobDetailsViewsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentJobDetailsViewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Shared view model
        viewModel = (activity as MainActivity).mainViewModel

        currentJob = args.job!!

        setUpWebView()

        binding.fabAddFavorite.setOnClickListener{
            addFavJob(view)
        }
    }

    private fun addFavJob(view: View) {

        val job = FavouriteJob(
                currentJob.candidateRequiredLocation, currentJob.category,
                currentJob.companyLogoUrl, currentJob.companyName,
                currentJob.description, currentJob.id,
                currentJob.jobType, currentJob.publicationDate,
                currentJob.salary, currentJob.title,currentJob.url
        )

        viewModel.addFavJob(job)
        Snackbar.make(view,"Job Saved Successfully",Snackbar.LENGTH_LONG).show()
        Log.d(TAG, "addFavJob: ${job.id} : ${job.companyName}")
//        Log.d(TAG, "addFavJob: ${currentJob.id} : ${currentJob.companyName}")


    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}