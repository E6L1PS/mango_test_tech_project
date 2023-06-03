package com.mango.test_tech_project.presentation.edit_profile_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mango.test_tech_project.R
import com.mango.test_tech_project.data.model.Avatar
import com.mango.test_tech_project.data.model.UserUpdate
import com.mango.test_tech_project.databinding.FragmentEditProfileBinding
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val binding by viewBinding<FragmentEditProfileBinding>()
    private val viewModel by viewModels<EditProfileViewModel>()
    private val args by navArgs<EditProfileFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userUpdateSend.onEach {
                when (it) {
                    is Resource.Success -> {
                        Log.d("userUpdateSendLog", "Success")
                        findNavController().navigate(R.id.action_editProfileFragment_to_navigation_profile)
                    }

                    is Resource.Loading -> {
                        Log.d("userUpdateSendLog", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("userUpdateSendLog", "Error: ${it.message}")

                    }
                }
            }.collect()
        }

        with(binding) {
            button.setOnClickListener {
                viewModel.updateUserInfo(
                    UserUpdate(
                        avatar = Avatar(
                            "dasda",
                            "dadsad"
                        ),
                        name = tiEtName.text.toString(),
                        birthday = tiEtBirthday.text.toString(),
                        city = tiEtLocation.text.toString(),
                        instagram = tiEtInstagram.text.toString(),
                        vk = tiEtVk.text.toString(),
                        status = "dsad",
                        username = args.username
                    )
                )
            }
        }

    }


}