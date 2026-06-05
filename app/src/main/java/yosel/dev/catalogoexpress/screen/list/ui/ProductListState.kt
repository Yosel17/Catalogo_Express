package yosel.dev.catalogoexpress.screen.list.ui

import yosel.dev.catalogoexpress.core.model.ProductModel

data class ProductListState(
    val isLoadingGetListProduct: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val productList: List<ProductModel> = emptyList()
)
