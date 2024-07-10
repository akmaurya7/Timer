package com.example.mvvm.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.Model.TimerData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val _timerState = MutableStateFlow(TimerData())
    val timerState: StateFlow<TimerData> = _timerState

    private var startTime = 0L
    private var pauseTime = 0L

    fun startTimer() {
        if (_timerState.value.isPaused) {
            resumeTimer()
        } else {
            startTime = System.currentTimeMillis()
            _timerState.value = _timerState.value.copy(isRunning = true, isPaused = false)
            runTimer()
        }
    }

    fun pauseTimer() {
        _timerState.value = _timerState.value.copy(isPaused = true, isRunning = false)
        pauseTime = System.currentTimeMillis()
    }

    fun resetTimer() {
        _timerState.value = TimerData()
    }

    fun lapTimer() {
        val currentTime = System.currentTimeMillis() - startTime
        val updatedLaps = _timerState.value.laps + currentTime
        _timerState.value = _timerState.value.copy(laps = updatedLaps)
    }

    private fun resumeTimer() {
        val pausedDuration = System.currentTimeMillis() - pauseTime
        startTime += pausedDuration
        _timerState.value = _timerState.value.copy(isPaused = false, isRunning = true)
        runTimer()
    }

    private fun runTimer() {
        viewModelScope.launch {
            while (_timerState.value.isRunning) {
                _timerState.value = _timerState.value.copy(time = System.currentTimeMillis() - startTime)
                delay(1000L)
            }
        }
    }

    fun formatTime(timeMillis: Long): String {
        val seconds = (timeMillis / 1000) % 60
        val minutes = (timeMillis / (1000 * 60)) % 60
        val hours = (timeMillis / (1000 * 60 * 60)) % 24
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
