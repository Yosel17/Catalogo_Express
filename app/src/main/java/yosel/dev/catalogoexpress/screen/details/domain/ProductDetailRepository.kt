package yosel.dev.catalogoexpress.screen.details.domain

import yosel.dev.catalogoexpress.core.model.ProductModel

interface ProductDetailRepository {
    suspend fun getProductDetail(productId: Int): Result<ProductModel>
}