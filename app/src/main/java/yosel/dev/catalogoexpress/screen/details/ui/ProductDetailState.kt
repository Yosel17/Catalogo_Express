package yosel.dev.catalogoexpress.screen.details.ui

import yosel.dev.catalogoexpress.core.model.ProductModel

data class ProductDetailState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val product: ProductModel? = null
)
