package com.example.ocr_v2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import com.example.compose.Ocr_v2Theme

class MainActivity : ComponentActivity() {
    private var imageUri = mutableStateOf<Uri?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ocr_v2Theme {
                    Navigation()
            }
        }
    }
}




