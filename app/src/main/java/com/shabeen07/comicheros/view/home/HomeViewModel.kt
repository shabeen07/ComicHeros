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
    private var _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    val currentPage: Int get() = _currentPage.value ?: 1


    init {
        getCharacters()
    }

    // get characters
    fun getCharacters() {
        viewModelScope.launch {
            CharacterRepository.getCharacters(currentPage)
                .collect {
                    _characters.value = it
                }
        }
    }

    fun setPage(page: Int) {
        _currentPage.value = page
    }

}
