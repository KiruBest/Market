package com.example.magazine.ui.page.bottom_menu.favorites

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("StaticFieldLeak")
class FavoritesViewModel : ViewModel() {
    lateinit var recyclerViewFavorites: RecyclerView
    lateinit var isEmptyFavorite: TextView
}