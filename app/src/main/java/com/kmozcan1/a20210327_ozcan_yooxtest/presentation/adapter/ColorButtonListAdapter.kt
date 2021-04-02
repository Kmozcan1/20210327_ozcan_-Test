package com.kmozcan1.a20210327_ozcan_yooxtest.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.kmozcan1.a20210327_ozcan_yooxtest.R
import com.kmozcan1.a20210327_ozcan_yooxtest.databinding.ColorButtonListItemBinding
import com.kmozcan1.a20210327_ozcan_yooxtest.presentation.model.ColorVariantUiModel

/**
 * Created by Kadir Mert Özcan on 01-Apr-21.
 */
class ColorButtonListAdapter(
    val colorList: MutableList<ColorVariantUiModel>,
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
        holder.setup(colorList[position], position)
    }

    override fun onAttachedToRecyclerView(recView: RecyclerView) {
        super.onAttachedToRecyclerView(recView)
        recyclerView = recView
    }


    override fun getItemCount(): Int {
        return colorList.size
    }

    /** Adds the list of colors the RecyclerView */
    fun addColorList(colorCodeListItems: List<ColorVariantUiModel>) {
        val startPosition = itemCount
        colorList.addAll(colorCodeListItems)
        notifyItemRangeInserted(startPosition, colorCodeListItems.size)
    }

    /** Clears the RecyclerView data */
    fun clearColorList() {
        val size = colorList.size
        colorList.clear()
        notifyItemRangeRemoved(0, size)
    }

    inner class ColorButtonListViewHolder(
        private val binding: ColorButtonListItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setup(color: ColorVariantUiModel, position: Int) {
            binding.colorButton.run {
                // Sets the button color
                val rgb = color.rgb
                setBackgroundColor(Color.parseColor("#$rgb"))

                binding.colorButton.setOnClickListener {
                    if (selectedButtonPosition != position) {
                        // remove indicator from the unselected button
                        val view = recyclerView
                                .findViewHolderForAdapterPosition(selectedButtonPosition)?.itemView
                        val indicatorImage =
                                view?.findViewById<ShapeableImageView>(
                                        R.id.selectedColorIndicatorImageView)
                        indicatorImage?.visibility = View.INVISIBLE
                        // add indicator to the selected button
                        binding.selectedColorIndicatorImageView.visibility = View.VISIBLE

                        /*val button = view?.findViewById<MaterialButton>(R.id.colorButton)
                        button?.strokeColor =
                                ContextCompat.getColorStateList(
                                        context,
                                        android.R.color.transparent
                                )

                        if (rgb == String.format("%06x",
                                        ContextCompat.getColor(context, R.color.black)
                                                and 0xffffff)) {
                            binding.colorButton.strokeColor =
                                    ContextCompat.getColorStateList(context, R.color.white)
                        } else {
                            binding.colorButton.strokeColor =
                                    ContextCompat.getColorStateList(context, R.color.black)
                        }*/



                    }

                    selectedButtonPosition = position

                    callbackListener.onColorButtonClick(color)
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
        fun onColorButtonClick(colorVariant: ColorVariantUiModel)
    }
}