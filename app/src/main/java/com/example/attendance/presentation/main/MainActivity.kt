package com.example.attendance.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.attendance.data.workManager.WorkManagerHelper
import com.example.attendance.presentation.navGraph.navGraph
import com.example.attendance.ui.theme.AttendanceTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WorkManagerHelper.enqueuePeriodic(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //enableEdgeToEdge()
//        installSplashScreen().apply {
//            setKeepOnScreenCondition{
//                viewModel.splashScreenCondition
//            }
//        }
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
        setContent {
            AttendanceTheme(darkTheme = false) {

                val context = this@MainActivity
                val startDestination = viewModel.uiState.collectAsState().value
                LaunchedEffect(Unit) {
                    viewModel.uiEvent.collect { event->
                        when(event){
                            is MainUiEvent.ShowToast -> {
                                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                val domain = viewModel.domain
                val systemController = rememberSystemUiController()

                SideEffect {
                    domain.let {
                        systemController.setStatusBarColor(
                            color = it.primaryColor,
                            darkIcons = false
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .background(color = domain.primaryColor)
                ) {
                    if (startDestination != null) {
                        navGraph(startDestination)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AttendanceTheme {
        Greeting("Android")
    }
}