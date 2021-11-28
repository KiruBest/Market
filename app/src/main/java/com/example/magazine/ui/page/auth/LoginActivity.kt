package com.example.magazine.ui.page.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.example.magazine.*
import com.example.magazine.interfaces.LoginView
import com.example.magazine.ui.page.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), LoginView, View.OnClickListener {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var noAccount: TextView
    private lateinit var buttonCreateAccount: Button
    private lateinit var progressBarSignUp: ProgressBar
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindViews()

        val currentUser = firebaseAuth.currentUser
        if(currentUser != null){
            Toast.makeText(this, "You signed in", Toast.LENGTH_SHORT).show()
        }
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

    override fun passwordError() {
        password.error = "Password field can't be empty"
    }

    override fun navigationToSignUp() {
        val intent = Intent(getContext(), RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigationToHome() {
        val intent = Intent(getContext(), MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun bindViews() {
        email = findViewById(R.id.editTextEmailAddress)
        password = findViewById(R.id.password)
        noAccount = findViewById(R.id.noAccount)
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount)
        progressBarSignUp = findViewById(R.id.progressBarSignIn)
        firebaseAuth = FirebaseAuth.getInstance()

        buttonCreateAccount.setOnClickListener(this)

        noAccount.setOnClickListener {
            navigationToSignUp()
        }

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
            TextUtils.isEmpty(email.text.toString().trim()) -> emailError()
            TextUtils.isEmpty(password.text.toString().trim()) -> passwordError()

            else -> {
                try {
                    showProgress()
                    firebaseAuth.signInWithEmailAndPassword(
                        email.text.toString().trim(),
                        password.text.toString().trim()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = firebaseAuth.currentUser

                            Toast.makeText(this, R.string.successful, Toast.LENGTH_SHORT).show()

                            navigationToHome()
                        } else {
                            hideProgress()
                            showAuthError()
                        }
                    }
                } catch (e: Exception) {
                    Log.d("sign in error", e.toString())
                }
            }
        }
    }
}