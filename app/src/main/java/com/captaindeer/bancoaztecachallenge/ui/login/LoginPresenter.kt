package com.captaindeer.bancoaztecachallenge.ui.login

import com.captaindeer.erikrucksack.ui.login.LoginInterface
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginInterface.View) : LoginInterface.Presenter {

    private var auth: FirebaseAuth? = null

    override fun log(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view.onSuccess(0)
                } else {
                    view.onError("Usuario o contraseña incorrectos \n" + task.exception.toString())
                }
            }
    }

    override fun newUser(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        view.onSuccess(1)
    }

    override fun isLogged() {
        auth = FirebaseAuth.getInstance()
        if (auth?.currentUser != null) {
            view.onSuccess(0)
        }
    }

    override fun loginGoogle(credential: AuthCredential) {
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view.onSuccess(0)
                } else {
                    view.onError("Incorrect user or password")
                }
            }
    }

}