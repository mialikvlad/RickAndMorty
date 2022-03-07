package com.example.rickmorty.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    protected var _binding: VB? = null
    protected val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    open fun onViewCleared() {}

    abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): VB

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createBinding(inflater, container, savedInstanceState)
            .also {
                _binding = it
            }
            .root
    }

    final override fun onDestroyView() {
        super.onDestroyView()
        onViewCleared()
        _binding = null
    }

    protected fun handleError(error: Throwable) {
        handleError(error.message ?: GENERIC_ERROR_MESSAGE)
    }

    protected fun handleError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAction(android.R.string.ok) {}
            .show()
    }

    companion object {
        private const val GENERIC_ERROR_MESSAGE = "Something went wrong"
    }
}