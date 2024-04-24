package com.example.productlist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productlist.domain.GetProductListUseCase
import com.example.productlist.presentation.data.ProductModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ProductListViewModelTest {

    @RelaxedMockK
    private lateinit var getProductListUseCase: GetProductListUseCase
    private lateinit var productListViewModel: ProductListViewModel

    @get:Rule
    var rule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        productListViewModel = ProductListViewModel(getProductListUseCase)
        Dispatchers.setMain(Dispatchers.IO)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when change booleanShowProgressBar`() = runTest {
        val showTest = true
        productListViewModel.showDialog()
        assert(productListViewModel.showD.value == showTest)
    }

    @Test
    fun `when change booleanDismissProgressBar`() = runTest {
        val showTest = false
        productListViewModel.dismissDialog()
        assert(productListViewModel.showD.value == showTest)
    }

    @Test
    fun `when color of product is null or empty put default color`() = runTest {
        val color = "#3d9dd7"
        val product = ProductModel("Celulares", "1000", "899", "#3d9dd7", "", 1)
        productListViewModel.getColor(product)
        assert(productListViewModel.getColor(product) == color)
    }

    @Test
    fun `when color of product is null put default color`() = runTest {
        val color = "#000000"
        val product = ProductModel("Celulares", "1000", "899", null,"", 1)
        productListViewModel.getColor(product)
        assert(productListViewModel.getColor(product) == color)
    }

    @Test
    fun `when color of product is empty put default color`() = runTest {
        val color = "#000000"
        val product = ProductModel("Celulares", "1000", "899", "","", 1)
        productListViewModel.getColor(product)
        assert(productListViewModel.getColor(product) == color)
    }

    @Test
    fun `when change sort option`() = runTest {
        val sort = "menor precio"
        productListViewModel.changeSort("menor precio")
        assert(productListViewModel.sort.value == sort)
    }
}