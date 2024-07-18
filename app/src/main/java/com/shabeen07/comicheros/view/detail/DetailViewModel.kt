package com.shabeen07.comicheros.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabeen07.comicheros.api.Resource
import com.shabeen07.comicheros.models.CharacterItem
import com.shabeen07.comicheros.repository.CharacterRepository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private var _character = MutableLiveData<Resource<CharacterItem>>()
    val character: MutableLiveData<Resource<CharacterItem>> get() = _character

    fun getCharacter(characterId: Int){
        viewModelScope.launch {
            CharacterRepository.getCharacterById(characterId)
                .collect{
                    _character.value = it
                }
        }
    }

}