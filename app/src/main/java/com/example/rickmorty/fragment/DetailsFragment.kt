package com.example.rickmorty.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.rickmorty.databinding.FragmentDetailsBinding
import com.example.rickmorty.provider.ServiceProvider
import com.example.rickmorty.viewModel.DetailsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailsViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(
                    rickApi = ServiceProvider.provideRickApi(),
                    name = args.name
                ) as T
            }

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.setupWithNavController(findNavController())
            viewModel
                .rickDetailsFlow
                .onEach { rickListInfo ->
                    imageViewRick.load(rickListInfo.results[0].image)
                    nameCharacter.text = rickListInfo.results[0].name
                    textViewStatus.text = "Status: ${rickListInfo.results[0].status}"
                    textViewSpecies.text = "Species: ${rickListInfo.results[0].species}"
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(inflater, container, false)
    }
}