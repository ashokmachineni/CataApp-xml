package com.example.cats.presentation.home

import androidx.lifecycle.MutableLiveData
import com.example.cats.domain.model.CatDetail
import com.example.cats.domain.network.ApiServices
import com.example.cats.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(var apiService: ApiServices) : BaseViewModel() {


    val catData: MutableLiveData<ArrayList<CatDetail>> = MutableLiveData()

    /**
     * Get List of items from server
     */
    fun getCats()    {
        launch {
            kotlin.runCatching {
                apiService.getCats()
            }.fold({
                catData.postValue(it)
            }, {
                exceptionLiveData.postValue(it)
            })
        }
    }

    /**
     * Get List of items from server which you enter in search or selected category
     */
    fun getCatSearch(value: String?) {
        launch {
            kotlin.runCatching {
                apiService.getCatSearch(value)
            }.fold({
                catData.postValue(it)
            }, {
                exceptionLiveData.postValue(it)
            })
        }
    }
}