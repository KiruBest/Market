package com.example.magazine.data.present.search

import com.example.magazine.data.present.products.ProductModel

data class SearchModel(
    val productModel: ProductModel
) {
    companion object{
        val searched: List<SearchModel> = mutableListOf()
    }
}