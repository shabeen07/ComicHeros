package com.shabeen07.comicheros.api

import com.shabeen07.comicheros.models.CharacterItem
import com.shabeen07.comicheros.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    // get characters by page
    @GET("api/character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse


    @GET("api/character/{characterId}")
    suspend fun getCharacterById(@Path("characterId") characterId: Int): CharacterItem

}