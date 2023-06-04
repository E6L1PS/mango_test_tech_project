package com.mango.test_tech_project.presentation.splash_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mango.test_tech_project.R
import com.mango.test_tech_project.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding<FragmentSplashBinding>()
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.hide()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pbSplash.visibility = View.VISIBLE

        view.postDelayed({
            if (viewModel.isAuthenticated()) {
                findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_navigation_profile)
            }
        }, 500)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.show()
    }
}