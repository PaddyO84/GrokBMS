package com.paddyo.bms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paddyo.bms.databinding.FragmentJobListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobListFragment : Fragment() {
    private var _binding: FragmentJobListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Implement job list logic
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}