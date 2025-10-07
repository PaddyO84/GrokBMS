package com.paddyo.bms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paddyo.bms.databinding.FragmentCustomerListBinding
import com.paddyo.bms.viewmodels.CustomerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

@AndroidEntryPoint
class CustomerListFragment : Fragment() {
    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CustomerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CustomerAdapter { customerId ->
            val action = CustomerListFragmentDirections.actionCustomerListFragmentToCustomerProfileFragment(customerId)
            findNavController().navigate(action)
        }
        binding.customerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        lifecycleScope.launch {
            viewModel.customers.collectLatest { customers ->
                adapter.submitList(customers)
            }
        }

        binding.addCustomerButton.setOnClickListener {
            // TODO: Navigate to add customer screen
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}