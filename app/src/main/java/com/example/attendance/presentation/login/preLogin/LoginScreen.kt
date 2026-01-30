package com.example.attendance.presentation.login.preLogin

import android.R.id.message
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.BuildConfig
import com.example.attendance.R
import com.example.attendance.domain.model.login.LoginRequest
import com.example.attendance.presentation.login.postLogin.BootStrapViewModel
import com.example.attendance.presentation.login.postLogin.BootstrapState
import com.example.attendance.ui.theme.dimens
import com.example.attendance.util.Constants
import com.pehchaan.backend.service.AuthenticationActivity
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    bootStrapViewModel: BootStrapViewModel = hiltViewModel()
) {

    Log.e("SessionKyaHai?", viewModel.session?.isLoggedIn.toString())
    Log.e("SessionKyaHaiToken?", viewModel.session?.token.toString())
    Log.e("DomainKyaHai?", viewModel.selectedDomain.name)
    Log.e("DomainKyaHaiBuildVersion?", BuildConfig.VERSION_NAME)
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val state by viewModel.loginUiState.collectAsState()
    val bootstrapState = bootStrapViewModel.state

    LaunchedEffect(Unit) {
        viewModel.loginUiEvent.collect{ event ->
            when(event){
                is LoginUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
                }
                is LoginUiEvent.StartBootStrap -> {
                    bootStrapViewModel.startBootstrap()
                }
                else -> {}
            }
        }
    }

    // 🔹 Activity Result Launcher (Compose way)
    val startForAuthentication =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val status =
                    data?.getStringExtra(Constants.RESULT_STATUS) ?: "failure"
                val message =
                    data?.getStringExtra(Constants.RESULT_MESSAGE) ?: "Unknown error"

                if (status == "success") {

                    Toast
                        .makeText(context, "✅ Success: $message", Toast.LENGTH_SHORT)
                        .show()

//                    // 👇 Same logic jo fragment me tha
//                    viewModel.onFaceAuthSuccess()

                } else {
                    Toast
                        .makeText(context, "✅ Failure: $message", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast
                    .makeText(context, "✅ Failure: $message", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    val domain = viewModel.selectedDomain
    val password = viewModel.password
    val passwordVisible = viewModel.passwordVisible
    val dimens = MaterialTheme.dimens

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.15f))
            .clickable(enabled = false) {}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = dimens.spaceL)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(dimens.space4XL))
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimens.spaceXL),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_ddgky),
                    contentDescription = stringResource(R.string.ddugky),
                    modifier = Modifier.height(dimens.space4XL)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_rseti),
                    contentDescription = stringResource(R.string.rseti),
                    modifier = Modifier.height(dimens.space4XL)
                )
            }
            Spacer(Modifier.height(dimens.space2XL))
            Text(
                text = stringResource(R.string.login_to_your_account),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(Modifier.height(dimens.spaceXS))
            Text(
                text = stringResource(R.string.please_sign_in_to_your_account),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Spacer(Modifier.height(dimens.spaceXL))

            // Domain Selector
            DomainDropDown(
                selected = domain,
                onSelect = viewModel::onDomainChange
            )

            Spacer(Modifier.height(dimens.spaceL))

            // User Id Field
            OutlinedTextField(
                value = viewModel.userId,
                onValueChange = viewModel::onUserIdChange,
                modifier = Modifier.fillMaxWidth(),
                isError = state.userIdError != null,
                shape = RoundedCornerShape(dimens.radiusM),
                placeholder = {
                    Text(text = stringResource(R.string.login_id))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null
                    )
                },
                colors = TextFieldBlackColors,
                supportingText = {
                    state.userIdError?.let {
                        Text(
                            text = it.asString(context),
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(Modifier.height(dimens.spaceS))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = viewModel::onPasswordChange,
                modifier = Modifier.fillMaxWidth(),
                isError = state.passwordError != null,
                shape = RoundedCornerShape(dimens.radiusM),
                placeholder = { Text(stringResource(R.string.enter_password)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        // login call
                        viewModel.onLogin(
                            loginRequest = LoginRequest(
                                loginId = viewModel.userId,
                                password = viewModel.password
                            )
                        )
                    }
                ),
                visualTransformation =
                    if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = viewModel::togglePasswordVisibility) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, null)
                },
                colors = TextFieldBlackColors,
                supportingText = {
                    state.passwordError?.let {
                        Text(
                            text = it.asString(context),
                            color = Color.Red
                        )
                    }
                }
            )

            Spacer(Modifier.height(dimens.space2XS))
            // Forgot Password
            Text(
                text = stringResource(R.string.forgot_password),
                modifier = Modifier.align(Alignment.End),
                color = viewModel.selectedDomain.primaryColor,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(dimens.spaceM))

            // 🔘 Login Button
            Button(
                enabled = !state.isLoading,
                onClick = {
                    keyboardController?.hide()
                    viewModel.onLogin(
                        loginRequest = LoginRequest(
                            loginId = viewModel.userId,
                            password = viewModel.password
                        )
                    )

//                    startAuthentication(
//                        context = context,
//                        launcher = startForAuthentication,
//                        callType = Constants.CALL_TYPE_REGISTRATION,
//                        userId = "DSHEKAR",
//                        userName = "Dereddy Shekar Reddy"
//                    )

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.buttonHeight),
                shape = RoundedCornerShape(dimens.radiusM),
                colors = ButtonDefaults.buttonColors(
                    containerColor = viewModel.selectedDomain.primaryColor
                )
            ) {
                Text(
                    stringResource(R.string.login),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        when{
            state.isLoading -> {
                val showLoader =
                    state.isLoading || bootstrapState is BootstrapState.Loading

                if (showLoader) {
                    LoginLoadingOverlay(
                        color = viewModel.selectedDomain.primaryColor
                    )
                }
            }
            state.error != null -> {
                Log.d("LoginScreenError", "Error: ${state.error}")
            }
            state.data != null -> {
                Log.d("LoginScreenSuccess", "Data: ${state.data?.toString()}")
            }

        }
        when(bootstrapState){
            is BootstrapState.Error -> {
                Log.e("Bootstrap", "Error: ${bootstrapState.message}")
            }
            BootstrapState.Idle -> {

            }
            BootstrapState.Loading -> {

            }
            BootstrapState.Success -> {
                Log.e("Bootstrap", "Success")
            }
        }
    }
}

val TextFieldBlackColors
    @Composable
    get() = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        disabledTextColor = Color.Black,

        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        disabledBorderColor = Color.Black,

        focusedTrailingIconColor = Color.Black,
        unfocusedTrailingIconColor = Color.Black,
        disabledTrailingIconColor = Color.Black,

        focusedLeadingIconColor = Color.Black,
        unfocusedLeadingIconColor = Color.Black,
        disabledLeadingIconColor = Color.Black,

        cursorColor = Color.Black
    )

@Composable
fun LoginLoadingOverlay(
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.15f)), // subtle dim
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = color,
            strokeWidth = 3.dp,
            modifier = Modifier.size(36.dp)
        )
    }
}

fun startAuthentication(
    context: Context,
    launcher: ActivityResultLauncher<Intent>,
    callType: String,
    userId: String,
    userName: String
) {
    val intent = Intent(context, AuthenticationActivity::class.java).apply {
        putExtra(
            Constants.EXTRA_CLIENT_ID,
            Constants.YOUR_CLIENT_ID
        )
        putExtra(
            Constants.EXTRA_CALL_TYPE,
            callType
        )
        putExtra(
            Constants.EXTRA_USER_ID,
            userId
        )

        if (callType == Constants.CALL_TYPE_REGISTRATION) {
            putExtra(
                Constants.EXTRA_USER_NAME,
                userName
            )
        }
    }

    launcher.launch(intent)
}
