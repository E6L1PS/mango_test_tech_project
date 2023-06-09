package com.mango.test_tech_project.presentation.profile_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.mango.test_tech_project.R
import com.mango.test_tech_project.databinding.FragmentProfileBinding
import com.mango.test_tech_project.util.Constants
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding<FragmentProfileBinding>()
    private val viewModel by viewModels<ProfileViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            infoPerson.btnRefactorInfo.setOnClickListener {
                val direction =
                    ProfileFragmentDirections.actionNavigationProfileToEditProfileFragment(infoPerson.tvEmail.text.toString())
                findNavController().navigate(direction)
            }
            btnLogout.setOnClickListener {
                viewModel.clearTokens()
                findNavController().navigate(R.id.action_navigation_profile_to_signInFragment)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileInfo.onEach {
                when (it) {
                    is Resource.Success -> {
                        val userInfo = it.data
                        if (userInfo != null) {
                            Log.d("InfoProfileLog", it.toString())
                            with(binding.infoPerson) {

                                Glide.with(this@ProfileFragment)
                                    .load(Constants.BASE_URL + userInfo.avatar)
                                    .error(R.drawable.user)
                                    .centerCrop()
                                    .into(ivAvatar)

                                val localDate = LocalDate.parse(userInfo?.birthday ?: "2001-01-01")
                                tvFullName.text = userInfo?.name ?: ""
                                tvId.text = "Пользователь номер ${userInfo?.id ?: ""}"
                                tvEmail.text = userInfo?.username ?: ""
                                tvPhone.text = userInfo?.phone ?: ""
                                tvLocation.text = userInfo?.city ?: ""
                                tvCalendar.text = userInfo?.created ?: ""
                                tvBirthday.text = userInfo?.birthday ?: ""
                                tvInfo.text = getString(
                                    R.string.info,
                                    userInfo?.vk ?: "",
                                    userInfo?.instagram ?: "",
                                    userInfo?.status ?: ""
                                )
                                ivZodiac.setImageResource(
                                    determineZodiacSign(
                                        localDate.dayOfMonth,
                                        localDate.monthValue
                                    )
                                )
                            }

                        } else {
                            Log.d("InfoProfileLog", "update")
                            viewModel.uploadProfileInfoUseCase()
                        }
                    }

                    is Resource.Loading -> {
                        // TODO placeholder for loading
                        Log.d("InfoProfileLog", "Loading ${it.data}")
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        Log.d("InfoProfileLog", "Error ${it.message}")
                    }

                }


            }.collect()
        }
    }

    private fun determineZodiacSign(day: Int, month: Int) = when (month) {
        1 -> {
            if (day <= 19) {
                R.drawable.zodiac_10
            } else {
                R.drawable.zodiac_11
            }
        }
        2 -> {
            if (day <= 18) {
                R.drawable.zodiac_11
            } else {
                R.drawable.zodiac_12
            }
        }
        3 -> {
            if (day <= 20) {
                R.drawable.zodiac_12
            } else {
                R.drawable.zodiac_1
            }
        }
        4 -> {
            if (day <= 19) {
                R.drawable.zodiac_1
            } else {
                R.drawable.zodiac_2
            }
        }
        5 -> {
            if (day <= 20) {
                R.drawable.zodiac_2
            } else {
                R.drawable.zodiac_3
            }
        }
        6 -> {
            if (day <= 20) {
                R.drawable.zodiac_3
            } else {
                R.drawable.zodiac_4
            }
        }
        7 -> {
            if (day <= 22) {
                R.drawable.zodiac_4
            } else {
                R.drawable.zodiac_5
            }
        }
        8 -> {
            if (day <= 22) {
                R.drawable.zodiac_5
            } else {
                R.drawable.zodiac_6
            }
        }
        9 -> {
            if (day <= 22) {
                R.drawable.zodiac_6
            } else {
                R.drawable.zodiac_7
            }
        }
        10 -> {
            if (day <= 22) {
                R.drawable.zodiac_7
            } else {
                R.drawable.zodiac_8
            }
        }
        11 -> {
            if (day <= 21) {
                R.drawable.zodiac_8
            } else {
                R.drawable.zodiac_9
            }
        }
        12 -> {
            if (day <= 21) {
                R.drawable.zodiac_9
            } else {
                R.drawable.zodiac_10
            }
        }
        else -> {
            R.drawable.person_ic
        }
    }

}