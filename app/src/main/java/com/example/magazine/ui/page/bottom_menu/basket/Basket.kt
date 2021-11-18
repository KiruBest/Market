package com.example.magazine.ui.page.bottom_menu.basket

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.data.present.basket.BasketAdapter
import com.example.magazine.data.present.basket.OrderModel
import com.example.magazine.data.present.products.ProductModel
import com.example.magazine.ui.page.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Basket : Fragment() {

    companion object {
        fun newInstance() = Basket()
    }

    private lateinit var viewModel: BasketViewModel

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var recyclerViewBasket: RecyclerView

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.basket_fragment, container, false)

        firebaseAuth = Firebase.auth
        val user = firebaseAuth.currentUser

        Handler().postDelayed({
            if(user == null){
                Toast.makeText(activity, R.string.must_be_sign_in, Toast.LENGTH_SHORT).show()

                val loginActivityIntent = Intent(activity, LoginActivity::class.java)
                startActivity(loginActivityIntent)
            }
        }, 2000)

        recyclerViewBasket = view.findViewById(R.id.recyclerViewBasket)
        recyclerViewBasket.layoutManager = LinearLayoutManager(requireContext())
        val basketAdapter = BasketAdapter()
        recyclerViewBasket.adapter = basketAdapter
        basketAdapter.notifyDataSetChanged()

        if(OrderModel.orders.isNotEmpty()){
            view.findViewById<LinearLayout>(R.id.totalLayout).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.basketIsEmpty).visibility = View.INVISIBLE

            var total: Int = 0
            for (order in OrderModel.orders){
                val price = order.productModel.productPrice.toString().subSequence(0,
                    order.productModel.productPrice.toString().lastIndex).toString().toIntOrNull()
                total += price!!*order.quantity
            }

            view.findViewById<TextView>(R.id.totalSum).text = "$total$"
        }

        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        // TODO: Use the ViewModel
    }

}