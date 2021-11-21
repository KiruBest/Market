package com.example.magazine.ui.page.bottom_menu.favorites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.magazine.R
import com.example.magazine.data.present.favorite.FavoriteAdapter
import com.example.magazine.data.present.favorite.FavoriteModel

class Favorites : Fragment() {

    companion object {
        fun newInstance() = Favorites()
        val favoriteAdapter = FavoriteAdapter()
    }

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        viewModel.recyclerViewFavorites = requireView().findViewById(R.id.recyclerViewFavorite)
        viewModel.isEmptyFavorite = requireView().findViewById(R.id.isEmptyFavorite)

        if(FavoriteModel.favoritesList.isNotEmpty()) {
            viewModel.isEmptyFavorite.visibility = View.INVISIBLE

            viewModel.recyclerViewFavorites.adapter = favoriteAdapter
            viewModel.recyclerViewFavorites.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onStart() {
        super.onStart()
        if (FavoriteModel.favoritesList.isEmpty()) viewModel.isEmptyFavorite.visibility = View.VISIBLE
    }
}