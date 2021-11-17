package com.example.magazine.data.present.products

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.example.magazine.R
import com.example.magazine.interfaces.Saleble
import com.google.firebase.database.*
import java.io.Serializable

data class ProductModel(
    val pid:String,
    val productPicture:String,
    val productTitle:String,
    val productDescription:String,
    val productPrice:Any,
    val productSize:String,
    val category: String): Saleble, Serializable {

    companion object{
        val allProducts: List<ProductModel> = mutableListOf()
        val categoriesProducts: List<ProductModel> = mutableListOf()

        @SuppressLint("NotifyDataSetChanged")
        fun fillProducts(progressBar: ProgressBar){
            val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Products")
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    (allProducts as MutableList).clear()
                    (categoriesProducts as MutableList).clear()

                    progressBar.visibility = ProgressBar.VISIBLE

                    for (childDataSnapshot in dataSnapshot.children){
                        var productTitle: String = ""
                        var productPrice: String = ""
                        var productDescription: String = ""
                        var productPicture: String = ""
                        var category: String = ""
                        var pid: String = ""

                        for (child in childDataSnapshot.children){
                            when {
                                child.key.toString() == "productTitle" -> {
                                    productTitle = child.value.toString()
                                }
                                child.key.toString() == "productPrice" -> {
                                    productPrice = child.value.toString()
                                }
                                child.key.toString() == "productDescription" -> {
                                    productDescription = child.value.toString()
                                }
                                child.key.toString() == "productPicture" -> {
                                    productPicture = child.value.toString()
                                }
                                child.key.toString() == "category" -> {
                                    category = child.value.toString()
                                }
                                child.key.toString() == "pid" -> {
                                    pid = child.value.toString()
                                }
                            }
                        }

                        allProducts.add(ProductModel(pid, productPicture, productTitle, productDescription,
                            "$productPrice$", "", category))
                        (categoriesProducts as MutableList).add(allProducts.last())

                        ProductPresenter().getProductAdapter().notifyDataSetChanged()
                    }

                    progressBar.visibility = ProgressBar.INVISIBLE
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("databaseError", databaseError.toString())
                }
            }
            databaseReference.addValueEventListener(valueEventListener)
            databaseReference.onDisconnect()
        }
    }

    override fun getPrice(): Int {
        return productPrice as Int
    }

    override fun getTitle(): String {
        return productTitle
    }
}