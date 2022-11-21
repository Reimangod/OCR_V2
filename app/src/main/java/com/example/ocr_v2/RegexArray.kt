package com.example.ocr_v2
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.navigation.NavArgs
import kotlinx.serialization.Serializable

data class RegexArray(
    var nameArray: Array<String>,
    var perDayArray: Array<Int>,
    var perTimeArray: Array<Int>,
    var totalDayArray: Array<Int>
)