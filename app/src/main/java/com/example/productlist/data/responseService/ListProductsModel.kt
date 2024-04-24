package com.example.productlist.data.responseService

data class ListProductsModel(
    val nullSearch: String,
    val pageType: String,
    val plpResults: PlpResults,
    val status: Status
)