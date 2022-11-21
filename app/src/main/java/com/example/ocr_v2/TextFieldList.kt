package com.example.ocr_v2

import android.content.res.TypedArray
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.Ocr_v2Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TextFieldList(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val pillList = mutableListOf<RegexData>(
        RegexData("Panadol", 1, 2, 7),
        RegexData("Decolgen", 1, 2, 7),
        RegexData("Khang", 1, 2, 7))

    Column(horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .padding(20.dp)
                .wrapContentWidth(),
            onClick = {
                navController.navigate(Screen.MainScreen.route)

            },
        ) {
            Text(text = "Return to MainScreen")
        }
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            items(
                items = pillList,
            ) { task ->
                TextFieldItem(task.name, task.perDay, task.perTime, task.totalDay)
            }
        }
    }
}

//
//@Preview(showBackground = true, widthDp = 320)
//@Composable
//fun DefaultFieldPreview() {
//    Ocr_v2Theme {
//        TextFieldList()
//    }
//}
