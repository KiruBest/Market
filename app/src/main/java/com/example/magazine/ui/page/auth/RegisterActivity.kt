package com.example.magazine.ui.page.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.magazine.R
import com.example.magazine.interfaces.RegisteryView
import com.example.magazine.ui.page.MainActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity(), RegisteryView, View.OnClickListener {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var buttonCreateAccount: Button
    private lateinit var progressBarSignUp: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        bindViews()
    }

    override fun showProgress() {
        progressBarSignUp.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBarSignUp.visibility = View.INVISIBLE
    }

    override fun emailError() {
        email.error = "Email field can't be empty"
    }

    override fun usernameError() {
        username.error = "Username field can't be empty"
    }

    override fun passwordError() {
        password.error = "Password field can't be empty"
    }

    override fun navigationToHome() {
        val intent = Intent(getContext(), MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigationToSignIn() {
        val intent = Intent(getContext(), LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun bindViews() {
        username= findViewById(R.id.username)
        email = findViewById(R.id.editTextEmailAddress)
        password = findViewById(R.id.password)
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount)
        progressBarSignUp = findViewById(R.id.progressBarSignUp)

        firebaseAuth = FirebaseAuth.getInstance()

        buttonCreateAccount.setOnClickListener(this)

        findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            finish()
        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun showAuthError() {
        Toast.makeText(this, R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
            when {
                TextUtils.isEmpty(username.text.toString().trim()) -> usernameError()
                TextUtils.isEmpty(email.text.toString().trim()) -> emailError()
                TextUtils.isEmpty(password.text.toString().trim()) -> passwordError()

                else -> {
                    try {
                        showProgress()
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                            email.text.toString().trim(),
                            password.text.toString().trim()
                        ).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user = firebaseAuth.currentUser

                                    Toast.makeText(this, R.string.successful, Toast.LENGTH_SHORT).show()

                                    hideProgress()

                                    navigationToHome()
                                } else {
                                    hideProgress()
                                    showAuthError()
                                }
                            }
                    }catch (e:Exception){
                        Log.d("create account error", e.toString())
                    }
            }
        }
    }
}