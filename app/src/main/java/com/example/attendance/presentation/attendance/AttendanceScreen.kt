package com.example.attendance.presentation.attendance

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.R
import com.example.attendance.presentation.common.Toolbar
import com.example.attendance.ui.theme.dimens
import com.example.attendance.util.Constants
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.pehchaan.backend.service.AuthenticationActivity

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceScreen(
    userType: String,
    userId: String,
    batchId: Long,
    onBack: () -> Unit
) {
    lateinit var permissionLauncher: ActivityResultLauncher<String>
    lateinit var gpsLauncher: ActivityResultLauncher<IntentSenderRequest>
    lateinit var fetchEmbeddingsLauncher: ActivityResultLauncher<Intent>
    val viewModel: AttendanceViewModel = hiltViewModel()
    val dimens = MaterialTheme.dimens
    var click: String = ""
    //val systemUiController = rememberSystemUiController()
    val state = viewModel.uiState
    val context = LocalContext.current
    fetchEmbeddingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val capturedLat = result.data?.getDoubleExtra("captured_lat", 0.0) ?: 0.0
        val capturedLng = result.data?.getDoubleExtra("captured_lng", 0.0) ?: 0.0
        Log.e("BATCH_ID", "Screen$batchId")
        handleAuthenticationResult(
            result,
            context,
            click,
            viewModel,
            userId,
            userType,
            batchId,
            capturedLat,
            capturedLng,
        )
    }
    LaunchedEffect(userType, userId) {
        //checkForGpsAndLocation(context,permissionLauncher, gpsLauncher, fetchEmbeddingsLauncher, centerLat, centerLng, radius)
        viewModel.loadUser(userType, userId, batchId)
    }
    // 🔹 Dummy geofence data (facility location)
    val centerLat = "28.6276".toDouble()
    val centerLng = "77.2205".toDouble()
    val radius = 500f
//    val centerLat = bath?.latitude
//    val centerLng = bath?.longitude
//    val radius = bath?.radius?.toFloat()
    // 🔹 Permission launcher
    permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (!granted) {
                Toast.makeText(context, R.string.locationDenied, Toast.LENGTH_SHORT).show()
            } else {
                checkForGpsAndLocation(
                    context,
                    permissionLauncher,
                    gpsLauncher,
                    fetchEmbeddingsLauncher,
                    userId,
                    centerLat!!,
                    centerLng!!,
                    radius!!
                )
            }
        }

    // 🔹 GPS enable launcher
    gpsLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                checkForGpsAndLocation(
                    context,
                    permissionLauncher,
                    gpsLauncher,
                    fetchEmbeddingsLauncher,
                    userId,
                    centerLat!!,
                    centerLng!!,
                    radius!!
                )
            } else {
                Toast.makeText(context, R.string.gpsRequired, Toast.LENGTH_SHORT).show()
            }
        }

//    DisposableEffect(viewModel.domain) {
//        systemUiController.setStatusBarColor(
//            color = viewModel.domain.primaryColor,
//            darkIcons = false
//        )
//
//        onDispose {
//            // kuch nahi – next screen handle karega
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
    ) {

        // 🔹 TOOLBAR
        Toolbar(
            title =
                if (userType == "CANDIDATE")
                    stringResource(R.string.mark_candidate_attendance)
                else
                    stringResource(R.string.mark_self_attendance),
            domain = viewModel.domain,
            onBack = onBack
        )

        when {
            state.isLoading -> {
                //AttendanceShimmer()
            }

            state.error != null -> {

            }

            else -> {
                Spacer(Modifier.height(dimens.spaceM))

                // 🔹 CARD
                Card(
                    modifier = Modifier
                        .padding(horizontal = dimens.spaceM)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(dimens.radiusM),
                    elevation = CardDefaults.cardElevation(dimens.spaceXS),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(modifier = Modifier.padding(dimens.spaceM)) {

                        // Profile
                        ProfileSection(
                            type = userType,
                            candidate = viewModel.uiState.candidate,
                            faculty = viewModel.uiState.faculty,
                            domain = viewModel.domain
                        )

                        Divider(Modifier.padding(vertical = dimens.spaceM))

                        // Time
                        TimeSection()

                        Spacer(Modifier.height(dimens.spaceM))

                        // Buttons
                        AttendanceButtons(
                            domain = viewModel.domain,
                            canCheckIn = state.canCheckIn,
                            canCheckOut = state.canCheckOut,
                            onCheckIn = {
                                //viewModel.onCheckIn()
                                click = "CHECK-IN"
                                checkForGpsAndLocation(
                                    context,
                                    permissionLauncher,
                                    gpsLauncher,
                                    fetchEmbeddingsLauncher,
                                    userId,
                                    centerLat!!,
                                    centerLng!!,
                                    radius!!
                                )
                            },
                            onCheckOut = {
                                //viewModel.onCheckOut()
                                click = "CHECK-OUT"
                                checkForGpsAndLocation(
                                    context,
                                    permissionLauncher,
                                    gpsLauncher,
                                    fetchEmbeddingsLauncher,
                                    userId,
                                    centerLat!!,
                                    centerLng!!,
                                    radius!!
                                )
                            }
                        )

                        Divider(Modifier.padding(vertical = dimens.spaceM))

                        // States
                        AttendanceStats(
                            checkIn = state.checkInTime,
                            checkOut = state.checkOutTime,
                            total = state.totalHours
                        )
                    }
                }
            }
        }
    }
    AttendanceSuccessSheetHost(
        domain = viewModel.domain,
        show = state.showSuccessSheet,
        type = state.successType ?: "",
        state = state,
        onDismiss = { viewModel.hideSuccessSheet() }
    )
}

