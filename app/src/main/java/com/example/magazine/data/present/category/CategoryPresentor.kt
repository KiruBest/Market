package com.example.magazine.data.present.category

import android.content.Context
import com.example.magazine.R

class CategoryPresentor {
    fun onClick(){

    }

    fun fillCategory(context: Context) {
        CategoryModel.fillCategory(context)
    }

    fun getCategories(): List<CategoryModel>{
        return CategoryModel.categories
    }
}