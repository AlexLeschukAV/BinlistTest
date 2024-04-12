package com.example.binlisttest.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.binlisttest.R
import com.example.binlisttest.databinding.FragmentArchiveBinding
import com.example.binlisttest.presentation.adapter.ArchiveAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchiveFragment : Fragment(R.layout.fragment_archive) {
    private lateinit var binding: FragmentArchiveBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter: ArchiveAdapter by lazy { ArchiveAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArchiveBinding.bind(view)
        binding.rwList.adapter = adapter
        val list = viewModel.getArchive()
        adapter.submitList(list)
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}