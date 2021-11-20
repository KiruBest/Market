package com.example.magazine.data.present.basket

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.Editable
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

data class OrderModel(
    val productModel: ProductModel,
    var quantity: Int? = 1
) : Serializable{
    companion object{
        var orders: List<OrderModel> = mutableListOf()
    }
}