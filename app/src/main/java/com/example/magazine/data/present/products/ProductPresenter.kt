package com.example.magazine.data.present.products

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import com.example.magazine.R
import com.example.magazine.ui.page.bottom_menu.shop.Shop
import com.google.firebase.database.*

class ProductPresenter {
    fun fillProducts(progressBar: ProgressBar){
        ProductModel.fillProducts(progressBar)
    }

    fun getProducts(): List<ProductModel>{
        return ProductModel.categoriesProducts
    }

    fun getProductAdapter(): ProductsAdapter{
        return Shop.productsAdapter
    }
}