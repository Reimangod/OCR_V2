package com.example.ocr_v2

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object PillScreen : Screen("pill_screen")
}