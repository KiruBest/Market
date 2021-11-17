package com.example.magazine.interfaces

interface LoginView: BaseView, AuthView {
    fun showProgress()
    fun hideProgress()
    fun emailError()
    fun passwordError()
    fun navigationToSignUp()
    fun navigationToHome()
}