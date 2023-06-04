package com.mango.test_tech_project.presentation.verify_screen

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
            viewModel.loginOut.onEach {
                when (it) {
                    is Resource.Success -> {
                        val loginOut = it.data

                        if (loginOut != null) {

                            if (loginOut.is_user_exists) {
                                viewModel.updateCurrentUser(loginOut.user_id)
                                findNavController().navigate(R.id.action_verifyFragment_to_navigation_profile)
                            } else {
                                findNavController().navigate(
                                    VerifyFragmentDirections.actionVerifyFragmentToSignUpFragment(
                                        args.number
                                    )
                                )
                            }


                        }
                    }

                    is Resource.Loading -> {
                        // TODO placeholder for loading
                        Log.d("VerifyLog", "Loading")
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        Log.d("VerifyLog", "Error: ${it.message}")
                    }

                }
            }.collect()
        }

        with(binding) {
            infoNumber.text = "Мы отправили код на номер ${args.number}"
            smsCodeView.onChangeListener =
                SmsConfirmationView.OnChangeListener { code, isComplete ->
                    if (isComplete) {
                        viewModel.verifyCode(CheckAuthCode(args.number, code))
                        val inputMethodManager =
                            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(smsCodeView.windowToken, 0)
                    }
                }
        }

    }
}