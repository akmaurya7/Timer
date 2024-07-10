package com.example.mvvm.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvm.ViewModel.TimerViewModel

@Composable
fun TimerScreen(viewModel: TimerViewModel = viewModel()) {
    val timerState = viewModel.timerState.collectAsState()

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = viewModel.formatTime(timerState.value.time),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .wrapContentWidth()
                .height(300.dp)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(timerState.value.laps.reversed()) { index, lapTime ->
                Text(text = "Lap ${timerState.value.laps.size - index}: ${viewModel.formatTime(lapTime)}")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            when {
                !timerState.value.isRunning && !timerState.value.isPaused -> {
                    Button(onClick = { viewModel.startTimer() }) {
                        Text(text = "Start")
                    }
                }
                timerState.value.isRunning -> {
                    Button(onClick = { viewModel.lapTimer() }) {
                        Text(text = "Lap")
                    }
                    Button(onClick = { viewModel.pauseTimer() }) {
                        Text(text = "Pause")
                    }
                }
                timerState.value.isPaused -> {
                    Button(onClick = { viewModel.resetTimer() }) {
                        Text(text = "Reset")
                    }
                    Button(onClick = { viewModel.startTimer() }) {
                        Text(text = "Resume")
                    }
                }
            }
        }
    }
}
