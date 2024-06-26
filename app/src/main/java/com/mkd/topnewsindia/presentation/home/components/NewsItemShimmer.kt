package com.mkd.topnewsindia.presentation.home.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun Modifier.shimmer(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "RepeatableShimmer")
    val shimmerTranslateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = "ShimmerAnimation"
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.9f)
        ),
        start = Offset.Zero,
        end = Offset(x = shimmerTranslateAnim.value, y = shimmerTranslateAnim.value),
        tileMode = TileMode.Mirror
    )

    this
        .graphicsLayer(alpha = 0.99f)
        .background(brush = brush)
}


@Composable
fun ShimmerArticleItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(all = 8.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .shimmer()
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(0.5f)
                    .height(16.dp)
                    .shimmer()
                    .clip(RoundedCornerShape(4.dp))
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(16.dp)
                    .shimmer()
                    .clip(RoundedCornerShape(4.dp))
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Right Arrow",
            tint = Color.Gray,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShimmerArticleItemPreview() {
    ShimmerArticleItem()
}