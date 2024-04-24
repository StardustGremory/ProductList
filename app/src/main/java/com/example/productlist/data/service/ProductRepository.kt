package com.example.productlist.data.service

import com.example.productlist.data.responseService.ListProductsModel
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productService: ProductService){

    suspend fun getList(search: String, sort: String): Response<ListProductsModel> {
        return productService.getList(search = search, sort = sort)
    }
}