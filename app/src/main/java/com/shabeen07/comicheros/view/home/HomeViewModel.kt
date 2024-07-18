package com.shabeen07.comicheros.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabeen07.comicheros.api.Resource
import com.shabeen07.comicheros.models.CharacterResponse
import com.shabeen07.comicheros.repository.CharacterRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var _characters = MutableLiveData<Resource<CharacterResponse>>()
    val characters: MutableLiveData<Resource<CharacterResponse>> get() = _characters

    init {
        getCharacters()
    }

    // get characters
    private fun getCharacters(page: Int = 1) {
        viewModelScope.launch {
            CharacterRepository.getCharacters(page)
                .collect {
                    _characters.value = it
                }
        }
    }
}