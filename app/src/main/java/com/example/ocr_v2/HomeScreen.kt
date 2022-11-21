
package com.example.ocr_v2

import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.stream.Collectors.toList

@Composable
fun HomeScreen(
    context: ComponentActivity,
    pattern: Regex,
    imageUri: MutableState<Uri?>
) {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val selectImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUri.value = uri
        }
    var pillList = listOf<RegexData>()
    val initialItem = RegexData("initial", 0, 0, 0)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    modifier = Modifier.align(Alignment.BottomStart),
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
                    IconButton(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = {
                            val image = InputImage.fromFilePath(context, imageUri.value!!)
                            val lineList = mutableListOf<String>()
                            recognizer.process(image)
                                .addOnSuccessListener { visionText ->
                                    for (block in visionText.textBlocks) {
                                        for (line in block.lines) {
                                            lineList.add(line.text)
                                        }
                                    }
                                    Log.d(null, "$lineList")
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
                                    Log.d(null, "$pillList")
                                }
                                .addOnFailureListener {
                                    Log.e("TEXT_REC", it.message.toString())
                                }

                        }) {
                        Icon(
                            Icons.Filled.Search,
                            "scan",
                            tint = Color.Blue
                        )
                    }
                }
            }
        }
    }
}
