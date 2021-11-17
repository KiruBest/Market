package com.example.magazine.interfaces

interface RegisteryView: BaseView, AuthView {
    fun showProgress()
    fun hideProgress()
    fun emailError()
    fun usernameError()
    fun passwordError()
    fun navigationToHome()
    fun navigationToSignIn()
}