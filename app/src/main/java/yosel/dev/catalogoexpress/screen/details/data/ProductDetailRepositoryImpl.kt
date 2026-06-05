package yosel.dev.catalogoexpress.screen.details.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yosel.dev.catalogoexpress.core.api.CatalogService
import yosel.dev.catalogoexpress.core.model.ProductModel
import yosel.dev.catalogoexpress.core.utils.toModel
import yosel.dev.catalogoexpress.screen.details.domain.ProductDetailRepository
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val catalogService: CatalogService
) : ProductDetailRepository {

    override suspend fun getProductDetail(productId: Int): Result<ProductModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = catalogService.getProductDetail(productId)
                if (response.isSuccessful && response.body() != null) {
                    val productDto = response.body()!!
                    Result.success(productDto.toModel())
                } else {
                    Result.failure(Exception("Error del servidor: ${response.code()}"))
                }
            } catch (e: java.io.IOException) {
                Result.failure(Exception("Error de conexión. Verifica tu red."))
            } catch (e: Exception) {
                Result.failure(Exception("Ocurrió un error inesperado: ${e.localizedMessage}"))
            }
        }
    }
}