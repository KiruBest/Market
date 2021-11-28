package com.example.magazine.data.present.basket

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.magazine.R
import com.example.magazine.ui.page.bottom_menu.basket.Basket
import kotlinx.android.synthetic.main.basket_fragment.*

class BasketPresenter {
    companion object{
        private var total: Int = 0

        fun getTotal(): String = "$total$"
        fun getTotalFirst(view: View): String {
            BasketPresenter().sumUp(view)
            return "$total$"
        }
    }

    fun sumUp(view: View){
        total = 0

        if(OrderModel.orders.isNotEmpty()){
            view.findViewById<LinearLayout>(R.id.totalLayout).visibility = View.VISIBLE
            view.findViewById<Button>(R.id.buttonBuyProducts).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.basketIsEmpty).visibility = View.INVISIBLE

            for (order in OrderModel.orders){
                val price = order.productModel.productPrice.toString().subSequence(0,
                    order.productModel.productPrice.toString().lastIndex).toString().toIntOrNull()
                total += price!!* order.quantity!!
            }
        }
    }

    fun sumRecalculate(){
        if(OrderModel.orders.isNotEmpty()){
            total = 0

            for (order in OrderModel.orders){
                val price = order.productModel.productPrice.toString().subSequence(0,
                    order.productModel.productPrice.toString().lastIndex).toString().toIntOrNull()
                total += price!!* order.quantity!!
            }
        }
    }
}