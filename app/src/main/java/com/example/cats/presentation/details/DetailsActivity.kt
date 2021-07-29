package com.example.cats.presentation.details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.cats.R
import com.example.cats.databinding.ActivityDetailsBinding
import com.example.cats.domain.model.CatDetail
import com.example.cats.utility.*

class DetailsActivity : AppCompatActivity() {
    private var catData: CatDetail? = null
    private lateinit var binding: ActivityDetailsBinding
    var favourite = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        catData = intent.getSerializableExtra("cat") as CatDetail
        favourite = convertStringToList("favourite")
        setData()
        initListener()
    }

    private fun initListener() {
        if (favourite.contains(catData!!.id)) {
            binding.favourite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav))
        } else {
            binding.favourite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unfav))
        }
        binding.favourite.setOnSafeClickListener {
            if (favourite.contains(catData!!.id)) {
                favourite.remove(catData!!.id)
                binding.favourite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unfav))
            } else {
                favourite.add(catData!!.id!!)
                binding.favourite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fav))
            }
            SharedPrefHelper.save(
                "favourite",
                convertObjectToString(favourite)
            )
        }
        binding.home.setOnSafeClickListener {
            onBackPressed()
        }
    }

    /**
     * Here we show details of selected item from first screen
     */
    private fun setData() {
        binding.catName.text = catData!!.name
        Glide.with(this)
            .load("https://cdn2.thecatapi.com/images/${catData!!.reference_image_id}.jpg")
            .into(binding.catImage)
        binding.catWikipedia.text = "Cat Wikipedia: ${catData!!.wikipedia_url}"
        binding.catDetail.text = catData!!.description
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            binding.shimmerLayout.gone()
            binding.originalLayout.visible()
        }, 1500)
    }
}