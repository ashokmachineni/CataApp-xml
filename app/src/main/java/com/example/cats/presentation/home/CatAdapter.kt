package com.example.cats.presentation.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cats.databinding.ItemCatBinding
import com.example.cats.domain.model.CatDetail
import com.example.cats.utility.setOnSafeClickListener

/**
 * This is adapter class for show list of items
 * User can click on category
 */
class CatAdapter(
    var activity: Activity,
    var catList: List<CatDetail>,
    var onCatListener: OnCatListener
) :
    RecyclerView.Adapter<CatAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, catDetail: CatDetail) {
            binding.catName.text = catDetail.name
            binding.catEnergy.text = catDetail.energy_level.toString()
            Glide.with(activity)
                .load("https://cdn2.thecatapi.com/images/${catDetail.reference_image_id}.jpg")
                .into(binding.catImage)
            binding.root.setOnSafeClickListener {
                onCatListener.onClickCat(catDetail)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, catList[position])
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    fun addCatList(arrayList: ArrayList<CatDetail>) {
        catList = arrayList
        notifyDataSetChanged()
    }

    public interface OnCatListener {
        fun onClickCat(catDetail: CatDetail)
    }
}