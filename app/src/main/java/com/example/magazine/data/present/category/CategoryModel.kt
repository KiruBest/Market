package com.example.magazine.data.present.category

import android.content.Context
import com.example.magazine.R

data class CategoryModel(val id: Int, val category: String) {
    companion object{
        val categories: List<CategoryModel> = mutableListOf()

        fun fillCategory(context: Context) {
            for((index, item) in context.resources.getStringArray(R.array.category_string_array).toList().withIndex())
                    (categories as MutableList).add(CategoryModel(index, item))
        }
    }
}