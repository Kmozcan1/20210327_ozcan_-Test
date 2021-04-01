package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ColorButtonListItemBinding

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 */
class ColorButtonListAdapter(
    val rgbList: MutableList<String>,
    private val callbackListener: CallbackListener
) :
    RecyclerView.Adapter<ColorButtonListAdapter.ColorButtonListViewHolder>() {

    lateinit var recyclerView: RecyclerView
    var selectedButtonPosition: Int = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ColorButtonListViewHolder {
        val binding = ColorButtonListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorButtonListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorButtonListViewHolder, position: Int) {
        holder.setup(rgbList[position], position)
    }

    override fun onAttachedToRecyclerView(recView: RecyclerView) {
        super.onAttachedToRecyclerView(recView)
        recyclerView = recView
    }


    override fun getItemCount(): Int {
        return rgbList.size
    }

    // Adds the list of products image urls to the RecyclerView
    fun addImageUrlList(colorCodeListItems: List<String>) {
        val startPosition = itemCount
        rgbList.addAll(colorCodeListItems)
        notifyItemRangeInserted(startPosition, colorCodeListItems.size)
    }

    // Clears the RecyclerView data
    fun clearImageUrlList() {
        val size = rgbList.size
        rgbList.clear()
        notifyItemRangeRemoved(0, size)
    }

    inner class ColorButtonListViewHolder(
        private val binding: ColorButtonListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setup(rgb: String, position: Int) {
            binding.colorButton.run {
                // Sets the button color
                setBackgroundColor(Color.parseColor("#$rgb"))

                binding.colorButton.setOnClickListener {
                    if (selectedButtonPosition != position) {
                        // remove stroke from the unselected button
                        val view = recyclerView
                                .findViewHolderForAdapterPosition(selectedButtonPosition)?.itemView
                        val button = view?.findViewById<MaterialButton>(R.id.colorButton)
                        button?.strokeColor =
                                ContextCompat.getColorStateList(
                                        context,
                                        android.R.color.transparent
                                )

                        // add black stroke to the selected button (or white if the color is black)
                        if (rgb == String.format("%06x",
                                        ContextCompat.getColor(context, R.color.black)
                                                and 0xffffff)) {
                            binding.colorButton.strokeColor =
                                    ContextCompat.getColorStateList(context, R.color.white)
                        } else {
                            binding.colorButton.strokeColor =
                                    ContextCompat.getColorStateList(context, R.color.black)
                        }
                    }

                    selectedButtonPosition = position

                    callbackListener.onColorButtonClick()
                }

                // Manually click the first button on setup
                if (position == 0) {
                    performClick()
                }
            }
        }
    }


    /** Listener interface to make callbacks to the view that uses ColorButtonView */
    interface CallbackListener {
        fun onColorButtonClick()
    }
}