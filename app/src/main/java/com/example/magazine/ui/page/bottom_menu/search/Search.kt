package com.example.magazine.ui.page.bottom_menu.search

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.data.present.basket.BasketAdapter
import com.example.magazine.data.present.favorite.FavoriteAdapter
import com.example.magazine.data.present.products.ProductModel
import com.example.magazine.data.present.search.SearchAdapter
import com.example.magazine.data.present.search.SearchModel

class Search : Fragment() {

    companion object {
        fun newInstance() = Search()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.searchForTitle = requireView().findViewById(R.id.searchForTitle)
        viewModel.recyclerViewSearchProducts = requireView().findViewById(R.id.recyclerViewSearchProducts)
        val searchAdapter = SearchAdapter()
        viewModel.recyclerViewSearchProducts.adapter = searchAdapter
        viewModel.recyclerViewSearchProducts.layoutManager = LinearLayoutManager(requireContext())

        viewModel.searchForTitle.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                for (product in ProductModel.allProducts) {
                    if(p0!!.isNotEmpty() && product.productTitle.contains(p0, true)){
                        if(!SearchModel.searched.contains(SearchModel(product))){
                            (SearchModel.searched as MutableList).add(SearchModel(product))
                            searchAdapter.notifyDataSetChanged()
                        }
                    } else if (p0.isEmpty()) {
                        (SearchModel.searched as MutableList).clear()
                        searchAdapter.notifyDataSetChanged()
                    }else if (!product.productTitle.contains(p0, true) && SearchModel.searched.contains(SearchModel(product))) {
                        (SearchModel.searched as MutableList).remove(SearchModel(product))
                        searchAdapter.notifyDataSetChanged()
                    }
                }
                return
            }

            override fun afterTextChanged(p0: Editable?) {
                return
            }

        })
    }

}