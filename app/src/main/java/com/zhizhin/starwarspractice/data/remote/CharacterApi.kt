package com.zhizhin.starwarspractice.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("people/")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("search") search: String = ""
    ): CharactersResponseDto
}