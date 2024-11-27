package com.example.a512taskapp.datasources.services

import com.example.a512taskapp.domain.dtos.TaskResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TaskService {
    @GET("tasks")
    suspend fun getTasks(@Query("userId")userId:Int):Response<TaskResponse>
}