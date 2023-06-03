package com.mango.test_tech_project.presentation.verify_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fraggjkee.smsconfirmationview.SmsConfirmationView
import com.mango.test_tech_project.R
import com.mango.test_tech_project.data.model.CheckAuthCode
import com.mango.test_tech_project.databinding.FragmentVerifyBinding
import com.mango.test_tech_project.presentation.sign_in_screen.SignInFragmentDirections
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyFragment : Fragment(R.layout.fragment_verify) {

    private val binding by viewBinding<FragmentVerifyBinding>()
    private val viewModel by viewModels<VerifyViewModel>()
    private val args by navArgs<VerifyFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isUserExists.onEach {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            Log.d("VerifyLog", "Success")
                            findNavController().navigate(R.id.action_verifyFragment_to_navigation_profile)
                        } else {
                            val direction = VerifyFragmentDirections.actionVerifyFragmentToSignUpFragment(args.number)
                            findNavController().navigate(direction)
                        }
                    }

                    is Resource.Loading -> {
                        Log.d("VerifyLog", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("VerifyLog", "Error")
                    }

                }
            }.collect()
        }

        binding.smsCodeView.onChangeListener = SmsConfirmationView.OnChangeListener { code, isComplete ->
            if (isComplete) {
                viewModel.verifyCode(CheckAuthCode(args.number, code))
            }
        }

    }
}