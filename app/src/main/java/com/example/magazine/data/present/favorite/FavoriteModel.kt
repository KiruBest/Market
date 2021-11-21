package com.example.magazine.data.present.favorite

import com.example.magazine.data.present.products.ProductModel

data class FavoriteModel(
    val product: ProductModel
) {
    companion object{
        val favoritesList: List<FavoriteModel> = mutableListOf()
    }
}