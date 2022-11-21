package com.example.ocr_v2

import android.content.res.TypedArray
import android.media.Image
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun MainScreen(context: ComponentActivity, navController: NavController) {
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val pattern =
        Regex("(U.ng m.i ng.y|Ng.y u.ng|U.ng ng.y) ([1-9]) l.n, l.n ([1-9]) (vi.n|.ng) (\\W|\\S |)([1-9]) ng.y")
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val selectImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUri.value = uri
        }
    var pillList = listOf<RegexData>()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier.padding(20.dp).height(50.dp),
                    onClick = {
                        selectImage.launch("image/*")
                    }) {
                    Icon(
                        Icons.Filled.Add,
                        "add",
                        tint = Color.Blue
                    )
                }
                if (imageUri.value != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberImagePainter(
                            data = imageUri.value
                        ),
                        contentDescription = "image"
                    )
                    Button(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = {
                            val image = InputImage.fromFilePath(context, imageUri.value!!)
                            val lineList = mutableListOf<String>()
                            recognizer.process(image)
                                .addOnSuccessListener { visionText ->
                                    for (block in visionText.textBlocks) {
                                        for (line in block.lines) {
                                            Log.d("Line List", line.text)
                                            lineList.add(line.text)
                                        }
                                    }
                                    var regexItem = RegexData("ini", 0, 0, 0)
                                    for (lineText in lineList) {
                                        if (pattern.containsMatchIn(lineText)) {
                                            val match = pattern.find(lineText)!!
                                            val numberedGroupValues = match.destructured.toList()
                                            regexItem.perDay = numberedGroupValues[1].toInt()
                                            regexItem.perTime = numberedGroupValues[2].toInt()
                                            regexItem.totalDay = numberedGroupValues[5].toInt()
                                        }
                                        else if (lineText.length > 4) {
                                            regexItem.name = lineText
                                        }
                                        if (regexItem.name != "ini"
                                            && regexItem.perDay != 0
                                            && regexItem.perTime != 0
                                            && regexItem.totalDay != 0) {
                                            pillList += regexItem
                                            regexItem = RegexData("initial", 0, 0, 0)
                                        }
                                    }
                                    Log.d("Đây nè bà già", "$pillList")
                                    var nameList = arrayOf<String>()
                                    var perDayList = arrayOf<Int>()
                                    var perTimeList = arrayOf<Int>()
                                    var totalDayList = arrayOf<Int>()
                                    for (pillItem in pillList) {
                                        nameList += pillItem.name
                                        perDayList += pillItem.perDay
                                        perTimeList += pillItem.perTime
                                        totalDayList += pillItem.totalDay
                                    }
                                    val pillArray = RegexArray(nameList, perDayList, perTimeList, totalDayList)
                                    Log.d("Before Json", "$pillArray")

                                    navController.navigate(Screen.PillScreen.route)

                                    /**
                                    fun navigateToUser(pillArr: RegexArray
                                    ) {
                                        val pillArrayJson = Gson().toJson(pillArray)
                                        Log.d("after Json", "$pillArrayJson")
                                        navController.navigate("pill_screen/$pillArrayJson")
                                    }
                                    navigateToUser(pillArray)
**/
                                }
                                .addOnFailureListener {
                                    Log.d("You such a failure", it.message.toString())
                                }
                        }
                    ) {
                        Text("To Pill Screen")
                    }
                }
            }
        }
    }
}




