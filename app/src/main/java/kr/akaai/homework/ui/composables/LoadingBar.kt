package kr.akaai.homework.ui.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kr.akaai.homework.ui.theme.Purple200
import kr.akaai.homework.ui.theme.Purple700

@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(alpha = 0.4f))
    ) {
        val progressAnimDuration = 1500
        val progressAnim = rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = progressAnimDuration,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        Canvas(modifier = Modifier.size(150.dp)) {
            drawArc(
                startAngle = 0f,
                sweepAngle = progressAnim.value,
                useCenter = false,
                brush = Brush.linearGradient(listOf(
                    Purple200,
                    Purple700
                )),
                style = Stroke(
                    width = 25f,
                    cap = StrokeCap.Round
                )
            )
        }
    }
}
