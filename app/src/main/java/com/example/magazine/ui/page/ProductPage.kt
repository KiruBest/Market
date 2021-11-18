package com.example.magazine.ui.page

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.magazine.R
import com.example.magazine.data.present.basket.OrderModel
import com.example.magazine.data.present.products.ProductModel
import com.example.magazine.interfaces.BaseView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.lang.Exception

class ProductPage : AppCompatActivity(), BaseView, View.OnClickListener {
    var arguments: Bundle? = null

    private lateinit var productTitle: TextView
    private lateinit var productPrice: TextView
    private lateinit var productDescription: TextView
    private lateinit var productPicture: ImageView
    private lateinit var buttonAddToBag: Button
    private lateinit var buttonBack: ImageButton
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var product: ProductModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        bindViews()

        loadProduct()
    }

    override fun bindViews() {
        arguments = intent.extras

        if(arguments != null) product = arguments!!["product"] as ProductModel

        productTitle = findViewById<TextView>(R.id.productTitle)
        productPrice = findViewById<TextView>(R.id.productPrice)
        productDescription = findViewById<TextView>(R.id.productDescription)
        productPicture = findViewById<ImageView>(R.id.productPicture)
        buttonAddToBag = findViewById<Button>(R.id.buttonAddToBag)
        buttonBack = findViewById<ImageButton>(R.id.buttonBack)

        firebaseAuth = Firebase.auth

        buttonAddToBag.setOnClickListener(this)

        buttonBack.setOnClickListener {
            finish()
        }
    }

    override fun getContext(): Context {
        return this
    }

    private fun loadProduct(){
        productTitle.text = product.productTitle
        productPrice.text = product.productPrice.toString()
        productDescription.text = product.productDescription
        pictureLoad(product.productPicture)
    }

    override fun onClick(p0: View?) {
        val user = firebaseAuth.currentUser

        if (user != null){
            if(!OrderModel.orders.contains(OrderModel(product))) {
                (OrderModel.orders as MutableList).add(OrderModel(product))
            } else {
                Toast.makeText(this, "Уже в корзине!", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(this, R.string.must_be_sign_in, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun pictureLoad(url: String) {
        try {
            Picasso.with(this).load(url).into(productPicture)
        }catch (e: Exception){
            Log.d("pictureLoadFailed", e.stackTraceToString())
        }
    }
}