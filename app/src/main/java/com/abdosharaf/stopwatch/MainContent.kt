package com.abdosharaf.stopwatch

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdosharaf.stopwatch.ui.theme.Blue
import com.abdosharaf.stopwatch.ui.theme.Dark
import com.abdosharaf.stopwatch.ui.theme.Light
import com.abdosharaf.stopwatch.ui.theme.Red

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    MainContent()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainContent() {
    // TODO: Remove These!
    val hours by remember {
        mutableStateOf("00")
    }
    val minutes by remember {
        mutableStateOf("00")
    }
    val seconds by remember {
        mutableStateOf("00")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = hours,
                transitionSpec = { addAnimation() },
                label = "Hours"
            ) {
                Text(
                    text = it,
                    fontSize = MaterialTheme.typography.displayLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = if (it == "00") Color.White else Blue
                )
            }

            AnimatedContent(
                targetState = minutes,
                transitionSpec = { addAnimation() },
                label = "Minutes"
            ) {
                Text(
                    text = it,
                    fontSize = MaterialTheme.typography.displayLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = if (it == "00") Color.White else Blue
                )
            }

            AnimatedContent(
                targetState = seconds,
                transitionSpec = { addAnimation() },
                label = "Seconds"
            ) {
                Text(
                    text = it,
                    fontSize = MaterialTheme.typography.displayLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = if (it == "00") Color.White else Blue
                )
            }
        }

        Row(modifier = Modifier.weight(1f)) {
            // TODO: Change Button Color & Text!
            Button(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .weight(1f),
                onClick = {
                    // TODO: Implement Start/Stop Button!
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Start"
                )
            }

            Spacer(modifier = Modifier.width(30.dp))

            // TODO: Change Button Color!
            Button(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .weight(1f),
                onClick = {
                    // TODO: Implement Cancel Button!
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red,
                    contentColor = Color.White,
                    disabledContainerColor = Light,
                    disabledContentColor = Dark
                ),
                enabled = false
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 600): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) togetherWith slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}