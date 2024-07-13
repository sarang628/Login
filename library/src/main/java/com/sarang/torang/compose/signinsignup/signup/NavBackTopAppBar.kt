package com.sarang.torang.compose.signinsignup.signup

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sarang.torang.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBackTopAppBar(onBack: () -> Unit) {
    TopAppBar(title = { }, navigationIcon = {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.a11y_back)
            )
        }
    }
    )
}