package com.paddyo.bms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paddyo.bms.databinding.FragmentSettingsBinding
import com.paddyo.bms.viewmodels.BusinessProfileViewModel
import com.paddyo.bms.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val businessProfileViewModel: BusinessProfileViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    private val backupLocationLauncher = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        uri?.let { settingsViewModel.setBackupLocation(it.toString()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectBackupLocationButton.setOnClickListener {
            backupLocationLauncher.launch(null)
        }
        binding.saveBusinessProfileButton.setOnClickListener {
            // TODO: Save business profile
        }
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setDarkMode(isChecked)
        }
    }

    override onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}