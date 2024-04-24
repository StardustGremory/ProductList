package com.example.productlist.data.service

import com.example.productlist.data.responseService.ListProductsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductClient {
    @GET("appclienteservices/services/v7/plp/sf")
    suspend fun getList(
        @Query("page-number") page: String = "1",
        @Query("search-string") search: String ,
        @Query("sort-option") sort: String = "",
        @Query("force-plp") plp: String = "false",
        @Query("number-of-items-per-page") numPages : String = "40",
        @Query("cleanProductName") clean : String = "false"
    ): Response<ListProductsModel>
}
