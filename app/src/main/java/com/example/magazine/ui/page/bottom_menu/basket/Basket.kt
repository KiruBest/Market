package com.example.magazine.ui.page.bottom_menu.basket

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.data.present.basket.BasketAdapter
import com.example.magazine.data.present.basket.BasketPresenter
import com.example.magazine.data.present.basket.OrderModel
import com.example.magazine.ui.page.auth.LoginActivity
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

    private lateinit var totalSum: TextView

    internal lateinit var view: View

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.basket_fragment, container, false)

        firebaseAuth = Firebase.auth
        val user = firebaseAuth.currentUser

        Handler().postDelayed({
            if(user == null){
                Toast.makeText(activity, R.string.must_be_sign_in, Toast.LENGTH_SHORT).show()

                val loginActivityIntent = Intent(activity, LoginActivity::class.java)
                startActivity(loginActivityIntent)
            }
        }, 2000)

        totalSum = view.findViewById(R.id.totalSum)
        totalSum.text = BasketPresenter.getTotalFirst(view)

        recyclerViewBasket = view.findViewById(R.id.recyclerViewBasket)
        recyclerViewBasket.layoutManager = LinearLayoutManager(requireContext())
        val basketAdapter = BasketAdapter(totalSum)
        recyclerViewBasket.adapter = basketAdapter
        basketAdapter.notifyDataSetChanged()

        view.findViewById<Button>(R.id.buttonBuyProducts).setOnClickListener {
            if(OrderModel.orders.isNotEmpty()) Toast.makeText(requireContext(),
                BasketPresenter.getTotal(), Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[BasketViewModel::class.java]
        // TODO: Use the ViewModel
    }

}