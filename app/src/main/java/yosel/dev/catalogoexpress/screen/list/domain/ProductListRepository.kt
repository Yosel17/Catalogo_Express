package yosel.dev.catalogoexpress.screen.list.domain

import yosel.dev.catalogoexpress.core.model.ProductModel

interface ProductListRepository {

    suspend fun getListProducts(): Result<List<ProductModel>>
}