package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductListItemBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.setImageUrl

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 */
class ProductListAdapter (val productList: MutableList<ProductUiModel>) :
        RecyclerView.Adapter<ProductListAdapter.ProductListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListItemViewHolder {
        // Inflate with data binding
        val binding = ProductListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListItemViewHolder, position: Int) {
        with(productList[position]) {
            holder.bind(brand, category, price, imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // Adds the list of products to the RecyclerView
    fun addProductList(productListResult: List<ProductUiModel>) {
        productList.addAll(productListResult)
        notifyDataSetChanged()
    }

    // For binding the brand, category, price and the image url
    inner class ProductListItemViewHolder(
            private val binding: ProductListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(brand: String, category: String, price: String, imageUrl: String) {
            with(binding) {
                brandNameTextView.text = brand
                categoryTextView.text = category
                priceTextView.text = price
                productImageView.setImageUrl(imageUrl)
            }
        }
    }
}