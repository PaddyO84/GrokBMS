package com.paddyo.bms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paddyo.bms.databinding.FragmentCustomerProfileBinding
import com.paddyo.bms.viewmodels.JobViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

@AndroidEntryPoint
class CustomerProfileFragment : Fragment() {
    private var _binding: FragmentCustomerProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JobViewModel by viewModels()
    private val args: CustomerProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = JobAdapter { jobId ->
            // TODO: Navigate to job details
        }
        binding.jobRecyclerView.apply {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            this.adapter = adapter
        }

        lifecycleScope.launch {
            viewModel.getJobsForCustomer(args.customerId).collectLatest { jobs ->
                adapter.submitList(jobs)
            }
        }

        binding.addJobButton.setOnClickListener {
            val action = CustomerProfileFragmentDirections.actionCustomerProfileFragmentToNewJobFragment(args.customerId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}