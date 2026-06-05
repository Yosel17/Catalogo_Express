package yosel.dev.catalogoexpress.core.response

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("products") val products: List<ProductResponse>?
)
