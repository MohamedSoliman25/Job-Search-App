package com.example.jobsearchapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MainActivity
import com.example.jobsearchapp.R
import com.example.jobsearchapp.adapters.FavouriteJobAdapter
import com.example.jobsearchapp.databinding.FragmentJobDetailsViewsBinding
import com.example.jobsearchapp.databinding.FragmentSavedJobBinding
import com.example.jobsearchapp.models.FavouriteJob
import com.example.jobsearchapp.viewmodel.RemoteJobViewModel


class SavedJobFragment : Fragment(R.layout.fragment_saved_job),FavouriteJobAdapter.ItemClick {

    private val TAG = "SavedJobFragment"
    private  var _binding: FragmentSavedJobBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel:RemoteJobViewModel
    private lateinit var favAdapter: FavouriteJobAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedJobBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).mainViewModel
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        favAdapter = FavouriteJobAdapter(this)
        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object :DividerItemDecoration(
                    activity,LinearLayout.VERTICAL) {})
            adapter = favAdapter
        }
        viewModel.getAllFavJobs().observe(viewLifecycleOwner, { jobToSave ->
            favAdapter.differ.submitList(jobToSave)
            updateUI(jobToSave)
        })
    }

    private fun updateUI(favJob: List<FavouriteJob>) {

        if (favJob.isNotEmpty()){
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        }
        else{
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(job: FavouriteJob, view: View, position: Int) {
        deleteJob(job)
    }

    private fun deleteJob(job: FavouriteJob) {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Job")
            setMessage("Are you want to permanently delete this job?")
            setPositiveButton("DELETE"){_,_->
                viewModel.deleteJob(job)
                Toast.makeText(activity,"job deleted",Toast.LENGTH_LONG).show()
            }
            setNegativeButton("CANCEL",null)
        }.create().show()
    }

}