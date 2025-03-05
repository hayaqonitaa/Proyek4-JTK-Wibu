package com.example.jtkwibu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jtkwibu.ui.MainScreen
import com.example.jtkwibu.ui.theme.JtkWibuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JtkWibuTheme {
                MainScreen()
            }
        }
    }
}
