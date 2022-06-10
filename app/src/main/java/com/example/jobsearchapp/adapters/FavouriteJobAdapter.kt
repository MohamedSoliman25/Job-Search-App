package com.example.jobsearchapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobsearchapp.databinding.JobItemBinding
import com.example.jobsearchapp.fragments.MainFragmentDirections
import com.example.jobsearchapp.models.FavouriteJob
import com.example.jobsearchapp.models.Job

class FavouriteJobAdapter constructor(private val itemClick: ItemClick): RecyclerView.Adapter<FavouriteJobAdapter.RemoteJobViewHolder>() {

    private var binding:JobItemBinding ?=null


     class RemoteJobViewHolder(itemBinding: JobItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :
    DiffUtil.ItemCallback<FavouriteJob>(){
        override fun areItemsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
            return oldItem.id ==newItem.id
        }

        override fun areContentsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        binding = JobItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
        )
        return RemoteJobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {

        val currentJob = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                    .load(currentJob.companyLogoUrl)
                    .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType
            binding?.ibDelete?.visibility = View.VISIBLE

            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = dateJob?.get(0)
        }.setOnClickListener { mView->

            val tags = arrayListOf<String>()
            val job = Job(
                    currentJob.candidateRequiredLocation, currentJob.category,
                    currentJob.companyLogoUrl, currentJob.companyName,
                    currentJob.description, currentJob.jobId,
                    currentJob.jobType, currentJob.publicationDate,
                    currentJob.salary,tags, currentJob.title,currentJob.url
            )
            val direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(job)
            mView.findNavController().navigate(direction)
        }
        holder.itemView.apply{
            binding?.ibDelete?.setOnClickListener {
                itemClick.onItemClick(currentJob,binding?.ibDelete!!,position)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface  ItemClick {
        fun onItemClick(job:FavouriteJob,
        view:View,
        position: Int)
    }
}