package com.example.cats.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cats.R
import com.example.cats.databinding.ActivityHomeBinding
import com.example.cats.domain.model.CatDetail
import com.example.cats.presentation.base.BaseActivity
import com.example.cats.presentation.details.DetailsActivity
import com.example.cats.utility.SharedPrefHelper
import com.example.cats.utility.gone
import com.example.cats.utility.setOnSafeClickListener
import com.example.cats.utility.visible
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity() {

    companion object{
        val EXTRA_MESSAGE = "This is a test"
    }

    private var catAdapter: CatAdapter? = null
    private var categoryAdapter: CategoryAdapter? = null
    private var binding: ActivityHomeBinding? = null
    private val currentViewModel: HomeViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel
    var categoryList: List<CatCategory> = ArrayList<CatCategory>()
    var catList: List<CatDetail> = ArrayList<CatDetail>()
    var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SharedPrefHelper.get(SharedPrefHelper.THEME, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initListener()
        attachObserver()
        setCategoryData()
        setCatAdapter()
        currentViewModel.getCats()
    }

    /**
     * set Listener of all the click event and other listeners
     */
    private fun initListener() {

        binding!!.searchCats.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == KeyEvent.KEYCODE_ENTER) {
                val search = binding!!.searchCats.text.toString()
                selectedIndex = -1
                categoryAdapter!!.changeIndex(-1)
                categoryAdapter!!.notifyDataSetChanged()
                if (search.isNotEmpty()) {
                    binding!!.progressBar.visible()
                    binding!!.catsRecycler.gone()
                    currentViewModel.getCatSearch(search)
                } else {
                    binding!!.progressBar.visible()
                    binding!!.catsRecycler.gone()
                    currentViewModel.getCats()
                }
            }
            false
        }

        binding!!.themeChange.setOnSafeClickListener {
            if (SharedPrefHelper.get(SharedPrefHelper.THEME, false)) {
                SharedPrefHelper.save(SharedPrefHelper.THEME, false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                SharedPrefHelper.save(SharedPrefHelper.THEME, true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    /**
     * Initialize Items adapter and set to recyclerView
     */
    private fun setCatAdapter() {
        catAdapter = CatAdapter(this, catList, object : CatAdapter.OnCatListener {
            override fun onClickCat(catDetail: CatDetail) {
                startActivity(
                    Intent(this@HomeActivity, DetailsActivity::class.java).putExtra(
                        "cat",
                        catDetail
                    )
                )
            }

        })
        binding!!.catsRecycler.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = catAdapter
        }
    }

    /**
     * Here we observe data which come from result of API call
     */
    private fun attachObserver() {
        currentViewModel.catData.observe(this) {
            catAdapter!!.addCatList(it)
            binding!!.progressBar.gone()
            binding!!.catsRecycler.visible()
        }
    }

    /**
     * Here get category from API and set to recyclerview for show category at top
     */
    private fun setCategoryData() {
        categoryList = getAllCatsCategories()
        categoryAdapter = CategoryAdapter(
            this,
            categoryList,
            selectedIndex,
            object : CategoryAdapter.OnCategoryListener {
                override fun onSelectCategory(pos: Int) {
                    selectedIndex = pos
                    binding!!.progressBar.visible()
                    binding!!.catsRecycler.gone()
                    binding!!.searchCats.setText(categoryList[pos].value)
                    currentViewModel.getCatSearch(categoryList[pos].value)
                }

            })
        binding!!.categoryRecycler.apply {
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

}