package com.mango.test_tech_project.presentation.sign_in_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mango.test_tech_project.R
import com.mango.test_tech_project.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            if (true) {
                findNavController().navigate(R.id.action_signInFragment_to_profileFragment)

            } else {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }
}