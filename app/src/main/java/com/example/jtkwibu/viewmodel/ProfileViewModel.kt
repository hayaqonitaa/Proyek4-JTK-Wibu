package com.example.jtkwibu.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jtkwibu.data.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AnimeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _profileImagePath = MutableStateFlow<String?>(null)
    val profileImagePath = _profileImagePath.asStateFlow()

    init {
        viewModelScope.launch {
            _profileImagePath.value = repository.getProfileImagePath()
        }
    }

    fun setProfileImage(uri: Uri) {
        viewModelScope.launch {
            val file = File(context.filesDir, "profile_image_${System.currentTimeMillis()}.jpg") // Nama unik agar tidak cached
            context.contentResolver.openInputStream(uri)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            repository.saveProfileImagePath(file.absolutePath)

            kotlinx.coroutines.delay(100)

            _profileImagePath.value = file.absolutePath
        }
    }
}
