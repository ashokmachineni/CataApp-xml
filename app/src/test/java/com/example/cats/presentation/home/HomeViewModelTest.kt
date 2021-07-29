package com.example.cats.presentation.home

import com.example.cats.BuildConfig
import com.example.cats.domain.network.ApiServices
import com.example.cats.domain.network.JsonRepository
import com.google.gson.Gson
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HomeViewModelTest {

    private lateinit var jsonRepository: JsonRepository
    private lateinit var placeholderApi: ApiServices
    private val mockServer = MockWebServer()

    /**
     * This method called start at class
     * Here we initialize all the required data
     */
    @Before
    fun setUp() {
        mockServer.start()
        placeholderApi = Retrofit.Builder()
            .baseUrl(mockServer.url(BuildConfig.SERVER_URL))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(ApiServices::class.java)
        jsonRepository = JsonRepository(placeholderApi)
    }

    /**
     * This will test Items api without filter
     */
    @Test
    fun successToCatApi()  {
        jsonRepository.getAllCats()
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it.size == 67}
            .assertNoErrors()
    }

    @Test
    fun failedToCatApi()  {
        jsonRepository.getAllCats()
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it.size == 66}
            .assertNoErrors()
    }

    /**
     * This will test Items api with filter like select any category
     */
    @Test
    fun successToSearchCatApi() {
        jsonRepository.getSearchCat("Aegean")
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it[0].id.equals("aege") }
            .assertNoErrors()
    }

    @Test
    fun failedToSearchCatApi() {
        jsonRepository.getSearchCat("Aegean")
            .test()
            .awaitDone(3, TimeUnit.SECONDS)
            .assertComplete()
            .assertValueCount(1)
            .assertValue { it[0].id.equals("abys") }
            .assertNoErrors()
    }

    /**
     * Here Close out mockserver object and release memory
     * Also Test end here with their result
     */
    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}