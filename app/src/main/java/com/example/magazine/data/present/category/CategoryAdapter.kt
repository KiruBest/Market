package com.example.magazine.data.present.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magazine.R
import com.example.magazine.data.present.products.ProductModel
import com.example.magazine.data.present.products.ProductPresenter
import com.example.magazine.data.present.products.ProductsAdapter
import com.example.magazine.ui.page.bottom_menu.shop.Shop

class CategoryAdapter(private val categories: List<CategoryModel>): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>(){
    private lateinit var selectedItem: View

    class CategoryHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var categoryTitle: TextView? = null

        init {
            categoryTitle = itemView.findViewById(R.id.categoryTitle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)

        return CategoryHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        if (position != 0) holder.categoryTitle?.text = categories[position].category
        else{
            holder.categoryTitle?.text = holder.itemView.resources.getString(R.string.all_categories)
            selectedItem = holder.itemView
            selectedItem.isSelected = true
        }

        holder.itemView.setOnClickListener {
            selectedItem.isSelected = false
            holder.itemView.isSelected = true
            selectedItem = holder.itemView

            (ProductModel.categoriesProducts as MutableList).clear()

            if (categories[position].id == 0) {
                ProductModel.categoriesProducts.addAll(ProductModel.allProducts)
            } else {
                for (product in ProductModel.allProducts) {
                    if (product.category.lowercase() == categories[position].category.lowercase()) {
                        ProductModel.categoriesProducts.add(product)
                    }
                }
            }

            ProductPresenter().getProductAdapter().notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}