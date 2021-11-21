package com.example.magazine.data.present.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.ui.page.productpage.ProductPage
import com.squareup.picasso.Picasso

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var productTitle: TextView? = null
        var productPrice: TextView? = null
        var productPicture: ImageView? = null

        init {
            productTitle = itemView.findViewById(R.id.productTitle)
            productPrice = itemView.findViewById(R.id.productPrice)
            productPicture = itemView.findViewById(R.id.productPicture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.productTitle?.text = FavoriteModel.favoritesList[position].product.productTitle
        holder.productPrice?.text = FavoriteModel.favoritesList[position].product.productPrice.toString()
        if(FavoriteModel.favoritesList[position].product.productPicture != ""){
            Picasso.with(holder.itemView.context).load(FavoriteModel.favoritesList[position]
                .product.productPicture).into(holder.productPicture)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductPage::class.java)
            intent.putExtra("product", FavoriteModel.favoritesList[holder.absoluteAdapterPosition].product)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return FavoriteModel.favoritesList.size
    }
}