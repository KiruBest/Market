package com.example.magazine.data.present.basket

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.JsonWriter
import com.example.magazine.data.present.products.ProductModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.io.Serializable
import java.util.prefs.Preferences

class OrderModel : Serializable{
    companion object{
        var pids: List<String> = mutableListOf()
        val orders = mutableListOf<ProductModel>()

        fun add(pid: String, product: ProductModel, context: Context){
            (this.pids as MutableList).add(pid)
            this.orders.add(product)

            OrderModel().saveOrders(context, product)
        }

        fun get(context: Context, product: ProductModel){
            OrderModel().loadOrders(context, product)
        }
    }

    private fun saveOrders(context: Context, product: ProductModel) {
        val user = Firebase.auth.currentUser

        if (user != null){
            val fileName = product.pid

            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(product.productTitle.toByteArray())
            }
        }
    }

    private fun loadOrders(context: Context, product: ProductModel){
        val user = Firebase.auth.currentUser

        if (user != null){
            val fileName = product.pid

            context.openFileInput(fileName).bufferedReader().readLine()
        }
    }
}