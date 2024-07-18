package com.shabeen07.comicheros.view.login

import androidx.lifecycle.ViewModel
import com.shabeen07.comicheros.utils.Constants

class LoginViewModel : ViewModel() {

    fun checkLogin(email: String, password: String) : Boolean {
        return email == Constants.EMAIL && password == Constants.PASSWORD
    }
}