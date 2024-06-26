package com.mkd.topnewsindia.presentation.details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mkd.topnewsindia.R
import com.mkd.topnewsindia.utils.ButtonState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onBackClick: () -> Unit,
    title: String,
    onPlayClick: () -> Unit,
    buttonState: ButtonState
) {

    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.DarkGray,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = onPlayClick) {
                val icon: Painter = if (buttonState.name == "Play") {
                    painterResource(id = R.drawable.ic_play)
                } else {
                    painterResource(id = R.drawable.ic_stop)
                }
                Icon(painter = icon, contentDescription = "Play/Pause Icon")
            }
        }
    )
}

@Preview
@Composable
fun DetailsTopBarPreview() {
    DetailsTopBar(
        onBackClick = {},
        title = "Times of India",
        onPlayClick = {},
        buttonState = ButtonState.Play
    )
}