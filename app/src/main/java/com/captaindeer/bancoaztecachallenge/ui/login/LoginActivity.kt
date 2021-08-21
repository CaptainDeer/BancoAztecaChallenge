package com.captaindeer.bancoaztecachallenge.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.captaindeer.bancoaztecachallenge.R
import com.captaindeer.bancoaztecachallenge.databinding.ActivityLoginBinding
import com.captaindeer.bancoaztecachallenge.ui.main.MainActivity
import com.captaindeer.erikrucksack.ui.login.LoginInterface
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginInterface.View {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions

    companion object {
        private const val RC_SIGN_IN = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializer
        configLoginGoogle()
        presenter = LoginPresenter(this)

        //Functions
        presenter.isLogged()

        //Clicks
        binding.goToRegistry.setOnClickListener(this)
        binding.goToEmailLogin.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.btnRegistry.setOnClickListener(this)
        binding.btnLoginGoogle.setOnClickListener(this)

    }

    //Activity Lifecycle
    override fun onStart() {
        super.onStart()
        presenter.isLogged()
    }

    override fun onResume() {
        super.onResume()
        presenter.isLogged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(
                    this,
                    "Error: " + e.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    //Click actions
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.goToRegistry -> {
                goToRegistry()
            }
            R.id.goToEmailLogin -> {
                goToEmailLogin()
            }
            R.id.btnLogin -> {
                if (binding.etEmailLogin.text.isNullOrEmpty() || binding.etPasswordLogin.text.isNullOrEmpty())
                    onError("Please fill all the fields")
                else
                    readEditText()
            }
            R.id.btnRegistry -> {
                if (binding.etNameRegistry.text?.isEmpty()!! || binding.etEmailRegistry.text?.isEmpty()!! || binding.etPasswordRegistry.text?.isEmpty()!! || binding.etPasswordRepRegistry.text?.isEmpty()!!)
                    onError("Please fill all the fields")
                else if (binding.etPasswordRegistry.text.toString() != binding.etPasswordRepRegistry.text.toString())
                    onError("The passwords doesn´t match")
                else
                    setNewUser()
            }
            R.id.btnLoginGoogle -> {
                signInGoogle()
            }
        }
    }

    //Activity errors
    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    //Activity Misions
    override fun onSuccess(flag: Int) {
        when (flag) {
            0 -> {
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
            1 -> {
                goToEmailLogin()
                Toast.makeText(this, "Use your new account!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    //Reading user data
    private fun readEditText() {
        presenter.log(
            binding.etEmailLogin.text!!.trim().toString(),
            binding.etPasswordLogin.text!!.trim().toString()
        )
    }

    //Configure Google Sign in
    private fun configLoginGoogle() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    //Sign in Google
    private fun signInGoogle() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    //Set new user data
    private fun setNewUser() {
        presenter.newUser(
            binding.etEmailRegistry.text!!.trim().toString(),
            binding.etPasswordRegistry.text!!.trim().toString()
        )
    }

    //Change FrameLayout
    private fun goToRegistry() {
        binding.flEmailLogin.visibility = View.GONE
        binding.flRegistry.visibility = View.VISIBLE
    }

    private fun goToEmailLogin() {
        binding.flRegistry.visibility = View.GONE
        binding.flEmailLogin.visibility = View.VISIBLE
    }

    private fun firebaseAuthGoogle(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        presenter.loginGoogle(credential)
    }
}