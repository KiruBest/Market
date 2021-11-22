package com.example.magazine.data.present.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.data.present.basket.BasketAdapter
import com.example.magazine.data.present.basket.OrderModel
import com.example.magazine.data.present.search.SearchAdapter.SearchHolder
import com.example.magazine.ui.page.productpage.ProductPage
import com.squareup.picasso.Picasso

class SearchAdapter : RecyclerView.Adapter<SearchHolder>() {
    inner class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var productTitle: TextView? = null
        var productPrice: TextView? = null
        var productPicture: ImageView? = null

        init {
            productTitle = itemView.findViewById(R.id.productTitle)
            productPrice = itemView.findViewById(R.id.productPrice)
            productPicture = itemView.findViewById(R.id.productPicture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_in_shop_example_layout, parent, false)
        return SearchHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.productTitle?.text = SearchModel.searched[position].productModel.productTitle
        holder.productPrice?.text = SearchModel.searched[position].productModel.productPrice.toString()
        Picasso.with(holder.itemView.context)
            .load(SearchModel.searched[position].productModel.productPicture)
            .into(holder.productPicture)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductPage::class.java)
            intent.putExtra("product", SearchModel.searched[position].productModel)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return SearchModel.searched.size
    }
}