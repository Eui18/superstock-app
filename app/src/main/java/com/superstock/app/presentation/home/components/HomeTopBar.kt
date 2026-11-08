package com.superstock.app.presentation.home.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.superstock.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            TextButton(onClick = onLogoutClick) {
                Text(stringResource(R.string.home_logout))
            }
        },
        modifier = modifier
    )
}