package yosel.dev.catalogoexpress.core.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("stock") val stock: Int?,
    @SerializedName("thumbnail") val thumbnail: String?
)
