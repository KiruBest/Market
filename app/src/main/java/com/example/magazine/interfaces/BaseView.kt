package com.example.magazine.interfaces

import android.content.Context

interface BaseView {
    fun bindViews()
    fun getContext(): Context
}