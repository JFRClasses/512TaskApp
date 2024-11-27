package com.example.a512taskapp.domain.dtos

import com.example.a512taskapp.domain.entities.Task

data class TaskResponse(
    val tasks : List<Task>,
    val completedTasks : Int,
    val pendingTasks : Int,
    val expiredTasks : Int
)
