package com.example.productlist.data.responseService

data class RefinementGroup(
    val dimensionName: String,
    val moreRefinements: Boolean,
    val multiSelect: Boolean,
    val name: String,
    val refinement: List<Refinement>
)