package com.example.cats.domain.network

import com.example.cats.domain.model.CatDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    /*@GET(ApiConstant.albums)
    suspend fun gelAlbum(): ArrayList<AlbumModel>*/

    @GET("breeds")
    suspend fun getCats(
    ): ArrayList<CatDetail>

    @GET("breeds/search")
    suspend fun getCatSearch(
        @Query("q") id: String?
    ): ArrayList<CatDetail>


    @GET("breeds")
    fun getCatsTest(
    ): Single<ArrayList<CatDetail>>

    @GET("breeds/search")
    fun getCatSearchTest(
        @Query("q") id: String?
    ): Single<ArrayList<CatDetail>>
}