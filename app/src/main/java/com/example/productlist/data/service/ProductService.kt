package com.example.productlist.data.service

import com.example.productlist.data.responseService.ListProductsModel
import retrofit2.Response
import javax.inject.Inject

class ProductService @Inject constructor(private val productClient: ProductClient) {

    suspend fun getList(search: String, sort: String): Response<ListProductsModel>{
        return productClient.getList(search = search, sort = sort)
    }

}