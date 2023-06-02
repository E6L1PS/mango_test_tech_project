package com.mango.test_tech_project.presentation.profile_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mango.test_tech_project.R
import com.mango.test_tech_project.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding<FragmentProfileBinding>()
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileInfo.onEach {
                if (it != null) {
                    Log.d("InfoProfileLog", it.toString())
                    with(binding.infoPerson) {
                        tvId.text = it.id.toString()
                        tvFullName.text = it.username
                        tvPhone.text = it.phone
                        tvLocation.text = it.city
                        tvCalendar.text = it.created
                    }

                } else {
                    Log.d("InfoProfileLog", "update")
                    viewModel.uploadProfileInfoUseCase()
                }

            }.collect()
        }
    }

}