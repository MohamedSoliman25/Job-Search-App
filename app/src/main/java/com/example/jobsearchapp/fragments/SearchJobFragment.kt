package com.example.jobsearchapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MainActivity
import com.example.jobsearchapp.R
import com.example.jobsearchapp.adapters.RemoteJobAdapter
import com.example.jobsearchapp.databinding.FragmentSavedJobBinding
import com.example.jobsearchapp.databinding.FragmentSearchJobBinding
import com.example.jobsearchapp.viewmodel.RemoteJobViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchJobFragment : Fragment(R.layout.fragment_search_job) {


    private var _binding:FragmentSearchJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var jobAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchJobBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Shared view model
        viewModel = (activity as MainActivity).mainViewModel
        searchJob()
        setUpRecyclerView()
    }

    private fun searchJob() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener{text->
            job?.cancel()
            job = MainScope().launch {
               delay(500L)
                text?.let {
                    if (text.toString().isNotEmpty()){
                        viewModel.searchRemoteJob(text.toString())
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        jobAdapter = RemoteJobAdapter()
        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = jobAdapter
        }
        viewModel.searchResult().observe(viewLifecycleOwner,{remoteJob->
            jobAdapter.differ.submitList(remoteJob.jobs)
        }
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}