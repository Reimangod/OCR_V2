package com.example.ocr_v2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.Ocr_v2Theme
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldItem(
    name: String,
    perDay: Int,
    perTime: Int,
    total: Int,
    modifier:Modifier = Modifier) {
    var perDayText by remember { mutableStateOf(TextFieldValue("$perDay")) }
    var perTimeText by remember { mutableStateOf(TextFieldValue("$perTime")) }
    var totalText by remember { mutableStateOf(TextFieldValue("$total")) }
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, modifier = Modifier.padding(8.dp))
        TextField(
            value = perDayText,
            onValueChange = {
                perDayText = it
            },
            label = { Text(text = "Dose per day") },
            modifier = Modifier.padding(4.dp)
        )
        TextField(
            value = perTimeText,
            onValueChange = {
                perTimeText = it
            },
            label = { Text(text = "Dose per time") },
            modifier = Modifier.padding(4.dp)
        )
        TextField(
            value = totalText,
            onValueChange = {
                totalText = it
            },
            label = { Text(text = "Total day") },
            modifier = Modifier.padding(4.dp)
        )
    }
}

val exampleData: RegexData = RegexData("Panadol",2, 1, 7)

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    Ocr_v2Theme {
        TextFieldItem(exampleData.name, exampleData.perDay, exampleData.perTime, exampleData.totalDay)
    }
}

