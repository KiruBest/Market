package com.example.magazine.data.present.products

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.*
import com.example.magazine.ui.page.ProductPage
import com.squareup.picasso.Picasso

class ProductsAdapter(private val products: List<ProductModel>): RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {

    inner class ProductsHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var productTitle: TextView? = null
        var productPrice: TextView? = null
        var productPicture: ImageView? = null

        init {
            productTitle = itemView.findViewById(R.id.productTitle)
            productPrice = itemView.findViewById(R.id.productPrice)
            productPicture = itemView.findViewById(R.id.productPicture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)

        return ProductsHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        holder.productTitle?.text = products[position].productTitle
        holder.productPrice?.text = products[position].productPrice.toString()
        if(products[position].productPicture != ""){
            Picasso.with(holder.itemView.context).load(products[position].productPicture).into(holder.productPicture)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductPage::class.java)
            intent.putExtra("product", products[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}