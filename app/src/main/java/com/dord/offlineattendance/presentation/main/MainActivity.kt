package com.dord.offlineattendance.presentation.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.lifecycle.lifecycleScope
import com.dord.offlineattendance.BuildConfig
import com.dord.offlineattendance.R
import com.dord.offlineattendance.data.workManager.WorkManagerHelper
import com.dord.offlineattendance.domain.security.SecurityResult
import com.dord.offlineattendance.domain.usecase.changeLanguage.GetLanguageUseCase
import com.dord.offlineattendance.presentation.common.AppAlertDialog
import com.dord.offlineattendance.presentation.common.AppDialogConfig
import com.dord.offlineattendance.presentation.navGraph.navGraph
import com.dord.offlineattendance.presentation.security.SecurityManager
import com.dord.offlineattendance.ui.theme.AttendanceTheme
import com.dord.offlineattendance.util.AppUtil
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    @Inject
    lateinit var getLanguageUseCase: GetLanguageUseCase
    @RequiresApi(Build.VERSION_CODES.O)
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

        lifecycleScope.launch {
            getLanguageUseCase().collect { lang ->

                AppUtil.changeAppLanguage(context = this@MainActivity, lang)
                Log.e("LANG", "Applied = $lang")
            }
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

            Log.e("LANG", "Current = ${Locale.getDefault()}")
            AttendanceTheme(darkTheme = false) {
                var securityResult by remember { mutableStateOf<SecurityResult?>(null) }
                val context = this@MainActivity
                val startDestination = viewModel.uiState.collectAsState().value
                LaunchedEffect(Unit) {
                    viewModel.uiEvent.collect { event ->
                        when (event) {
                            is MainUiEvent.ShowToast -> {
                                Toast.makeText(
                                    context,
                                    event.message.asString(context),
                                    Toast.LENGTH_SHORT
                                ).show()
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