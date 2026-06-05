package yosel.dev.catalogoexpress.core.utils

import yosel.dev.catalogoexpress.core.model.ProductModel
import yosel.dev.catalogoexpress.core.response.ProductResponse
import kotlin.math.ln
import kotlin.math.max

fun ProductResponse.toModel(): ProductModel {
    val safeId = this.id ?: 0
    val safeTitle = if (this.title.isNullOrEmpty()) "Producto sin título" else this.title
    val safeDescription = this.description ?: ""
    val safePrice = this.price ?: 0.0
    val safeRating = this.rating ?: 0.0
    val safeStock = this.stock ?: 0
    val safeImageUrl = this.thumbnail ?: ""

    val numerator = safeRating * ln(safeStock + 1.0)
    val denominator = max(safePrice, 1.0)
    val calculatedScore = numerator / denominator

    return ProductModel(
        id = safeId,
        title = safeTitle,
        description = safeDescription,
        price = safePrice,
        rating = safeRating,
        stock = safeStock,
        imageUrl = safeImageUrl,
        score = calculatedScore
    )
}