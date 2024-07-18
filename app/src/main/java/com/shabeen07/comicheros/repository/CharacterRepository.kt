package com.shabeen07.comicheros.repository

import com.shabeen07.comicheros.api.CharacterClient
import com.shabeen07.comicheros.api.Resource
import com.shabeen07.comicheros.models.CharacterItem
import com.shabeen07.comicheros.models.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object CharacterRepository {

    // get all characters
    fun getCharacters(page: Int) : Flow<Resource<CharacterResponse>> = flow {
        // emit loading
        emit(Resource.Loading())
        val response = try {
            CharacterClient.characterService.getCharacters(page)
        }catch (e: Exception){
            // emit error
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            return@flow
        }
        // emit success if response is successful
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)

    // get character by id
    fun getCharacterById(characterId: Int) : Flow<Resource<CharacterItem>> = flow {
        // emit loading
        emit(Resource.Loading())
        val response = try {
            CharacterClient.characterService.getCharacterById(characterId)
        }catch (e: Exception){
            // emit error
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            return@flow
        }
        // emit success if response is successful
        emit(Resource.Success(response))
    }.flowOn(Dispatchers.IO)


}