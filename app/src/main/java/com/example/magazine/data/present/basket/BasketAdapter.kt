package com.example.magazine.data.present.basket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.ui.page.bottom_menu.basket.Basket
import com.example.magazine.ui.page.bottom_menu.shop.Shop
import com.squareup.picasso.Picasso

class BasketAdapter: RecyclerView.Adapter<BasketAdapter.BasketHolder>() {
    class BasketHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var productTitle: TextView? = null
        var productPrice: TextView? = null
        var productPicture: ImageView? = null
        var countOfProducts: EditText? = null

        init {
            productTitle = itemView.findViewById(R.id.productTitle)
            productPrice = itemView.findViewById(R.id.productPrice)
            productPicture = itemView.findViewById(R.id.productPicture)
            countOfProducts = itemView.findViewById(R.id.countOfProducts)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.basket_example_layout, parent, false)
        return BasketHolder(itemView)
    }

    override fun onBindViewHolder(holder: BasketHolder, position: Int) {
        holder.productTitle?.text = OrderModel.orders[position].productTitle
        holder.productPrice?.text = OrderModel.orders[position].productPrice.toString()
        holder.countOfProducts?.setText("1")
        Picasso.with(holder.itemView.context).load(OrderModel.orders[position].productPicture).into(holder.productPicture)
    }

    override fun getItemCount(): Int {
        return OrderModel.pids.size
    }
}