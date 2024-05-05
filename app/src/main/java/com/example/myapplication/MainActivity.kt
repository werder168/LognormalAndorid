package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.logic.MainViewModel
import com.example.myapplication.logic.RandomFromLognormal
import com.example.myapplication.ui.MainScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels<MainViewModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(RandomFromLognormal()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state

            MyApplicationTheme {
                MainScreen(
                    value = state,
                    onClick = { mean, variance ->
                        viewModel.onClick(mean, variance)
                    }
                )
            }
        }
    }
}

