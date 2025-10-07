package com.paddyo.bms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paddyo.bms.data.entities.Job
import com.paddyo.bms.databinding.FragmentNewJobBinding
import com.paddyo.bms.viewmodels.JobViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewJobFragment : Fragment() {
    private var _binding: FragmentNewJobBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JobViewModel by viewModels()
    private val args: NewJobFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveJobButton.setOnClickListener {
            val title = binding.jobTitleInput.text.toString()
            if (title.isNotEmpty()) {
                viewModel.addJob(Job(
                    customerId = args.customerId,
                    title = title,
                    dateRequested = System.currentTimeMillis(),
                    status = "Pending"
                ))
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}