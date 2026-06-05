package yosel.dev.catalogoexpress.core.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import yosel.dev.catalogoexpress.core.response.ProductListResponse
import yosel.dev.catalogoexpress.core.response.ProductResponse

interface CatalogService {

    @GET("products")
    suspend fun getProducts(): Response<ProductListResponse>

    @GET("products/{id}")
    suspend fun getProductDetail(
        @Path("id") productId: Int
    ): Response<ProductResponse>
}