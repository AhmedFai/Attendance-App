package com.example.attendance.presentation.common

import android.widget.Toolbar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attendance.R
import com.example.attendance.domain.model.DomainType
import com.example.attendance.ui.theme.dimens

@Composable
fun Toolbar(
    title: String,
    domain: DomainType,
    onBack: () -> Unit
){

    val dimens = MaterialTheme.dimens

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimens.toolbarHeight)
            .background(domain.primaryColor)
            .padding(end = dimens.toolbarPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onBack) {
            Icon(
                modifier = Modifier.size(dimens.iconS),
                imageVector = Icons.Outlined.ArrowBackIosNew,
                contentDescription = null,
                tint = Color.White
            )
        }

        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimens.spaceXS),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            modifier = Modifier
                .height(dimens.toolbarEndIconHeight)
                .width(dimens.toolbarEndIconWidth)
                .clip(RoundedCornerShape(dimens.spaceXS))
                .background(Color.White),
            contentAlignment = Alignment.Center,
        ){
            Image(
                painter = painterResource(
                    if (domain == DomainType.DDUGKY)
                        R.drawable.ic_ddgky
                    else
                        R.drawable.ic_rseti
                ),
                contentDescription = null
            )
        }
    }
}