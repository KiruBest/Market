package com.example.magazine.ui.page.bottom_menu.shop

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.data.present.category.CategoryAdapter
import com.example.magazine.data.present.category.CategoryPresentor
import com.example.magazine.data.present.products.ProductPresenter
import com.example.magazine.data.present.products.ProductsAdapter
import com.example.magazine.ui.page.AddProductActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Shop : Fragment() {

    companion object {
        private fun newInstance() = Shop()
        private const val ADMIN_UID: String = "wFsiIg6QxzVuHgvKuN69vJevyie2"
        val productsAdapter = ProductsAdapter(ProductPresenter().getProducts())
    }

    private lateinit var viewModel: ShopViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.shop_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        viewModel.progressBarShop = requireView().findViewById(R.id.progressBarShop)

        requireActivity().findViewById<TextView>(R.id.toolbarTextView).text = requireContext().resources.getText(R.string.shop)

        val recyclerViewProducts = requireView().findViewById<RecyclerView>(R.id.recyclerViewProducts)
        recyclerViewProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewProducts.adapter = productsAdapter
        ProductPresenter().fillProducts(viewModel.progressBarShop)

        val recyclerViewCategory = requireView().findViewById<RecyclerView>(R.id.recyclerViewCategory)
        recyclerViewCategory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.setHasFixedSize(true)
        CategoryPresentor().fillCategory(requireContext())
        recyclerViewCategory.adapter = CategoryAdapter(CategoryPresentor().getCategories())

        firebaseAuth = Firebase.auth

        if(chekAdminAccount()){
            val buttonPlus: View = requireView().findViewById(R.id.buttonPlus)

            buttonPlus.visibility = View.VISIBLE

            buttonPlus.setOnClickListener {
                val addProductActivityIntent = Intent(activity, AddProductActivity::class.java)
                startActivity(addProductActivityIntent)
            }
        }
    }

    private fun chekAdminAccount(): Boolean{
        val user = firebaseAuth.currentUser
        return user != null && user.uid == ADMIN_UID
    }
}
