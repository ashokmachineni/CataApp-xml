package com.example.cats.presentation.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cats.R
import com.example.cats.databinding.ItemCategoryBinding
import com.example.cats.utility.setOnSafeClickListener

/**
 * This is adapter class for show list of category on Top of the screen.
 * User can click on category and filter that list
 */
class CategoryAdapter(
    var activity: Activity,
    var categoryList: List<CatCategory>,
    var selectedIndex: Int,
    var onCategoryListener: OnCategoryListener
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, catCategory: CatCategory) {
            binding.categoryName.text = catCategory.value
            if (selectedIndex == position) {
                binding.categoryBack.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray
                    )
                )
            } else {
                binding.categoryBack.setCardBackgroundColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.purple_200
                    )
                )
            }

            binding.root.setOnSafeClickListener {
                val tempPos = selectedIndex
                selectedIndex = position
                notifyItemChanged(tempPos)
                notifyItemChanged(selectedIndex)
                onCategoryListener.onSelectCategory(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun changeIndex(i: Int) {
        selectedIndex = i
    }

    public interface OnCategoryListener {
        fun onSelectCategory(pos: Int)
    }
}