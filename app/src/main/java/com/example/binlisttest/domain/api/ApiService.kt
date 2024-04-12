package com.example.binlisttest.domain.api

import com.example.binlisttest.domain.model.BinlistData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{bin}")
    suspend fun getProducts(@Path("bin") bin: String): Response<BinlistData>
}