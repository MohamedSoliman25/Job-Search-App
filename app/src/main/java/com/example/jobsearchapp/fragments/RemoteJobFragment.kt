package com.example.jobsearchapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MainActivity
import com.example.jobsearchapp.R
import com.example.jobsearchapp.adapters.RemoteJobAdapter
import com.example.jobsearchapp.databinding.FragmentMainBinding
import com.example.jobsearchapp.databinding.FragmentRemoteJobBinding
import com.example.jobsearchapp.viewmodel.RemoteJobViewModel


class RemoteJobFragment : Fragment(R.layout.fragment_remote_job) {

    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemoteJobBinding.inflate(
                inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //shared View Model
        viewModel =(activity as MainActivity).mainViewModel

        setUpRecyclerView()
    }
    private fun setUpRecyclerView(){
        remoteJobAdapter = RemoteJobAdapter()
        binding.rvRemoteJobs.apply{
            layoutManager  = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object: DividerItemDecoration(activity,LinearLayout.VERTICAL){})
            adapter = remoteJobAdapter
        }
        fetchingData()
    }

    private fun fetchingData() {
        viewModel.remoteJobResult().observe(viewLifecycleOwner,{remotJob->
            if (remotJob!=null){
                remoteJobAdapter.differ.submitList(remotJob.jobs)
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}