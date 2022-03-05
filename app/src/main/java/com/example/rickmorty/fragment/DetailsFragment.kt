package com.example.rickmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.rickmorty.databinding.FragmentDetailsBinding
import com.example.rickmorty.model.RickInfo
import com.example.rickmorty.model.RickListInfo
import com.example.rickmorty.retorfit.RickService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    private val args: DetailsFragmentArgs by navArgs()

    private var currentRequest: Call<RickListInfo>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRickDetails()
        with(binding) {
            toolbar.setupWithNavController(findNavController())
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCleared() {
        //cancel current request if view was destroyed
        currentRequest?.cancel()
    }

    private fun loadRickDetails(){
        currentRequest = RickService.rickApi.getRickInfo(args.name)
            .enqueueRequest{ rickDetails ->
                with(binding){
                    imageViewRick.load(rickDetails.results[0].image)
                    nameCharacter.text = "${rickDetails.results[0].name}"
                    textViewStatus.text = "Status: ${rickDetails.results[0].status}"
                    textViewSpecies.text = "Species: ${rickDetails.results[0].species}"
                }
            }
    }

}