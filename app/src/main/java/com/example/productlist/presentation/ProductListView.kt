package com.example.productlist.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight
import com.example.productlist.presentation.data.ProductModel


@Composable
fun InitView(productListViewModel: ProductListViewModel) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        SearchView(Modifier.align(Alignment.TopEnd), productListViewModel)
    }
}


@Composable
fun SearchView(modifier: Modifier, productListViewModel: ProductListViewModel) {

    var query by rememberSaveable { mutableStateOf("") }
    val sort by productListViewModel.sort.observeAsState("")
    val show by productListViewModel.showD.observeAsState(false)
    val myListProducts: List<ProductModel> = productListViewModel.listProducts

    Column(
        Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        Spacer(
            Modifier
                .size(16.dp)
                .fillMaxWidth()
        )
        Row(
            Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Column {
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                        productListViewModel.callGetList(query, sort)
                    },
                    modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(16.dp)
                        ),
                    singleLine = true,
                    maxLines = 1,
                    placeholder = { Text(text = "Buscar producto en Liverpool..") },
                    textStyle = TextStyle(fontSize = 18.sp),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                )
                Row(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    Button(onClick = {
                        productListViewModel.changeSort("predefinida")
                        productListViewModel.callGetList(query, sort)
                    }) {
                        Text(text = "Predefinida", fontSize = 10.sp)
                    }
                    Button(onClick = {
                        productListViewModel.changeSort("menor precio")
                        productListViewModel.callGetList(query, sort)
                    }) {
                        Text(text = "Menor precio", fontSize = 10.sp)
                    }
                    Button(onClick = {
                        productListViewModel.changeSort("mayor precio")
                        productListViewModel.callGetList(query, sort)
                    }) {
                        Text(text = "Mayor precio", fontSize = 10.sp)
                    }
                    Button(onClick = {
                        productListViewModel.changeSort("relevancia")
                        productListViewModel.callGetList(query, sort)
                    }) {
                        Text(text = "Relevancia", fontSize = 10.sp)
                    }
                    Button(onClick = {
                        productListViewModel.changeSort("lo más nuevo")
                        productListViewModel.callGetList(query, sort)
                    }) {
                        Text(text = "Lo más nuevo", fontSize = 10.sp)
                    }
                    Button(onClick = {
                        productListViewModel.changeSort("calificaciones")
                        productListViewModel.callGetList(query, sort)
                    }) {
                        Text(text = "Calificaciones", fontSize = 10.sp)
                    }
                }
            }
        }
        Spacer(
            Modifier
                .size(16.dp)
                .fillMaxWidth()
        )
        if (show) {
            CircularProgressIndicator(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            )
        } else {
            ListProducts(myListProducts, productListViewModel)
            Spacer(
                Modifier
                    .size(16.dp)
                    .fillMaxWidth()
            )
        }
    }


}

@Composable
fun ListProducts(products: List<ProductModel>, productListViewModel: ProductListViewModel) {
    LazyColumn {
        items(products, key = { it.id }) { product ->
            ItemsList(productModel = product, productListViewModel)
        }
    }
}

@Composable
fun ItemsList(productModel: ProductModel, productListViewModel: ProductListViewModel) {
    val colorHex = productListViewModel.getColor(productModel)
    Card(
        border = BorderStroke(2.dp, Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(start = 16.dp, end = 16.dp)) {
        Row() {
            Image(
                painter = rememberAsyncImagePainter(productModel.image),
                contentDescription = "Card view",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
            )
            Column(
                Modifier
                    .size(200.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = productModel.name,
                    modifier = Modifier
                        .padding(8.dp),
                    maxLines = 2,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$" + productModel.precio.toString(),
                    modifier = Modifier
                        .padding(6.dp),
                    fontSize = 12.sp,
                    textDecoration = TextDecoration.LineThrough,
                    textAlign = TextAlign.Left,
                    color = Color.Gray
                )
                Text(
                    text = "$" + productModel.precioDesc.toString(),
                    modifier = Modifier
                        .padding(start = 6.dp),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                    color = Color.Red
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Colores: ", fontSize = 12.sp)
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                Color(android.graphics.Color.parseColor(colorHex))
                            )
                            .size(18.dp)
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Item: " + "${productModel.id}",
                        fontSize = 8.sp,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(top = 16.dp, end = 16.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Right,
                        color = Color.Black
                    )
                }

            }
        }
    }
    Spacer(
        Modifier
            .size(16.dp)
            .fillMaxWidth()
    )
}






