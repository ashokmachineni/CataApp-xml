package com.example.cats.domain.network

import com.example.cats.domain.model.CatDetail
import com.example.cats.domain.network.ApiServices
import io.reactivex.Single

class JsonRepository(private val api: ApiServices) {

    fun getAllCats(): Single<ArrayList<CatDetail>> = api.getCatsTest()

    fun getSearchCat(value: String): Single<ArrayList<CatDetail>> = api.getCatSearchTest(value)

}