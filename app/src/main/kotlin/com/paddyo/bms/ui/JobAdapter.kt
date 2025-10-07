package com.paddyo.bms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paddyo.bms.data.entities.Job
import com.paddyo.bms.databinding.ItemJobBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JobAdapter(
    private val onClick: (Long) -> Unit
) : ListAdapter<Job, JobAdapter.JobViewHolder>(JobDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JobViewHolder(private val binding: ItemJobBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            binding.jobTitle.text = job.title
            binding.jobStatus.text = job.status
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.jobDate.text = dateFormat.format(Date(job.dateRequested))
            binding.root.setOnClickListener { onClick(job.id) }
        }
    }

    private class JobDiffCallback : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }
}