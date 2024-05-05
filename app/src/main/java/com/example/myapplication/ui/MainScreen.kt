package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.GET_RANDOM_NUM
import com.example.myapplication.MAIN_SCREEN
import com.example.myapplication.MEAN_VAL
import com.example.myapplication.R
import com.example.myapplication.RANDOM_NUMBER_RES
import com.example.myapplication.VARIANCE_VALUE

@Composable
fun MainScreen(
    value: String,
    onClick: (String, String) -> Unit
) {
    var mean by rememberSaveable { mutableStateOf("") }
    var variance by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().testTag(MAIN_SCREEN)) {
        Image(
            painter = painterResource(id = R.drawable.fon),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = mean,
                onValueChange = { newValue ->
                    mean = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(MEAN_VAL),
                label = { Text("Введите μ") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )
            TextField(value = variance,
                onValueChange = { newValue ->
                    variance = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(VARIANCE_VALUE),
                label = { Text("Введите σ^2") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Card(shape = RoundedCornerShape(12.dp)) {
                Text(
                    text = value,
                    modifier = Modifier
                        .padding(50.dp)
                        .testTag(RANDOM_NUMBER_RES))
            }
            Spacer(modifier = Modifier.weight(0.5f))
            Button(
                onClick = {
                    onClick(mean, variance)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .testTag(GET_RANDOM_NUM)
            ) {
                Text(text = "Получить результат")
            }
        }
    }
}


@Preview
@Composable
fun basPreview() {
    MainScreen(value = "1.0", onClick = { _, _ -> })
}

