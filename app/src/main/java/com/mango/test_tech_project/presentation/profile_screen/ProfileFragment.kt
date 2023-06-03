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
import java.time.LocalDate

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
                        val localDate = LocalDate.parse(it.birthday)
                        tvFullName.text = it.name
                        tvId.text = "Пользователь номер ${it.id}"
                        tvEmail.text = it.username
                        tvPhone.text = it.phone
                        tvLocation.text = it.city
                        tvCalendar.text = it.created
                        tvBirthday.text = it.birthday
                        ivZodiac.setImageResource(determineZodiacSign(localDate.dayOfMonth, localDate.monthValue))
                    }

                } else {
                    Log.d("InfoProfileLog", "update")
                    viewModel.uploadProfileInfoUseCase()
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