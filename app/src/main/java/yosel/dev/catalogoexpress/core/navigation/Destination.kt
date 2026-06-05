package yosel.dev.catalogoexpress.core.navigation

import kotlinx.serialization.Serializable
sealed interface Destination {
    @Serializable
    data object ProductList : Destination

    @Serializable
    data class ProductDetail(val productId: Int) : Destination
}