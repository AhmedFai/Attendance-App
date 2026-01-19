package com.example.attendance.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomainDropDown(
    selected: DomainType,
    onSelect: (DomainType) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .background(Color.White),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = stringResource(selected.titleRes),
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(
                        if (selected == DomainType.DDUGKY)
                            R.drawable.ic_ddugky_min
                        else R.drawable.ic_rseti_min
                    ),
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldBlackColors
        )

        ExposedDropdownMenu(
            modifier = Modifier
                .background(Color.White),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DomainType.entries.forEach { domain ->
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(domain.titleRes),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    onClick = {
                        onSelect(domain)
                        expanded = false
                    }
                )
            }
        }
    }


}