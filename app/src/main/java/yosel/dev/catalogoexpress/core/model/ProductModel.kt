package yosel.dev.catalogoexpress.core.model

data class ProductModel(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val stock: Int,
    val imageUrl: String,
    val score: Double
)
