package com.example.rickmorty.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.rickmorty.databinding.FragmentDetailsBinding
import com.example.rickmorty.presentation.extensions.applyInsetsWithAppBar
import com.example.rickmorty.presentation.model.LceState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel by viewModel<DetailsViewModel> {
        parametersOf(args.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setupWithNavController(findNavController())
            viewModel
                .rickDetailsFlow
                .onEach { lceState ->
                    when (lceState) {
                        is LceState.Content -> {
                            val rickDetails = lceState.value
                            imageViewRick.load(rickDetails.image)
                            nameCharacter.text = rickDetails.name
                            status.text = rickDetails.status
                            species.text = rickDetails.species
                        }
                        is LceState.Error -> {
                            Snackbar.make(
                                root,
                                lceState.throwable.localizedMessage ?: "Smth went wrong",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            appBar.applyInsetsWithAppBar { view, insets ->
                view.updatePadding(left = insets.left, right = insets.right, top = insets.top)
                insets
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}