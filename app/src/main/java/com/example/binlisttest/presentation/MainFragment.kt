package com.example.binlisttest.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.binlisttest.R
import com.example.binlisttest.databinding.FragmentMainBinding
import com.example.binlisttest.domain.model.BinlistData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding.button.setOnClickListener {
            clearField()
            val code = binding.binEditText.text.toString()
            if (code.isNotEmpty()) viewModel.getData(code) else showDialog("Code is empty")
        }

        binding.button2.setOnClickListener {
            binding.binEditText.setText("")
            findNavController().navigate(R.id.action_mainFragment_to_archiveFragment)
        }
        viewModel.dataCard.observe(viewLifecycleOwner) {
            bind(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                showDialog(it)
                viewModel.setError()
            }
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state) {
                viewModel.getCard()?.let {
                    val card = it.copy(request = binding.binEditText.text.toString())
                    viewModel.saveCard(card)
                }
                viewModel.setStatus(false)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            when (isLoading) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun bind(data: BinlistData) = with(binding) {
        country.text = data.country?.name
        typeCard.text = data.scheme
        phoneCard.text = data.bank?.phone
        urlCard.text = data.bank?.url
        val lat_long = "lat: ${data.country?.latitude}   long:${data.country?.longitude}"
        coordinateCard.text = lat_long
    }

    private fun clearField() = with(binding) {
        country.text = ""
        typeCard.text = ""
        phoneCard.text = ""
        urlCard.text = ""
        coordinateCard.text = ""
    }

    private fun showDialog(e: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.error))
        builder.setMessage(e)
        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}