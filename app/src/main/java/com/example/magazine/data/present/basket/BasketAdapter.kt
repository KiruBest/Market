package com.example.magazine.data.present.basket

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.ui.page.bottom_menu.basket.Basket
import com.example.magazine.ui.page.bottom_menu.shop.Shop
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.basket_fragment.*
import kotlinx.android.synthetic.main.basket_fragment.view.*

class BasketAdapter(val textView: TextView): RecyclerView.Adapter<BasketAdapter.BasketHolder>() {
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
        holder.productTitle?.text = OrderModel.orders[position].productModel.productTitle
        holder.productPrice?.text = OrderModel.orders[position].productModel.productPrice.toString()
        holder.countOfProducts?.setText(OrderModel.orders[position].quantity.toString())

        holder.countOfProducts?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                OrderModel.orders[holder.absoluteAdapterPosition].quantity = p0.toString().toIntOrNull()
                if (OrderModel.orders[holder.absoluteAdapterPosition].quantity != null) {
                    BasketPresenter().sumRecalculate()
                    textView.text = BasketPresenter.getTotal()
                }
                return
            }

            override fun afterTextChanged(p0: Editable?) {
                return
            }

        })

        Picasso.with(holder.itemView.context).load(OrderModel.orders[position].productModel.productPicture).into(holder.productPicture)
    }

    override fun getItemCount(): Int {
        return OrderModel.orders.size
    }
}