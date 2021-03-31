package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ProductListItemBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ProductUiModel
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.setImageUrl
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.view.ProductListFragment

/**
 * Created by Kadir Mert Özcan on 28-Mar-21.
 */
class ProductListAdapter(
    val productList: MutableList<ProductUiModel>,
    private val listener: (String) -> Unit) :
        RecyclerView.Adapter<ProductListAdapter.ProductListItemViewHolder>() {

    lateinit var recyclerView: RecyclerView

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListItemViewHolder {
        // Inflate with data binding
        val binding = ProductListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ProductListItemViewHolder, position: Int) {
        with(productList[position]) {
            holder.bind(brand, category, price, imageUrl)
        }
    }

    override fun onAttachedToRecyclerView(recView: RecyclerView) {
        super.onAttachedToRecyclerView(recView)
        recyclerView = recView
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // Adds the list of products to the RecyclerView
    fun addProductList(productListResult: List<ProductUiModel>) {
        val startPosition = itemCount
        productList.addAll(productListResult)
        notifyItemRangeInserted(startPosition, productListResult.size)
    }

    // Clears the RecyclerView data
    fun clearProductList() {
        val size = productList.size
        productList.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun scrollToTop() {
        recyclerView.layoutManager?.scrollToPosition(0)

    }

    // For binding the brand, category, price, image url and the item click listener
    inner class ProductListItemViewHolder(
            private val binding: ProductListItemBinding,
            val listener: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(brand: String, category: String, price: String, imageUrl: String) {
            with(binding) {
                brandNameTextView.text = brand
                categoryTextView.text = category
                priceTextView.text = price
                productImageView.setImageUrl(imageUrl)
                root.setOnClickListener{
                    listener(brand)
                }
            }
        }
    }
}