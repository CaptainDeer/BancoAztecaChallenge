package com.captaindeer.erikrucksack.ui.login

import com.google.firebase.auth.AuthCredential

interface LoginInterface {

    interface Presenter {
        fun log(email: String, password: String)
        fun newUser(email: String, password: String)
        fun isLogged()
        fun loginGoogle(credential: AuthCredential)
    }

    interface View {
        fun onError(msg: String)
        fun onSuccess(flag: Int)
    }

}