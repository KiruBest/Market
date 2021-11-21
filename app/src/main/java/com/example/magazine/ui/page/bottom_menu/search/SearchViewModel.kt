package com.example.magazine.ui.page.bottom_menu.search

import android.annotation.SuppressLint
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("StaticFieldLeak")
class SearchViewModel : ViewModel() {
    lateinit var searchForTitle: EditText
    lateinit var recyclerViewSearchProducts: RecyclerView
}