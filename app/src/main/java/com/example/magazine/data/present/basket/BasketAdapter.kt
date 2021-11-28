package com.example.magazine.data.present.basket

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.ui.page.MainActivity
import com.example.magazine.ui.page.auth.LoginActivity
import com.example.magazine.ui.page.bottom_menu.basket.Basket
import com.example.magazine.ui.page.bottom_menu.shop.Shop
import com.example.magazine.ui.page.productpage.ProductPage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.basket_fragment.*
import kotlinx.android.synthetic.main.basket_fragment.view.*
import kotlinx.coroutines.delay
import java.util.logging.Handler

class BasketAdapter(val textView: TextView, val view: View): RecyclerView.Adapter<BasketAdapter.BasketHolder>() {
    class BasketHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var productTitle: TextView? = null
        var productPrice: TextView? = null
        var productPicture: ImageView? = null
        var countOfProducts: EditText? = null
        var buttonInc: ImageButton? = null
        var buttonDec: ImageButton? = null
        var buttonDeleteItem: ImageButton? = null

        init {
            productTitle = itemView.findViewById(R.id.productTitle)
            productPrice = itemView.findViewById(R.id.productPrice)
            productPicture = itemView.findViewById(R.id.productPicture)
            countOfProducts = itemView.findViewById(R.id.countOfProducts)
            buttonInc = itemView.findViewById(R.id.buttonInc)
            buttonDec = itemView.findViewById(R.id.buttonDec)
            buttonDeleteItem = itemView.findViewById(R.id.buttonDeleteItem)
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

        holder.buttonInc?.setOnClickListener {
            holder.countOfProducts?.setText((holder.countOfProducts?.text.toString().toInt() + 1)
                .toString())
        }

        holder.buttonDec?.setOnClickListener {
            holder.countOfProducts?.setText((holder.countOfProducts?.text.toString().toInt() - 1)
                .toString())
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductPage::class.java)
            intent.putExtra("product", OrderModel.orders[holder.absoluteAdapterPosition].productModel)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            removeItem(holder)
            return@setOnLongClickListener true
        }

        holder.buttonDeleteItem!!.setOnClickListener {
            removeItem(holder)
        }

        Picasso.with(holder.itemView.context).load(OrderModel.orders[position].productModel.productPicture).into(holder.productPicture)
    }

    private fun removeItem(holder: BasketHolder){
        (OrderModel.orders as MutableList).removeAt(holder.absoluteAdapterPosition)
        notifyItemRemoved(holder.absoluteAdapterPosition)
        notifyItemRangeChanged(holder.absoluteAdapterPosition, itemCount)
        MainActivity.changeBandageInBottomMenu()

        android.os.Handler().postDelayed({
            if(OrderModel.orders.isEmpty()){
                view.findViewById<LinearLayout>(R.id.totalLayout).visibility = View.INVISIBLE
                view.findViewById<Button>(R.id.buttonBuyProducts).visibility = View.INVISIBLE
                view.findViewById<TextView>(R.id.basketIsEmpty).visibility = View.VISIBLE
            }
        }, 200)
    }

    override fun getItemCount(): Int {
        return OrderModel.orders.size
    }
}