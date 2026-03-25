package com.example.attendance.presentation.changeLanguageScreen

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.attendance.R
import com.example.attendance.presentation.common.Toolbar
import com.example.attendance.ui.theme.dimens
import com.example.attendance.util.AppUtil
import java.util.Locale

@Composable
fun LanguageScreen(
    viewModel : ChangeLanguageViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onLanguageSelected: () -> Unit
) {

    val context = LocalContext.current
    val dimens = MaterialTheme.dimens
    val languages = listOf(
        LanguageItem("en", "English", "A", Color(0xFFD6EAF8), Color(0xFFA9CCE3)),
        LanguageItem("hi", "हिंदी", "अ", Color(0xFFFADBD8), Color(0xFFF5B7B1)),
        LanguageItem("bn", "বাংলা", "জা", Color(0xFFFCF3CF), Color(0xFFF9E79F)),
        LanguageItem("gu", "ગુજરાતી", "જા", Color(0xFFE8DAEF), Color(0xFFD2B4DE)),
        LanguageItem("ta", "தமிழ்", "அ", Color(0xFFD1F2EB), Color(0xFFA3E4D7)),
        LanguageItem("ml", "മലയാളം", "അ", Color(0xFFFADBD8), Color(0xFFF5B7B1)),
        LanguageItem("as", "অসমীয়া", "অ", Color(0xFFEAF2F8), Color(0xFFAED6F1)),
        LanguageItem("kn", "ಕನ್ನಡ", "ಅ", Color(0xFFE8F8F5), Color(0xFFA2D9CE)),
        LanguageItem("mr", "मराठी", "अ", Color(0xFFFEF9E7), Color(0xFFF7DC6F)),
        LanguageItem("or", "ଓଡ଼ିଆ", "ଅ", Color(0xFFFDEDEC), Color(0xFFF5B7B1)),
        LanguageItem("pa", "ਪੰਜਾਬੀ", "ਅ", Color(0xFFE8DAEF), Color(0xFFD7BDE2)),
        LanguageItem("te", "తెలుగు", "అ", Color(0xFFEAF2F8), Color(0xFFA9CCE3)),
        LanguageItem("ur", "اردو", "ا", Color(0xFFE8F6F3), Color(0xFFA3E4D7))
    )

    val selectedCode by viewModel.language.collectAsState()
    val domain = viewModel.domain
    var selectedLanguage by remember { mutableStateOf<LanguageItem?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
            )
    ) {

        Toolbar(
            title = "Change Language",
            domain = domain,
            onBack = onBack
        )

        LazyVerticalGrid(
            modifier = Modifier.padding(bottom = dimens.screenPaddingVertical),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(dimens.spaceM),
            verticalArrangement = Arrangement.spacedBy(dimens.spaceM),
            horizontalArrangement = Arrangement.spacedBy(dimens.spaceM)
        ) {

            items(languages) { item ->
                LanguageCard(
                    item = item,
                    isSelected = selectedCode  == item.code,
                    onClick = {
                        selectedLanguage = item
                        showDialog = true
                    }
                )
            }
        }
    }

    if (showDialog && selectedLanguage != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Change Language") },
            text = { Text("Do you want to switch to ${selectedLanguage!!.label}?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.changeLanguage(selectedLanguage!!.code)
                    AppUtil.changeAppLanguage(context, selectedLanguage!!.code)
                    showDialog = false
                    onLanguageSelected()
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

