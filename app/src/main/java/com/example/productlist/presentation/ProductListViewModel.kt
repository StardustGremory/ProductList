package com.example.productlist.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productlist.domain.GetProductListUseCase
import com.example.productlist.presentation.data.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val getProductListUseCase: GetProductListUseCase) :
    ViewModel() {

    private val _listProducts = mutableStateListOf<ProductModel>()
    val listProducts: List<ProductModel> = _listProducts

    private val _showD = MutableLiveData<Boolean>()
    val showD: MutableLiveData<Boolean> = _showD

    private val _sort = MutableLiveData<String>()
    val sort = _sort

    fun callGetList(query: String, sort: String) {
        showDialog()
        viewModelScope.launch {
            val call = getProductListUseCase.getList(search = query, sort = sort)
            if (call.body() != null) {
                _listProducts.clear()
                val list = call.body()?.plpResults?.records
                if (list!!.isNotEmpty()) {
                    for (x in list.indices) {
                        _listProducts.add(
                            ProductModel(
                                list[x].productDisplayName,
                                list[x].listPrice.convert(),
                                list[x].promoPrice.convert(),
                                list[x].variantsColor?.get(0)?.colorHex,
                                list[x].lgImage,
                                x + 1
                            )
                        )
                    }
                }
            }
            dismissDialog()
        }
    }

    fun getColor(productModel: ProductModel): String {
        return if (!productModel.colorHex.isNullOrEmpty()) {
            productModel.colorHex ?: "#000000"
        } else {
            "#000000"
        }
    }

    fun changeSort(sort: String) {
        _sort.value = sort
    }

    fun showDialog() {
        _showD.value = true
    }

    fun dismissDialog() {
        _showD.value = false
    }

    private fun Double.convert(): String {
        val format = DecimalFormat("#,###.00")
        format.isDecimalSeparatorAlwaysShown = false
        return format.format(this).toString()
    }

}
