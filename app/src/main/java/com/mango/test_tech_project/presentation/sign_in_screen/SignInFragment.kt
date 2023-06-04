package com.mango.test_tech_project.presentation.sign_in_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mango.test_tech_project.R
import com.mango.test_tech_project.data.model.PhoneBase
import com.mango.test_tech_project.databinding.FragmentSignInBinding
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>()
    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isSuccess.onEach {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            Log.d("SignInLog", "Success")
                            val direction =
                                SignInFragmentDirections.actionSignInFragmentToVerifyFragment(
                                    binding.tiEtPhoneNumber.text.toString()
                                )
                            findNavController().navigate(direction)
                        } else {

                        }
                    }

                    is Resource.Loading -> {
                        // TODO placeholder for loading
                        Log.d("SignInLog", "Loading")
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        Log.d("SignInLog", "Error")
                    }

                }
            }.collect()
        }


        binding.button.setOnClickListener {
            viewModel.signIn(PhoneBase(binding.tiEtPhoneNumber.text.toString()))
        }
    }
}