package kr.akaai.homework.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.akaai.homework.R

@Composable
fun BaseToolbar(
    titleComposable: @Composable () -> Unit = {},
    frontComposable: @Composable () -> Unit = {},
    rearComposable: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            titleComposable()
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            frontComposable()
            Spacer(modifier = Modifier.weight(1f))
            rearComposable()
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}


object BaseToolbarDefaults {
    @Composable
    fun defaultTitle(
        title: String = "",
        titleSize: TextUnit = 25.sp,
        titleColor: Color = Color.Black,
        titleWeight: FontWeight = FontWeight.Medium
    ): @Composable () -> Unit = {
        Text(
            text = title,
            fontSize = titleSize,
            color = titleColor,
            fontWeight = titleWeight,
            modifier = Modifier
        )
    }

    @Composable
    fun defaultToolbarImage(
        painter: Painter = painterResource(id = R.drawable.ic_arrow_left),
        size: Size = Size(24f, 24f),
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {}
    ): @Composable () -> Unit = {
        Image(
            painter = painter,
            contentDescription = "",
            modifier = modifier
                .width(size.width.dp)
                .height(size.height.dp)
                .clickable { onClick() }
        )
    }
}