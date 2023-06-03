package com.mango.test_tech_project.presentation.sign_up_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mango.test_tech_project.R
import com.mango.test_tech_project.data.model.RegisterIn
import com.mango.test_tech_project.databinding.FragmentSignUpBinding
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding<FragmentSignUpBinding>()
    private val viewModel by viewModels<SignUpViewModel>()
    private val args by navArgs<SignUpFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tiEtPhoneNumber.setText(args.number)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isRegistered.onEach {
                when (it) {
                    is Resource.Success -> {
                        Log.d("isRegistered", "Success")
                        findNavController().navigate(R.id.action_signUpFragment_to_profileFragment)
                    }

                    is Resource.Loading -> {
                        Log.d("isRegistered", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("isRegistered", "Error: ${it.message}")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }.collect()
        }

        with(binding) {
            button.setOnClickListener {
                viewModel.signUp(
                    RegisterIn(
                        phone = tiEtPhoneNumber.text.toString(),
                        name = tiEtName.text.toString(),
                        username = tiEtUserName.text.toString()
                    )
                )
            }
        }

    }

}