private fun startAuthentication(
    context: Context,
    userId: String,
    launcher: ActivityResultLauncher<Intent>,
    capturedLat: Double,
    capturedLng: Double
) {
    val intent = Intent(context, AuthenticationActivity::class.java).apply {
        putExtra(Constants.EXTRA_CLIENT_ID, Constants.YOUR_CLIENT_ID)
        putExtra(Constants.EXTRA_CALL_TYPE, Constants.CALL_TYPE_LOGIN)
        putExtra(Constants.EXTRA_USER_ID, "FAIFAI")
        putExtra("captured_lat", capturedLat)
        putExtra("captured_lng", capturedLng)
    }
    try {
        launcher.launch(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Authentication service unavailable", Toast.LENGTH_SHORT).show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun handleAuthenticationResult(
    result: ActivityResult,
    context: Context,
    click: String,
    viewModel: AttendanceViewModel,
    userId: String,
    userType: String,
    batchId: Long,
    latitude: Double?,
    longitude: Double?,
) {
    if (result.resultCode == Activity.RESULT_OK) {
        val status = result.data?.getStringExtra(Constants.RESULT_STATUS) ?: "failure"
        val message = result.data?.getStringExtra(Constants.RESULT_MESSAGE) ?: "Unknown error"
        if (status == "success") {
            if (click == "CHECK-IN") {
                viewModel.markCheckIn(
                    userId = userId,
                    userType = userType,
                    batchId = batchId,
                    latitude = latitude,
                    longitude = longitude
                )
            } else if (click == "CHECK-OUT") {
                viewModel.markCheckOut(
                    userId = userId,
                    userType = userType,
                    batchId = batchId
                )
            }
        } else {
            Toast.makeText(context, "Authentication failed: $message", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
    }
}

fun isInsideGeofence(
    currentLat: Double,
    currentLng: Double,
    centerLat: Double,
    centerLng: Double,
    radiusMeters: Float
): Boolean {
    val result = FloatArray(1)

    android.location.Location.distanceBetween(
        currentLat,
        currentLng,
        centerLat,
        centerLng,
        result
    )

    return result[0] <= radiusMeters
}

fun checkForGpsAndLocation(
    context: Context,
    permissionLauncher: ActivityResultLauncher<String>,
    gpsLauncher: ActivityResultLauncher<IntentSenderRequest>,
    fetchEmbeddingsLauncher: ActivityResultLauncher<Intent>,
    userId: String,
    centerLat: Double,
    centerLng: Double,
    radius: Float
) {
    // STEP 1 — Permission check
    val permission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    if (permission != PackageManager.PERMISSION_GRANTED) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        return
    }

    // STEP 2 — GPS check
    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        1000
    ).build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    val client = LocationServices.getSettingsClient(context)

    client.checkLocationSettings(builder.build())
        .addOnSuccessListener {

            // STEP 3 — Get current location
            val fusedClient =
                LocationServices.getFusedLocationProviderClient(context)

            fusedClient.lastLocation
                .addOnSuccessListener { location ->

                    if (location == null) {
                        Toast.makeText(context, R.string.failedLocation, Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    // STEP 4 — Geofence check
                    val inside = isInsideGeofence(
                        location.latitude,
                        location.longitude,
                        centerLat,
                        centerLng,
                        radius
                    )

                    Log.e("MyLatLong", "${location.latitude}, ${location.longitude}")
                    Log.e("CenterLatLong", "$centerLat, $centerLng")
                    Log.e("USER_ID", userId)
//                    Toast.makeText(
//                        context,
//                        "${location.latitude}, ${location.longitude}",
//                        Toast.LENGTH_LONG
//                    ).show()
                    if (inside) {
                        startAuthentication(context, userId, fetchEmbeddingsLauncher, location.latitude, location.longitude)
                    } else {
                        Toast.makeText(context, R.string.outsideRange, Toast.LENGTH_LONG).show()
                    }
                }

        }
        .addOnFailureListener { exception ->

            if (exception is ResolvableApiException) {
                try {
                    val intent = IntentSenderRequest.Builder(exception.resolution).build()
                    gpsLauncher.launch(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
}