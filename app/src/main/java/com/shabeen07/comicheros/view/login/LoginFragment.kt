package com.shabeen07.comicheros.view.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.shabeen07.comicheros.R
import com.shabeen07.comicheros.databinding.FragmentLoginBinding
import com.shabeen07.comicheros.utils.Constants

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var fragmentLoginBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return fragmentLoginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set default value
        fragmentLoginBinding.edEmail.setText(Constants.EMAIL)
        fragmentLoginBinding.edPassword.setText(Constants.PASSWORD)

        navController = Navigation.findNavController(view)

        // login button click
        fragmentLoginBinding.btnLogin.setOnClickListener {
            validateAndLogin(view)
        }
    }

    private fun validateAndLogin(view: View) {
        val email = fragmentLoginBinding.edEmail.text.toString()
        val password = fragmentLoginBinding.edPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            return
        }
        // login
        val isLoggedIn = viewModel.checkLogin(email, password)
        if (isLoggedIn) {
            Snackbar.make(
                view,
                "Logged In",
                Snackbar.LENGTH_SHORT
            ).show()
            // navigate to home
            navController.navigate(R.id.action_loginFragment_to_homeFragment)
        } else {
            Snackbar.make(
                view,
                "Invalid email or password",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}