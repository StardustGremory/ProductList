package com.example.productlist.domain

import com.example.productlist.data.responseService.ListProductsModel
import com.example.productlist.data.service.ProductRepository
import retrofit2.Response
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(private val productRepository: ProductRepository){

    suspend fun getList(search: String, sort: String): Response<ListProductsModel> {
        return productRepository.getList(search, sort)
    }
}
