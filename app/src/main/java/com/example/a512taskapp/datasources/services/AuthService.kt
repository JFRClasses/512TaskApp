package com.example.a512taskapp.datasources.services

import com.example.a512taskapp.domain.dtos.AuthDto
import com.example.a512taskapp.domain.dtos.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    suspend fun login(@Body login:AuthDto) : Response<AuthResponse>

    @POST("user")
    suspend fun registerUser(@Body authDto: AuthDto) : Response<AuthResponse>
}