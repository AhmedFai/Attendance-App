package com.example.attendance.presentation.main

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.attendance.BuildConfig
import com.example.attendance.R
import com.example.attendance.data.workManager.WorkManagerHelper
import com.example.attendance.domain.security.SecurityResult
import com.example.attendance.presentation.common.AppAlertDialog
import com.example.attendance.presentation.common.AppDialogConfig
import com.example.attendance.presentation.navGraph.navGraph
import com.example.attendance.presentation.security.SecurityManager
import com.example.attendance.ui.theme.AttendanceTheme
import com.example.attendance.util.AppUtil.printSslPin
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WorkManagerHelper.enqueuePeriodic(this)
        //printSslPin()
        // Prevent Screen Shot
        if (!BuildConfig.DEBUG) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }

        // WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
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
                var securityResult by remember { mutableStateOf<SecurityResult?>(null) }
                val context = this@MainActivity
                val startDestination = viewModel.uiState.collectAsState().value
                LaunchedEffect(Unit) {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is MainUiEvent.ShowToast -> {
                                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    securityResult = SecurityManager.validate(context)
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
                securityResult?.let { result ->

                    if (result != SecurityResult.SAFE) {

                        val message = when (result) {

                            SecurityResult.ROOTED_DEVICE ->
                                stringResource(R.string.rootDetected)

                            SecurityResult.EMULATOR_DEVICE ->
                                stringResource(R.string.emulatorDetected)

                            SecurityResult.DEVELOPER_OPTIONS_ENABLED ->
                                stringResource(R.string.developerOptionDetected)

                            SecurityResult.FRIDA_DETECTED ->
                                stringResource(R.string.securityViolationDetected)

                            else -> ""
                        }

                        AppAlertDialog(
                            config = AppDialogConfig(
                                domainType = domain,
                                title = stringResource(R.string.securityAlert),
                                message = message,
                                positiveText = stringResource(R.string.exit_text),
                                onPositiveClick = {
                                    finishAffinity()
                                }
                            )
                        )
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