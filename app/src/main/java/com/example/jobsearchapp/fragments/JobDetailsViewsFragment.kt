package com.example.jobsearchapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.FragmentJobDetailsViewsBinding
import com.example.jobsearchapp.models.Job


class JobDetailsViewsFragment : Fragment(R.layout.fragment_job_details_views) {

    private  var _binding:FragmentJobDetailsViewsBinding? = null
    private val binding get() = _binding!!
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
        currentJob = args.job!!

        setUpWebView()
    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let {
                loadUrl(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}