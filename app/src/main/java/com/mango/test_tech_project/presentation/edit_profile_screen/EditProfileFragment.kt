package com.mango.test_tech_project.presentation.edit_profile_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.mango.test_tech_project.R
import com.mango.test_tech_project.data.model.Avatar
import com.mango.test_tech_project.data.model.UserUpdate
import com.mango.test_tech_project.databinding.FragmentEditProfileBinding
import com.mango.test_tech_project.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val binding by viewBinding<FragmentEditProfileBinding>()
    private val viewModel by viewModels<EditProfileViewModel>()
    private val args by navArgs<EditProfileFragmentArgs>()
    private var uriImage: Uri? = null

    private val pickImagesLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                uriImage = uri
                Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .into(binding.ivAvatar)
            }
        }

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
                        // TODO placeholder for loading
                        Log.d("userUpdateSendLog", "Loading")
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        Log.d("userUpdateSendLog", "Error: ${it.message}")

                    }
                }
            }.collect()
        }

        with(binding) {
            cvAvatar.setOnClickListener {
                selectImagesFromGallery()
            }

            button.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    if (uriImage == null) {
                        Toast.makeText(requireContext(), "Выберите аватар", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        viewModel.updateUserInfo(
                            UserUpdate(
                                avatar = Avatar(
                                    readBytesAndEncodeToBase64(uriImage!!),
                                    getFileName(uriImage!!)
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


    }

    private fun selectImagesFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickImagesLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private suspend fun readBytesAndEncodeToBase64(uri: Uri): String = withContext(Dispatchers.IO) {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        inputStream.use {
            Base64.encodeToString(it!!.readBytes(), Base64.DEFAULT)
        }
    }

    private fun getFileName(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor?.moveToFirst()
        val name = cursor?.getString(nameIndex ?: 0)
        cursor?.close()
        Log.d("fileName", "$name")
        return name ?: ""
    }

}