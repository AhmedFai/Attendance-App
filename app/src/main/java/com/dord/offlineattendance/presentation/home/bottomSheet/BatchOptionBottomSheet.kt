package com.dord.offlineattendance.presentation.home.bottomSheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.dord.offlineattendance.R
import com.dord.offlineattendance.presentation.batchListScreen.BatchListViewModel
import com.dord.offlineattendance.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchOptionBottomSheet(
    viewModel: BatchListViewModel,
    onDismiss: () -> Unit,
    onClick: (String) -> Unit
){

    val state = viewModel.uiState
    val dimens = MaterialTheme.dimens

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(
            topStart = dimens.bottomSheetTopShape,
            topEnd = dimens.bottomSheetTopShape
        ),
        containerColor = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.spaceM),
            verticalArrangement = Arrangement.spacedBy(dimens.spaceM)
        ) {

            Text(
                text = stringResource(R.string.select_an_option),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(top = dimens.spaceS),
                verticalArrangement = Arrangement.spacedBy(dimens.spaceS)
            ) {
                items(state.batches) { batch ->
                    BatchOptionItem(
                        batch,
                        onClick = onClick
                    )
                }
                item {

                    Text(
                        text = stringResource(R.string.embeddingsForAll),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimens.spaceM)
                            .clickable {
                                onClick("ALL")
                            },
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(dimens.spaceXS))

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.cancel), color = Color.Gray)
            }
        }
    }

}