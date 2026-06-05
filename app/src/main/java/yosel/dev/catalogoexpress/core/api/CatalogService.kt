package yosel.dev.catalogoexpress.core.api

import retrofit2.Response
import retrofit2.http.GET
import yosel.dev.catalogoexpress.core.response.ProductListResponse

interface CatalogService {

    @GET("products")
    suspend fun getProductos(): Response<ProductListResponse>
}