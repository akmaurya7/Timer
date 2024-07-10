package com.example.mvvm.Model

data class TimerData(
    var isRunning: Boolean = false,
    var isPaused: Boolean = false,
    var time: Long = 0L,
    var laps: List<Long> = emptyList()
)
